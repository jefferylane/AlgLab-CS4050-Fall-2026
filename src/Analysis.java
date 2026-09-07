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
            double ratio = row.meanNanosecs / predicted;
            ratios.add(ratio);
        }
        return new AnalysisResult(model, ratios);
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
}

