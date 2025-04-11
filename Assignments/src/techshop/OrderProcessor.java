package techshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OrderProcessor {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/TechShop";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "password"; 

    public void processOrder(Order order) throws IncompleteOrderException {
        if (order == null) {
            logErrorAndThrow("Attempted to process a null order.", "Order is null. Cannot process.");
        }

        Product product = order.getProductReference();

        if (product == null || product.getName() == null || product.getName().trim().isEmpty()) {
            logErrorAndThrow("Order is missing product reference.", "Order is incomplete: Product reference is missing.");
        }

        System.out.println("Processing order for product: " + product.getName());

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            conn.setAutoCommit(false); 

            try {
                int productId = getProductId(conn, product.getName());
                int customerId = order.getCustomerId(); 
                int quantity = order.getQuantity();

                if (!isProductAvailable(conn, productId, quantity)) {
                    System.out.println("Insufficient stock for product: " + product.getName());
                    return;
                }

                int orderId = insertOrder(conn, customerId);
                insertOrderDetails(conn, orderId, productId, quantity);
                updateInventory(conn, productId, quantity);

                conn.commit(); 
                Logger.logSuccess("Order processed successfully for product: " + product.getName());
            } catch (SQLException e) {
                conn.rollback();
                Logger.logError("Order processing failed: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException | LoggingException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private int getProductId(Connection conn, String productName) throws  SQLException {
        String query = "SELECT ProductID FROM Products WHERE ProductName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ProductID");
            } else {
                throw new SQLException("Product not found: " + productName);
            }
        }
    }

    private boolean isProductAvailable(Connection conn, int productId, int quantity) throws SQLException {
        String query = "SELECT QuantityInStock FROM Inventory WHERE ProductID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("QuantityInStock") >= quantity;
            }
        }
        return false;
    }

    private int insertOrder(Connection conn, int customerId) throws SQLException {
        String query = "INSERT INTO Orders (CustomerID, TotalAmount) VALUES (?, 0)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("Failed to insert order.");
        }
    }

    private void insertOrderDetails(Connection conn, int orderId, int productId, int quantity) throws SQLException {
        String query = "INSERT INTO OrderDetails (OrderID, ProductID, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        }
    }

    private void updateInventory(Connection conn, int productId, int quantity) throws SQLException {
        String query = "UPDATE Inventory SET QuantityInStock = QuantityInStock - ? WHERE ProductID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }

    public void processOrderConcurrently(Order order, int expectedVersion) throws ConcurrencyException, IncompleteOrderException {
        if (order == null) {
            logErrorAndThrow("Attempted to process a null order.", "Order is null. Cannot process.");
        }

        Product product = order.getProductReference();

        if (product == null || product.getName() == null || product.getName().trim().isEmpty()) {
            logErrorAndThrow("Order is missing product reference.", "Order is incomplete: Product reference is missing.");
        }

        synchronized (order) {
            if (order.getVersion() != expectedVersion) {
                try {
                    Logger.logError("Concurrency conflict detected for Order ID " + order.getVersion());
                } catch (LoggingException e) {
                    System.err.println("Logging failed: " + e.getMessage());
                }
                throw new ConcurrencyException("Order was modified by another user. Please retry.");
            }

            System.out.println("Processing order for product: " + product.getName());
            order.setVersion(order.getVersion() + 1);

            try {
                Logger.logSuccess("Successfully processed order for product - " + product.getName() +
                        ". Updated order version to " + order.getVersion());
            } catch (LoggingException e) {
                System.err.println("Logging failed: " + e.getMessage());
            }
        }
    }

    private void logErrorAndThrow(String logMessage, String exceptionMessage) throws IncompleteOrderException {
        try {
            Logger.logError(logMessage);
        } catch (LoggingException e) {
            System.err.println("Logging failed: " + e.getMessage());
        }
        throw new IncompleteOrderException(exceptionMessage);
    }
}
