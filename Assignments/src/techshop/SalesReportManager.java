package techshop;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesReportManager {
    private Connection conn;

    public SalesReportManager(Connection conn) {
        this.conn = conn;
    }

  
    public void getTotalRevenue() {
        String query = "SELECT SUM(TotalAmount) AS TotalRevenue FROM Orders";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                System.out.println("Total Revenue: $" + rs.getDouble("TotalRevenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  
    public void getBestSellingProduct() {
        String query = """
            SELECT p.ProductName, SUM(od.Quantity) AS TotalQuantityOrdered
            FROM OrderDetails od
            JOIN Products p ON od.ProductID = p.ProductID
            GROUP BY p.ProductName
            ORDER BY TotalQuantityOrdered DESC
            LIMIT 1;
        """;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                System.out.println("Best-Selling Product: " + rs.getString("ProductName") +
                        " (Sold: " + rs.getInt("TotalQuantityOrdered") + " units)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTopCustomer() {
        String query = """
            SELECT c.FirstName, c.LastName, SUM(o.TotalAmount) AS TotalSpent
            FROM Customers c
            JOIN Orders o ON c.CustomerID = o.CustomerID
            GROUP BY c.CustomerID
            ORDER BY TotalSpent DESC
            LIMIT 1;
        """;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                System.out.println("Top Customer: " + rs.getString("FirstName") + " " + rs.getString("LastName") +
                        " (Spent: $" + rs.getDouble("TotalSpent") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void getTopCategory() {
        String query = """
            SELECT c.CategoryName, SUM(od.Quantity) AS TotalQuantityOrdered
            FROM OrderDetails od
            JOIN Products p ON od.ProductID = p.ProductID
            JOIN Categories c ON p.CategoryID = c.CategoryID
            GROUP BY c.CategoryName
            ORDER BY TotalQuantityOrdered DESC
            LIMIT 1;
        """;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                System.out.println("Most Popular Category: " + rs.getString("CategoryName") +
                        " (Sold: " + rs.getInt("TotalQuantityOrdered") + " units)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    public void getAverageOrderValue() {
        String query = "SELECT AVG(TotalAmount) AS AverageOrderValue FROM Orders";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                System.out.println("Average Order Value: $" + rs.getDouble("AverageOrderValue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
