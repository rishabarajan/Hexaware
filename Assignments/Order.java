package techshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Order {
    private int orderID;
    private int customerId;
    private List<Product> products;
    private Date orderDate;
    private double totalAmount;
    private String status; // "Pending", "Shipped", "Delivered", etc.
    private Product productReference;
  
    private int quantity;
    private int version;

    public Product getProductReference() 
    { return productReference; }
    public int getQuantity() { return quantity; }
    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }
    public Order(int orderID, int customerId, List<Product> products, Date orderDate, double totalAmount, String status) {
        this.orderID = orderID;
        this.customerId = customerId;
        this.products = products;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getter methods
    public int getOrderID() {
        return orderID;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    // Setter methods
    public void setStatus(String status) {
        this.status = status;
    }

    public void displayOrderDetails() {
        System.out.println("Order ID: " + orderID);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("Status: " + status);
        System.out.println("Products:");
        for (Product product : products) {
            product.displayProductDetails();
            System.out.println("------------------");
        }
    }

    public double calculateTotalAmount() {
        double sum = 0;
        for (Product product : products) {
            sum += product.getPrice();
        }
        return sum;
    }

    // CRUD Operations
    public void createOrder(DatabaseConnector db) {
        Connection conn = db.openConnection();
        String sql = "INSERT INTO Orders (customerID, orderDate, totalAmount, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, this.customerId);
            stmt.setDate(2, new java.sql.Date(this.orderDate.getTime()));
            stmt.setDouble(3, this.totalAmount);
            stmt.setString(4, this.status);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.orderID = rs.getInt(1);
                System.out.println("Order created successfully with Order ID: " + this.orderID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Order getOrderById(int id, DatabaseConnector db) {
        Connection conn = db.openConnection();
        String sql = "SELECT * FROM Orders WHERE orderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Order(
                    rs.getInt("orderID"),
                    rs.getInt("customerID"),
                    null, // Product list retrieval would be implemented separately
                    rs.getDate("orderDate"),
                    rs.getDouble("totalAmount"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateOrder(DatabaseConnector db) {
        Connection conn = db.openConnection();
        String sql = "UPDATE Orders SET status = ?, totalAmount = ? WHERE orderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.status);
            stmt.setDouble(2, this.totalAmount);
            stmt.setInt(3, this.orderID);
            stmt.executeUpdate();
            System.out.println("Order updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(DatabaseConnector db) {
        Connection conn = db.openConnection();
        String sql = "DELETE FROM Orders WHERE orderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.orderID);
            stmt.executeUpdate();
            System.out.println("Order deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
