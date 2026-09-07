import java.util.List;

public class AnalysisResult {
    public final Analysis.GrowthModel model;
    public final List<Double> ratios;

    public AnalysisResult(Analysis.GrowthModel model, List<Double> ratios) {
        this.model = model;
        this.ratios = ratios;
    }
}
