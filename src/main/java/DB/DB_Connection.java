package DB;


import javax.xml.transform.Source;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DB_Connection {

    public DB_Connection() {

    }

    public Connection makeConnection(){
        try{
            return DriverManager.getConnection("jdbc:mysql://atmproject_swimmingon:5d982b922928dd5c252ad1a7f6f269bcf6eed610@x2q.h.filess.io:3307/atmproject_swimmingon", "atmproject_swimmingon", "5d982b922928dd5c252ad1a7f6f269bcf6eed610");

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
