import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
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

        int[] sizes = {1000, 2000, 4000, 8000, 16000};
        int warmUpTrials = 1000;
        int timedTrials = 100;

        /*  The sizes initially ranged from 500 - 32000, but 32000 took too long
            for my mac, and 500 ended up feeling like dead weight due to testing
            array sort. The analysis of array sort kept coming back as linear
            in a coefficient of variations test, ended up being due to noise in
            the 500 - 1000 size range.

            That's also why warm up trials ended up being set to 1000. Initially,
            it was running ok at like 200-500, but when testing array sort it
            seemed to need much more priming than the other algorithms.

            I wanted to think about building in setting the warm ups at different
            amounts based on the algorithm but it's out of scope so i skipped that
            just to keep running along with the rest of the program.

            Timed trials are set at 100 just because i felt that was a reasonable
            amount to get ok mean, median, and std. dev. data.
         */

        Map<String, PerformanceData> allData = new LinkedHashMap<>();

        for (Algorithm<int[]> algorithm : algorithms) {
            String algoName = algorithm.getName();
            System.out.printf("Running experiment on " + algoName + "...%n%n");

            PerformanceData data = experiment.run(algorithm, generator, sizes, warmUpTrials, timedTrials);
            List<PerformanceData.Row> rows = data.getRows();

            List<AnalysisResult> results = analysis.compareAll(data);

            for (AnalysisResult result : results) {
                System.out.printf("Analyzing against " + result.model + " model...%n%n");
                for (int i = 0; i < rows.size(); i++) {
                    System.out.printf("size=%d  mean=%.1fns ratio=%.8f%n",
                            rows.get(i).size, rows.get(i).meanNanosecs, result.ratios.get(i));
                }
                System.out.printf("%n%-13s CV=%.4f%n%n", result.model, result.coefficientOfVariation);
            }

            AnalysisResult best = analysis.findBestFit(results);
            System.out.printf("  --> Best fit: %s (CV=%.4f)%n%n", best.model, best.coefficientOfVariation);

            allData.put(algoName, data);

            String filepath = algoName + "_report.csv";
            report.toCSV(data, filepath);
        }

        report.toCombinedCSV(allData, "combined_results.csv");
    }
}