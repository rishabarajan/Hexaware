package techshop;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ProductSearchAndRecommendation {
    private DatabaseConnector databaseConnector;

    public ProductSearchAndRecommendation(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    // Search products by name or category
    public List<String> searchProducts(String searchTerm) {
        List<String> products = new ArrayList<>();
        String query = "SELECT ProductName, Description, Price FROM Products p " +
                       "LEFT JOIN Categories c ON p.CategoryID = c.CategoryID " +
                       "WHERE p.ProductName LIKE ? OR c.CategoryName LIKE ?";

        try (Connection conn = databaseConnector.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setString(2, "%" + searchTerm + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String productDetails = rs.getString("ProductName") + " - " + 
                                        rs.getString("Description") + " - ₹" + 
                                        rs.getDouble("Price");
                products.add(productDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Recommend top-selling products
    public List<String> recommendProducts() {
        List<String> recommendedProducts = new ArrayList<>();
        String query = "SELECT p.ProductName, SUM(od.Quantity) as TotalSold " +
                       "FROM OrderDetails od " +
                       "JOIN Products p ON od.ProductID = p.ProductID " +
                       "GROUP BY p.ProductName " +
                       "ORDER BY TotalSold DESC " +
                       "LIMIT 5"; // Top 5 products

        try (Connection conn = databaseConnector.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String product = rs.getString("ProductName") + " - Sold: " + rs.getInt("TotalSold");
                recommendedProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recommendedProducts;
    }

    public static void main(String[] args) {
        DatabaseConnector dbConnector = new DatabaseConnector();
        ProductSearchAndRecommendation searchEngine = new ProductSearchAndRecommendation(dbConnector);

        System.out.println("Searching for 'Laptop'...");
        List<String> searchResults = searchEngine.searchProducts("Laptop");
        searchResults.forEach(System.out::println);

        System.out.println("\nTop Recommended Products:");
        List<String> recommendations = searchEngine.recommendProducts();
        recommendations.forEach(System.out::println);

        dbConnector.closeConnection();
    }
}
