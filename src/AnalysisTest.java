import java.util.List;

public class AnalysisTest {
    public static void main(String[] args) {
        Algorithm<int[]> algorithm = new InsertionSort();
        InputGenerator<int[]> generator = new RandomIntArrayGenerator(42L);
        Experiment experiment = new Experiment();
        Analysis analysis = new Analysis();

        int[] sizes = {500, 1000, 2000, 4000};
        int warmUpTrials = 500;
        int timedTrials = 10;

        PerformanceData data = experiment.run(algorithm, generator, sizes, warmUpTrials, timedTrials);
        AnalysisResult result = analysis.compare(data, Analysis.GrowthModel.LINEAR);
    
        List<PerformanceData.Row> rows = data.getRows();
        for (int i = 0; i < rows.size(); i++) {
            System.out.printf("size=%d  mean=%.1fns ratio=%.8f%n",
                rows.get(i).size, rows.get(i).meanNanosecs, result.ratios.get(i));
        }
    }
}
