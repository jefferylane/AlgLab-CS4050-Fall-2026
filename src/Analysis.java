import java.util.ArrayList;
import java.util.List;

public class Analysis {
    public enum GrowthModel {
        LINEAR,
        LINEARITHMIC,
        QUADRATIC
    }

    public AnalysisResult compare(PerformanceData data, GrowthModel model) {
        List<Double> ratios = new ArrayList<>();

        for (PerformanceData.Row row : data.getRows()) {
            double predicted = theoreticalValue(model, row.size);
            ratios.add(row.meanNanosecs / predicted);
        }

        return new AnalysisResult(model, ratios, coefficientOfVariention(ratios));
    }

    private double theoreticalValue(GrowthModel model, int size) {
        switch(model) {
            case LINEAR:
                return size;
            case LINEARITHMIC:
                return size * Math.log(size);
            case QUADRATIC:
                return size * (double) size;
            default:
                throw new IllegalArgumentException("Unknown Model: " + model);
        }
    }

    private static double coefficientOfVariention(List<Double> ratios) {
        double sum = 0;
        for (double ratio : ratios) {
            sum += ratio;
        }
        double mean = sum / ratios.size();

        double variance = 0;
        for (double ratio : ratios) {
            variance += Math.pow(ratio - mean, 2);
        }

        return Math.sqrt(variance) / mean;
    }

    public List<AnalysisResult> compareAll(PerformanceData data) {
        List<AnalysisResult> results = new ArrayList<>();
        for (GrowthModel model : GrowthModel.values()) {
            results.add(compare(data, model));
        }
        return results;
    }

    public AnalysisResult findBestFit(List<AnalysisResult> results) {
        AnalysisResult best = null;
        for (AnalysisResult result : results) {
            if (best == null || result.coefficientOfVariation < best.coefficientOfVariation) {
                best = result;
            }
        }
        return best;
    }
}

