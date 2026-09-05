import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PerformanceData {
    private final String algorithmName;
    private final String generatorDesc;
    private final List<Row> rows = new ArrayList<>();

    public PerformanceData(String algorithmName, String generatorDesc) {
        this.algorithmName = algorithmName;
        this.generatorDesc = generatorDesc;
    }

    public void addRow(int size, long[] rawTimes) {
        double meanNanosecs = calcMeanNs(rawTimes);
        double medianNanosecs = calcMedianNs(rawTimes);
        double stDevNanosecs = calcStDevNs(meanNanosecs, rawTimes);

        rows.add(new Row(size, rawTimes, meanNanosecs, medianNanosecs, stDevNanosecs));
    }

    public List<Row> getRows() {
        return rows;
    }

    public static class Row {
        public final int size;
        public final long[] rawTimes;
        public final double meanNanosecs;
        public final double medianNanosecs;
        public final double stDevNanosecs;

        Row(int size, long[] rawTimes, double meanNanosecs, double medianNanosecs, double stDevNanosecs) {
            this.size = size;
            this.rawTimes = rawTimes;
            this.meanNanosecs = meanNanosecs;
            this.medianNanosecs = medianNanosecs;
            this.stDevNanosecs = stDevNanosecs;
        }
    }

    private double calcMeanNs(long[] rawTimes) {
        double sum = 0;

        for (long rawTime : rawTimes) {
            sum += rawTime;
        }
        double meanNanosecs = sum / rawTimes.length;
        return meanNanosecs;
    }

    private double calcMedianNs(long[] rawTimes) {
        long[] sortedTimes = Arrays.copyOf(rawTimes, rawTimes.length);
        Arrays.sort(sortedTimes);

        int length = rawTimes.length;
        if(length % 2 == 1) {
            return (double) sortedTimes[length / 2];
        }
        else {
            return (double) (sortedTimes[length / 2 - 1] + sortedTimes[length / 2]) / 2;
        } 
    }

    private double calcStDevNs(double mean, long[] rawTimes) {
        double stDev = 0;

        for (long rawTime : rawTimes) {
            stDev += Math.pow(rawTime - mean, 2);
        }
        return Math.sqrt(stDev / rawTimes.length);
    }
}