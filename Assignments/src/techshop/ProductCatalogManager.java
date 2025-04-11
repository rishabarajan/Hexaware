package techshop;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCatalogManager {
    private Connection connection;

    public ProductCatalogManager() {
        DatabaseConnector dbConnector = new DatabaseConnector();
        this.connection = dbConnector.openConnection();
    }

   
    public void addProduct(String name, String description, double price, int categoryID) {
        String query = "INSERT INTO Products (ProductName, Description, Price, CategoryID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setInt(4, categoryID);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Product added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void updateProduct(int productID, String name, String description, double price) {
        String query = "UPDATE Products SET ProductName = ?, Description = ?, Price = ? WHERE ProductID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setInt(4, productID);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    public void deleteProduct(int productID) {
        String query = "DELETE FROM Products WHERE ProductID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productID);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all products
    public void displayProducts() {
        String query = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, c.CategoryName FROM Products p " +
                       "LEFT JOIN Categories c ON p.CategoryID = c.CategoryID";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            System.out.println("Product Catalog:");
            System.out.printf("%-5s %-20s %-40s %-10s %-15s%n", "ID", "Name", "Description", "Price", "Category");
            System.out.println("--------------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d %-20s %-40s %-10.2f %-15s%n",
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getDouble("Price"),
                        rs.getString("CategoryName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductCatalogManager pcm = new ProductCatalogManager();

        while (true) {
            System.out.println("\nProduct Catalog Management:");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Display Products");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    System.out.print("Enter Product Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Category ID: ");
                    int categoryID = scanner.nextInt();
                    pcm.addProduct(name, description, price, categoryID);
                    break;

                case 2:
                    System.out.print("Enter Product ID to Update: ");
                    int productID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter New Product Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New Description: ");
                    String newDescription = scanner.nextLine();
                    System.out.print("Enter New Price: ");
                    double newPrice = scanner.nextDouble();
                    pcm.updateProduct(productID, newName, newDescription, newPrice);
                    break;

                case 3:
                    System.out.print("Enter Product ID to Delete: ");
                    int deleteID = scanner.nextInt();
                    pcm.deleteProduct(deleteID);
                    break;

                case 4:
                    pcm.displayProducts();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
