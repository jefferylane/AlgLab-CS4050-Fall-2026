public class ExperimentTest {
    public static void main(String[] args) {
        Algorithm<int[]> algorithm = new InsertionSort();
        InputGenerator<int[]> generator = new RandomIntArrayGenerator(42L);
        Measurement measurement = new Measurement();
        Experiment experiment = new Experiment();

        int[] sizes = {500, 1000, 2000, 4000};
        int warmUpTrials = 500;
        int timedTrials = 10;

        PerformanceData data = experiment.run(algorithm, generator, sizes, warmUpTrials, timedTrials);

        for (PerformanceData.Row row : data.getRows()) {
            System.out.printf("size=%d  mean=%.1fns median=%.1fns   stdev=%.1fns%n",
                row.size, row.meanNanosecs, row.medianNanosecs, row.stDevNanosecs);
        }
    }
}
