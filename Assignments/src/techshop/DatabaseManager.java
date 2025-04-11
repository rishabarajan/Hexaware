package techshop;


public class DatabaseManager {

    
   
    public static void getConnection() throws SQLException {
        int attempts = 0;
        while (attempts < 3) {
            try {
               
                System.out.println("Attempting to connect to the database...");
                throw new SQLException("Simulated database connection failure.");

            } catch (SQLException e) {
                attempts++;
                try {
                    
                    Logger.logError("Database connection failed (Attempt " + attempts + "): " + e.getMessage());
                } catch (LoggingException logEx) {
                    throw new SQLException("Error logging failed connection attempt", logEx);
                }

                if (attempts == 3) {
                    throw new SQLException("Database connection failed after 3 attempts: " + e.getMessage());
                }
                try {
                    Thread.sleep(2000); 
                } catch (InterruptedException ignored) {}
            }
        }
    }

    
    public static void executeQuery(String query) throws SQLException {
        try {
            
            System.out.println("Executing query: " + query);
            throw new SQLException("Simulated query execution failure.");

        } catch (SQLException e) {
            try {
               
                Logger.logError("Database query failed: " + e.getMessage());
            } catch (LoggingException logEx) {
                throw new SQLException("Error logging query failure", logEx);
            }
            throw new SQLException("Database query execution failed", e);
        }
    }
}
