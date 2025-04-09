package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {

    public static String getPropertyString() {
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream("db.properties");
          //  InputStream is = PropertyUtil.class.getClassLoader().getResourceAsStream("db.properties");
           // props.load(is);

            props.load(fis);
            String host = props.getProperty("host");
            String port = props.getProperty("port");
            String dbname = props.getProperty("dbname");
            String user = props.getProperty("username");
            String pass = props.getProperty("password");

            return "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?user=" + user + "&password=" + pass;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
