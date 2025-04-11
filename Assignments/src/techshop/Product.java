package techshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private int productID;
    private String name;
    private String category;
    private double price;
    private int stockQuantity;

    public Product(int productID, String name, String category, double price, int stockQuantity) {
        this.productID = productID;
        this.name = name;
        this.category = category;
        setPrice(price);
        this.stockQuantity = stockQuantity;
    }

   
    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    
    public int getInventory() {
        return stockQuantity;
    }

    public String getDescription() {
        return category; 
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        if (price > 0) {
            this.price = price;
        } else {
            System.out.println("Invalid price. Price must be greater than zero.");
        }
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setInventory(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

  
    public void updateStock(int quantity) {
        if (quantity >= 0) {
            this.stockQuantity = quantity;
            System.out.println("Stock updated successfully.");
        } else {
            System.out.println("Stock quantity cannot be negative.");
        }
    }

    public boolean isAvailable(int quantity) {
        return stockQuantity >= quantity;
    }

    public void reduceInventory(int quantity) {
        if (isAvailable(quantity)) {
            this.stockQuantity -= quantity;
            System.out.println(quantity + " units deducted from stock. Remaining: " + stockQuantity);
        } else {
            System.out.println("Insufficient stock to reduce.");
        }
    }

  
    public void displayProductDetails() {
        System.out.println("Product ID: " + productID);
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Price: $" + price);
        System.out.println("Stock Quantity: " + stockQuantity);
    }

  
    public double calculateDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            System.out.println("Invalid discount percentage.");
            return price;
        }
        return price - (price * percentage / 100);
    }

 
    public void createProduct(DatabaseConnector db) {
        String sql = "INSERT INTO Products (name, category, price, stockQuantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.name);
            stmt.setString(2, this.category);
            stmt.setDouble(3, this.price);
            stmt.setInt(4, this.stockQuantity);
            stmt.executeUpdate();
            System.out.println("Product added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Product getProductById(int id, DatabaseConnector db) {
        String sql = "SELECT * FROM Products WHERE productID = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                    rs.getInt("productID"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getInt("stockQuantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateProduct(DatabaseConnector db) {
        String sql = "UPDATE Products SET name = ?, category = ?, price = ?, stockQuantity = ? WHERE productID = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.name);
            stmt.setString(2, this.category);
            stmt.setDouble(3, this.price);
            stmt.setInt(4, this.stockQuantity);
            stmt.setInt(5, this.productID);
            stmt.executeUpdate();
            System.out.println("Product updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(DatabaseConnector db) {
        String sql = "DELETE FROM Products WHERE productID = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.productID);
            stmt.executeUpdate();
            System.out.println("Product deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
