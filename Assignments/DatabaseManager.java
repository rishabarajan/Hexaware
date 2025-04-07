package techshop;


public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/techshop";
    private static final String USER = "root";
    private static final String PASSWORD = "Rishi4444!"; // Placeholder for actual DB password

    // Simulated method to establish a database connection with retry logic
    public static void getConnection() throws SQLException {
        int attempts = 0;
        while (attempts < 3) {
            try {
                // Simulate a database connection attempt (without actual DB)
                System.out.println("Attempting to connect to the database...");
                throw new SQLException("Simulated database connection failure.");

            } catch (SQLException e) {
                attempts++;
                try {
                    // Log the error (in reality, this would be logged to a file)
                    Logger.logError("Database connection failed (Attempt " + attempts + "): " + e.getMessage());
                } catch (LoggingException logEx) {
                    throw new SQLException("Error logging failed connection attempt", logEx);
                }

                if (attempts == 3) {
                    throw new SQLException("Database connection failed after 3 attempts: " + e.getMessage());
                }
                try {
                    Thread.sleep(2000); // Wait before retrying
                } catch (InterruptedException ignored) {}
            }
        }
    }

    // Simulated method to execute a query
    public static void executeQuery(String query) throws SQLException {
        try {
            // Simulate a database query execution
            System.out.println("Executing query: " + query);
            throw new SQLException("Simulated query execution failure.");

        } catch (SQLException e) {
            try {
                // Log the error (in reality, this would be logged to a file)
                Logger.logError("Database query failed: " + e.getMessage());
            } catch (LoggingException logEx) {
                throw new SQLException("Error logging query failure", logEx);
            }
            throw new SQLException("Database query execution failed", e);
        }
    }
}
