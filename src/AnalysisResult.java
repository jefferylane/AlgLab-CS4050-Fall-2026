import java.util.List;

public class AnalysisResult {
    public final Analysis.GrowthModel model;
    public final List<Double> ratios;
    public final double coefficientOfVariation;

    public AnalysisResult(Analysis.GrowthModel model, List<Double> ratios, double coefficientOfVariation) {
        this.model = model;
        this.ratios = ratios;
        this.coefficientOfVariation = coefficientOfVariation;
    }
}
