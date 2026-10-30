import RMI.ObjectService;
import RMI.TicketSla;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMI_XuLyObject {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCDT169";
        String qCode = "Y3MyUlBs";
        
        Registry registry = LocateRegistry.getRegistry("36.50.135.242", 1099);
        ObjectService service = (ObjectService) registry.lookup("RMIObjectService");
        
        TicketSla ticket = (TicketSla) service.requestObject(studentCode, qCode);
        
        if (ticket != null) {
            String p = ticket.getPriority();
            int h = ticket.getOpenedHoursAgo();
            boolean isBreached = false;
            
            if ("CRITICAL".equals(p) && h > 2) isBreached = true;
            else if ("HIGH".equals(p) && h > 8) isBreached = true;
            else if ("MEDIUM".equals(p) && h > 24) isBreached = true;
            else if ("LOW".equals(p) && h > 72) isBreached = true;
            
            ticket.setBreached(isBreached);
            
            if (!isBreached) {
                ticket.setAction("MONITOR");
            } else {
                if ("CRITICAL".equals(p) || h > 96) {
                    ticket.setAction("ESCALATE_L2");
                } else {
                    ticket.setAction("ESCALATE_L1");
                }
            }
            
            service.submitObject(studentCode, qCode, ticket);
        }
    }
}