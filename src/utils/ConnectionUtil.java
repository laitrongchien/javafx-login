package utils;

import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectionUtil {
    private Connection connect;
    public static Connection connectDb() {
        try {
//            String url = "jdbc:mysql://localhost:3306/javafx-login";
//            String username = "root";
//            String password = "laichienbk0810";
             Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx-login", "root", "laichienbk0810");
            //System.out.println("Connect Sucessfully");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
