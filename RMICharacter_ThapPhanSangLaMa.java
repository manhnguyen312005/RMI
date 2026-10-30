import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMICharacter_ThapPhanSangLaMa {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCDT169";
        String qCode = "JIu7QhQX";
        
        Registry registry = LocateRegistry.getRegistry("36.50.135.242", 1099);
        CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
        
        String data = service.requestCharacter(studentCode, qCode);
        
        if (data != null) {
            int num = Integer.parseInt(data.trim());
            int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
            String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
            
            StringBuilder roman = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                while (num >= values[i]) {
                    num -= values[i];
                    roman.append(symbols[i]);
                }
            }
            
            service.submitCharacter(studentCode, qCode, roman.toString());
        } else {
            System.out.println("Lỗi: Server trả về null. Hãy kiểm tra lại qCode!");
        }
    }
}