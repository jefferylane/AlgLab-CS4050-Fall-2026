public class Experiment {
    private final Measurement measurement = new Measurement();

    public <T> PerformanceData run(Algorithm<T> algorithm, InputGenerator<T> generator, int[] sizes, int warmUpTrials, int timedTrials) {
        PerformanceData data = new PerformanceData(algorithm.getName(), generator.getDescription());

        for (int size : sizes) {
            long[] rawTimes = measurement.measure(algorithm, generator, size, warmUpTrials, timedTrials);
            data.addRow(size, rawTimes);
        }

        return data;
    }
}
