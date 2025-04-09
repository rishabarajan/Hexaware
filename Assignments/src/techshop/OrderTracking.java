package techshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class OrderTracking {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Order ID: ");
        int orderId = scanner.nextInt();

        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection conn = dbConnector.openConnection();

        if (conn != null) {
            try {
                String query = "SELECT o.OrderID, o.OrderDate, c.FirstName, c.LastName, o.Status " +
                               "FROM Orders o " +
                               "JOIN Customers c ON o.CustomerID = c.CustomerID " +
                               "WHERE o.OrderID = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, orderId);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Order ID: " + rs.getInt("OrderID"));
                    System.out.println("Customer: " + rs.getString("FirstName") + " " + rs.getString("LastName"));
                    System.out.println("Order Date: " + rs.getTimestamp("OrderDate"));
                    System.out.println("Status: " + rs.getString("Status"));
                } else {
                    System.out.println("No order found with ID: " + orderId);
                }

                // Close resources
                rs.close();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dbConnector.closeConnection();
            }
        } else {
            System.out.println("Failed to connect to the database.");
        }

        scanner.close();
    }
}
