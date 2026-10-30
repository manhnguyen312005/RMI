import RMI.ByteService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;

public class RMIByte_TimPhanTuMax {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCDT169";
        String qCode = "qfkN4sIp";
        
        Registry registry = LocateRegistry.getRegistry("36.50.135.242", 1099);
        ByteService service = (ByteService) registry.lookup("RMIByteService");
        
        byte[] data = service.requestData(studentCode, qCode);
        
        Map<Byte, Integer> map = new LinkedHashMap<>();
        for (byte b : data) {
            map.put(b, map.getOrDefault(b, 0) + 1);
        }
        
        byte maxByte = data[0];
        int maxFreq = -1;
        
        for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                maxByte = entry.getKey();
            }
        }
        
        byte[] result = new byte[] { maxByte, (byte) maxFreq };
        service.submitData(studentCode, qCode, result);
    }
}