import RMI.ByteService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;

public class RMIByte_TimPhanTuMin {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCDT169";
        String qCode = "VJaLbJqx";
        String serverIp = "36.50.135.242";

        Registry registry = LocateRegistry.getRegistry(serverIp, 1099);
        ByteService service = (ByteService) registry.lookup("RMIByteService");

        byte[] data = service.requestData(studentCode, qCode);

        Map<Byte, Integer> map = new LinkedHashMap<>();
        for (byte b : data) {
            map.put(b, map.getOrDefault(b, 0) + 1);
        }

        byte minByte = data[0];
        int minFreq = Integer.MAX_VALUE;

        for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
            if (entry.getValue() < minFreq) {
                minFreq = entry.getValue();
                minByte = entry.getKey();
            }
        }

        byte[] result = new byte[] { minByte, (byte) minFreq };
        service.submitData(studentCode, qCode, result);
    }
}