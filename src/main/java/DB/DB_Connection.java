package DB;
import java.sql.Connection;
import java.sql.DriverManager;

public class DB_Connection {
    public DB_Connection() {}
    public Connection makeConnection(){
        try{
            return DriverManager.getConnection("jdbc:mysql://atm_fedsplitin:63f30ae389d224406d89e9b342c66eee2040fa10@swb.h.filess.io:3307/atm_fedsplitin", "atm_fedsplitin", "63f30ae389d224406d89e9b342c66eee2040fa10");

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
