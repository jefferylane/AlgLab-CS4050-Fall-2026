import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.printf("%nAlgorithm Lab: Testing the performance of sorting algorithms%n"
                        + "Jeffery Lane | CS4050-003 Fall 2026%n%n"

                        + "This program runs an experiment that measures how fast different "
                        + "sorting algorithms run, computes basic statistics (mean, median, "
                        + "and std. dev.) on the algorithm's performance data, and analyzes "
                        + "that data to see which theoretical models best fit each "
                        + "algorithm's performance.%n%n"
                    );

        List<Algorithm<int[]>> algorithms = Arrays.asList(
            new InsertionSort(),
            new SelectionSort(),
            new MergeSort(),
            new ArraysSortWrapper()
        );

        InputGenerator<int[]> generator = new RandomIntArrayGenerator(42L);
        Experiment experiment = new Experiment();
        Analysis analysis = new Analysis();
        Report report = new Report();
        String reportsDir = "reports";

        /*  Two size scales instead of one.

            slowScaleSizes stays as-is for Insertion Sort and Selection Sort —
            O(n^2) work means anything much larger than 16,000 starts taking
            a long time per trial.

            fastScaleSizes is for Merge Sort and Arrays.sort. Both are
            O(n log n), so they can handle far larger n in the same wall-clock
            budget, and a wider range is *needed*: across 1,000-16,000,
            log(n) only changes by about 40%, which makes linear and
            linearithmic growth hard to tell apart numerically (this is the
            issue documented in methodology-learnings). Widening the range
            here gives log(n) more room to move, which should make the
            n log n fit separate more clearly from a pure linear fit.

            Which algorithm uses which scale is decided explicitly above
            (slowScaleAlgorithms / fastScaleAlgorithms), not inferred at
            runtime -- there's no requirement for the program to figure
            this out on its own.
         */

        int[] slowScaleSizes = {1000, 2000, 4000, 8000, 16000};
        int[] fastScaleSizes = {20000, 40000, 80000, 160000, 320000};
        
        List<Algorithm<int[]>> slowScaleAlgorithms = Arrays.asList(algorithms.get(0), algorithms.get(1));
        List<Algorithm<int[]>> fastScaleAlgorithms = Arrays.asList(algorithms.get(2), algorithms.get(3));

        int warmUpTrials = 100;
        int timedTrials = 100;

        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Insertion Sort");
            System.out.println("2. Selection Sort");
            System.out.println("3. Merge Sort");
            System.out.println("4. Array Sort (Wrapper)");
            System.out.println("5. Run All Tests");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    runExperiment(algorithms.get(0), experiment, generator, analysis, report, reportsDir,
                            slowScaleSizes, warmUpTrials, timedTrials);
                    break;
                case "2":
                    runExperiment(algorithms.get(1), experiment, generator, analysis, report, reportsDir,
                            slowScaleSizes, warmUpTrials, timedTrials);
                    break;
                case "3":
                    runExperiment(algorithms.get(2), experiment, generator, analysis, report, reportsDir,
                            fastScaleSizes, warmUpTrials, timedTrials);
                    break;
                case "4":
                    runExperiment(algorithms.get(3), experiment, generator, analysis, report, reportsDir,
                            fastScaleSizes, warmUpTrials, timedTrials);
                    break;
                case "5":
                    Map<String, PerformanceData> allData = new LinkedHashMap<>();
                    for (Algorithm<int[]> algorithm : slowScaleAlgorithms) {
                        allData.put(algorithm.getName(),
                                runExperiment(algorithm, experiment, generator, analysis, report, reportsDir,
                                        slowScaleSizes, warmUpTrials, timedTrials));
                    }
                    for (Algorithm<int[]> algorithm : fastScaleAlgorithms) {
                        allData.put(algorithm.getName(),
                                runExperiment(algorithm, experiment, generator, analysis, report, reportsDir,
                                        fastScaleSizes, warmUpTrials, timedTrials));
                    }
                    report.toCombinedCSV(allData, reportsDir + File.separator + "combined_results.csv");
                    break;
                case "6":
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
        scanner.close();
    }

    private static PerformanceData runExperiment(Algorithm<int[]> algorithm, Experiment experiment, InputGenerator<int[]> generator,
                                     Analysis analysis, Report report, String reportsDir,
                                     int[] sizes, int warmUpTrials, int timedTrials) throws IOException {
        String algoName = algorithm.getName();
        System.out.printf("Running experiment on %s (sizes=%s)...%n%n", algoName, Arrays.toString(sizes));

        PerformanceData data = experiment.run(algorithm, generator, sizes, warmUpTrials, timedTrials);
        List<PerformanceData.Row> rows = data.getRows();

        List<AnalysisResult> results = analysis.compareAll(data);

        for (AnalysisResult result : results) {
            System.out.printf("Analyzing against %s model...%n%n", result.model);
            for (int i = 0; i < rows.size(); i++) {
                System.out.printf("size=%d  mean=%.1fns ratio=%.8f%n",
                        rows.get(i).size, rows.get(i).meanNanosecs, result.ratios.get(i));
            }
            System.out.printf("%n%-13s CV=%.4f%n%n", result.model, result.coefficientOfVariation);
        }

        AnalysisResult best = analysis.findBestFit(results);
        System.out.printf("  --> Best fit: %s (CV=%.4f)%n%n", best.model, best.coefficientOfVariation);

        String filepath = reportsDir + File.separator + sanitizeFilename(algoName) + "_report.csv";
        report.toCSV(data, filepath);

        return data;
    }

    private static String sanitizeFilename(String name) {
        return name.replaceAll("[^a-zA-Z0-9.-]", "");
    }
}