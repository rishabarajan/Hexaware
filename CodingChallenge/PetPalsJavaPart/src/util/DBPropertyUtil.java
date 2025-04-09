package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    public static Properties loadProperties(String propertyFileName) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(propertyFileName)) {
            props.load(fis);
        } catch (IOException e) {
            System.out.println("Error loading DB properties: " + e.getMessage());
        }
        return props;
    }
}
