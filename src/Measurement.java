public class Measurement {
    public <T> long[] measure(Algorithm<T> algorithm, InputGenerator<T> generator, int size, int warmUpTrials, int timedTrials) {
        warmUp(algorithm, generator, size, warmUpTrials);
        return runTimedTrials(algorithm, generator, size, timedTrials);
    }

    private <T> long timeOneExecution(Algorithm<T> algorithm, T input) {
        long start = System.nanoTime();
        algorithm.execute(input);
        long elapsedTime = (System.nanoTime() - start);

        return elapsedTime;
    }

    private <T> void warmUp(Algorithm<T> algorithm, InputGenerator<T> generator, int size, int warmUpTrials) {
        for (int i = 0; i < warmUpTrials; i++) {
            T input = generator.generate(size);
            algorithm.execute(input);
        }
    }

    private <T> long[] runTimedTrials(Algorithm<T> algorithm, InputGenerator<T> generator, int size, int timedTrials) {
        long[] timedTrialResults = new long[timedTrials];

        for (int trial = 0; trial < timedTrials; trial++) {
            T input = generator.generate(size);
            timedTrialResults[trial] = timeOneExecution(algorithm, input);
        }
        return timedTrialResults;
    }
}
