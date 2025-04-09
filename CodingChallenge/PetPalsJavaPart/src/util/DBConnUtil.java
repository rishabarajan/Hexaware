package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.FileInputStream;

public class DBConnUtil {

    public static Connection getConnection(String filename) {
        Connection conn = null;
        try {
            // IMPORTANT: Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(filename);
            props.load(fis);

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            System.out.println("Connecting to DB: " + url);

            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("Failed to connect to DB:");
            e.printStackTrace();
        }
        return conn;
    }
}
