package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                String connStr = PropertyUtil.getPropertyString();
                connection = DriverManager.getConnection(connStr);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
