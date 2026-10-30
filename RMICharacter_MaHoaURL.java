import RMI.CharacterService;
import java.net.URI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMICharacter_MaHoaURL {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCDT169";
        String qCode = "b1LoNgVP";
        
        Registry registry = LocateRegistry.getRegistry("36.50.135.242", 1099);
        CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
        
        String data = service.requestCharacter(studentCode, qCode);
        
        if (data != null) {
            String encodedString = new URI(null, null, data, null).toASCIIString();
            service.submitCharacter(studentCode, qCode, encodedString);
        } else {
            System.out.println("Lỗi: Server trả về null. Hãy tải lại trang để lấy qCode mới!");
        }
    }
}