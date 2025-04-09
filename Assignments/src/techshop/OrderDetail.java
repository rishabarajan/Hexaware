package techshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetail {
    private int orderDetailID;
    public Order order;
    private Product product;
    private int quantity;

    public OrderDetail(int orderDetailID, Order order, Product product, int quantity) {
        this.orderDetailID = orderDetailID;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public double calculateSubtotal() {
        return product.getPrice() * quantity;
    }

    public void getOrderDetailInfo() {
        System.out.println("Order Detail ID: " + orderDetailID);
        System.out.println("Product: " + product.getName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Subtotal: " + calculateSubtotal());
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public void addDiscount(double discount) {
        System.out.println("Discount of " + discount + " applied");
    }

    // ✅ **Fixed: Uses `isAvailable` from `Product`**
    public boolean isProductAvailable() {
        return product.isAvailable(quantity);
    }

    // ✅ **Fixed: Uses `reduceInventory` from `Product`**
    public void reduceProductInventory() {
        if (isProductAvailable()) {
            product.reduceInventory(quantity);
        } else {
            System.out.println("Product not available in the required quantity.");
        }
    }

    // CRUD Operations
    public void createOrderDetail(DatabaseConnector db) {
        String sql = "INSERT INTO OrderDetails (orderID, productID, quantity) VALUES (?, ?, ?)";
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, this.order.getOrderID());
            stmt.setInt(2, this.product.getProductID());
            stmt.setInt(3, this.quantity);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.orderDetailID = rs.getInt(1);
                System.out.println("OrderDetail created successfully with ID: " + this.orderDetailID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderDetail(DatabaseConnector db) {
        String sql = "UPDATE OrderDetails SET quantity = ? WHERE orderDetailID = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.quantity);
            stmt.setInt(2, this.orderDetailID);
            stmt.executeUpdate();
            System.out.println("OrderDetail updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderDetail(DatabaseConnector db) {
        String sql = "DELETE FROM OrderDetails WHERE orderDetailID = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.orderDetailID);
            stmt.executeUpdate();
            System.out.println("OrderDetail deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
