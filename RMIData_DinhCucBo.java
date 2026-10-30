import RMI.DataService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class RMIData_DinhCucBo {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCDT169";
        String qCode = "m6G8yOJ3";
        
        Registry registry = LocateRegistry.getRegistry("36.50.135.242", 1099);
        DataService service = (DataService) registry.lookup("RMIDataService");
        
        int[] a = (int[]) service.requestData(studentCode, qCode);
        
        if (a != null) {
            List<Integer> ans = new ArrayList<>();
            int n = a.length;
            
            if (n > 0) {
                if (n == 1) {
                    ans.add(1);
                } else {
                    if (a[0] > a[1]) ans.add(1);
                    
                    for (int i = 1; i < n - 1; i++) {
                        if (a[i] > a[i - 1] && a[i] > a[i + 1]) {
                            ans.add(i + 1);
                        }
                    }
                    
                    if (a[n - 1] > a[n - 2]) ans.add(n);
                }
            }
            
            service.submitData(studentCode, qCode, ans);
        }
    }
}