import RMI.DataService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Locale;

public class RMIData_CSVStats {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCDT169";
        String qCode = "wPveEhki";
        
        Registry registry = LocateRegistry.getRegistry("36.50.135.242", 1099);
        DataService service = (DataService) registry.lookup("RMIDataService");
        
        String data = (String) service.requestData(studentCode, qCode);
        
        if (data != null && !data.isEmpty()) {
            String[] parts = data.split(",");
            int n = parts.length;
            double[] arr = new double[n];
            double sum = 0;
            
            for (int i = 0; i < n; i++) {
                arr[i] = Double.parseDouble(parts[i].trim());
                sum += arr[i];
            }
            
            double mean = sum / n;
            
            double varianceSum = 0;
            for (double v : arr) {
                varianceSum += Math.pow(v - mean, 2);
            }
            double stddev = Math.sqrt(varianceSum / n);
            
            Arrays.sort(arr);
            int p95Index = (int) Math.ceil(n * 0.95) - 1;
            double p95 = arr[p95Index];
            
            String result = String.format(Locale.US, "average=%.2f;stddev=%.2f;p95=%.2f", mean, stddev, p95);
            
            service.submitData(studentCode, qCode, result);
        }
    }
}