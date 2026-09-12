import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class Report {
    public void toCSV(PerformanceData data, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("size,mean_ns,median_ns,stdev_ns");
            for (PerformanceData.Row row : data.getRows()) {
                writer.printf("%d,%.2f,%.2f,%.2f%n", row.size, row.meanNanosecs, row.medianNanosecs, row.stDevNanosecs);
            }
        }
    }

    public void toCombinedCSV(Map<String, PerformanceData> allData, String filepath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            writer.println("algorithm,size,mean_ns,median_ns,stdev_ns");
            for (Map.Entry<String, PerformanceData> entry : allData.entrySet()) {
                String algoName = entry.getKey();
                for (PerformanceData.Row row : entry.getValue().getRows()) {
                    writer.printf("%s,%d,%.2f,%.2f,%.2f%n",
                            algoName, row.size, row.meanNanosecs, row.medianNanosecs, row.stDevNanosecs);
                }
            }
        }
    }
}
