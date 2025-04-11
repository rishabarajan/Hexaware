package techshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;
import java.util.TreeMap;

public class InventoryManager {
    private SortedMap<Integer, Inventory> inventoryMap = new TreeMap<>(); 
    private DatabaseConnector dbConnector; 

    public InventoryManager() {
        this.dbConnector = new DatabaseConnector();
        loadInventoryFromDatabase(); 
    }

    private void loadInventoryFromDatabase() {
        String query = "SELECT * FROM Products";
        try (Connection conn = dbConnector.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int productID = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String category = rs.getString("Category");
                double price = rs.getDouble("Price");
                int stockQuantity = rs.getInt("StockQuantity");

         
                int inventoryID = generateInventoryID(); 

                Product product = new Product(productID, productName, category, price, stockQuantity);

                Inventory inventory = new Inventory(inventoryID, product, stockQuantity);

                inventoryMap.put(productID, inventory);
            }
            System.out.println("Inventory loaded from database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProductToInventory(Inventory inventory) {
        if (inventory != null && inventory.getProduct() != null) {
            int productID = inventory.getProduct().getProductID();
            inventoryMap.put(productID, inventory);

            String query = "INSERT INTO Products (ProductID, ProductName, Category, Price, StockQuantity) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = dbConnector.openConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, productID);
                pstmt.setString(2, inventory.getProduct().getName());
                pstmt.setString(3, inventory.getProduct().getCategory());
                pstmt.setDouble(4, inventory.getProduct().getPrice());
                pstmt.setInt(5, inventory.getQuantityInStock());

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Product added to inventory and database.");
                }
                else {
                    System.out.println("Failed to add product");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid inventory details.");
        }
    }

    public void updateInventory(int productID, int quantity) {
        Inventory inventory = inventoryMap.get(productID);
        if (inventory != null) {
            inventory.addToInventory(quantity);

            String query = "UPDATE Products SET StockQuantity = ? WHERE ProductID = ?";
            try (Connection conn = dbConnector.openConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, inventory.getQuantityInStock());
                pstmt.setInt(2, productID);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Inventory updated for Product ID " + productID + ".");
                } else {
                    System.out.println("Failed to update inventory.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Product ID " + productID + " not found in inventory.");
        }
    }

    public void removeProductFromInventory(int productID, int quantity) {
        Inventory inventory = inventoryMap.get(productID);
        if (inventory != null) {
            inventory.removeFromInventory(quantity);

            if (inventory.getQuantityInStock() == 0) {
                inventoryMap.remove(productID);

                String query = "DELETE FROM Products WHERE ProductID = ?";
                try (Connection conn = dbConnector.openConnection();
                     PreparedStatement pstmt = conn.prepareStatement(query)) {

                    pstmt.setInt(1, productID);
                    int rows = pstmt.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Product removed from inventory & database.");
                    } else {
                        System.out.println("Failed to remove product.");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Inventory updated for Product ID " + productID + " with new quantity: " + inventory.getQuantityInStock());
            }
        } else {
            System.out.println("Product ID " + productID + " not found in inventory.");
        }
    }

    public void checkProductAvailability(int productID, int quantityToCheck) {
        Inventory inventory = inventoryMap.get(productID);
        if (inventory != null) {
            if (inventory.isProductAvailable(quantityToCheck)) {
                System.out.println("Product ID " + productID + " is available.");
            } else {
                System.out.println("Product ID " + productID + " is not available in the requested quantity.");
            }
        } else {
            System.out.println("Product ID " + productID + " not found in inventory.");
        }
    }

    public void listAllInventory() {
        if (inventoryMap.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Inventory List:");
            Inventory.listAllProducts();
        }
    }

    public void processOrder(int productID, int requestedQuantity) throws InsufficientStockException {
        Inventory inventory = inventoryMap.get(productID);
        if (inventory != null) {
            if (inventory.isProductAvailable(requestedQuantity)) {
                inventory.removeFromInventory(requestedQuantity);

                String query = "UPDATE Products SET StockQuantity = ? WHERE ProductID = ?";
                try (Connection conn = dbConnector.openConnection();
                     PreparedStatement pstmt = conn.prepareStatement(query)) {

                    pstmt.setInt(1, inventory.getQuantityInStock());
                    pstmt.setInt(2, productID);
                    pstmt.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                System.out.println("Order processed for Product ID " + productID + ".");
            } else {
                throw new InsufficientStockException("Insufficient stock for Product ID " + productID + ".");
            }
        } else {
            System.out.println("Product ID " + productID + " not found.");
        }
       

    }
    private int generateInventoryID() {
        return (int) (Math.random() * 10000); 
    }

}


