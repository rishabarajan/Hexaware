package techshop;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

    private static final String LOG_FILE = "error_logs.txt";

    
    public static void logError(String message) throws LoggingException {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE, true); PrintWriter writer = new PrintWriter(fileWriter)) {
            writer.println(getCurrentTimestamp() + " - Error: " + message);
        } catch (IOException e) {
            throw new LoggingException("Error writing to log file: " + e.getMessage(), e);
        }
    }

    
    public static void logSuccess(String message) throws LoggingException {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE, true); PrintWriter writer = new PrintWriter(fileWriter)) {
            writer.println(getCurrentTimestamp() + " - Success: " + message);
        } catch (IOException e) {
            throw new LoggingException("Error writing to log file: " + e.getMessage(), e);
        }
    }

    
    private static String getCurrentTimestamp() {
        return java.time.LocalDateTime.now().toString();
    }
}
