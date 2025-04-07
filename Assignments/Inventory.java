package techshop;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private int inventoryID;
    private Product product;
    private int quantityInStock;

    // Static Map to store inventory (acts as an in-memory database)
    private static Map<Integer, Inventory> inventoryDB = new HashMap<>();

    // Constructor
    public Inventory(int inventoryID, Product product, int quantityInStock) {
        this.inventoryID = inventoryID;
        this.product = product;
        this.quantityInStock = quantityInStock;
        inventoryDB.put(product.getProductID(), this); // FIXED: Storing by Product ID for consistency
    }

    // Create - Add a new inventory item
    public static void addInventory(int inventoryID, Product product, int quantity) {
        if (!inventoryDB.containsKey(product.getProductID())) {
            new Inventory(inventoryID, product, quantity);
            System.out.println("Inventory added for product: " + product.getName());
        } else {
            System.out.println("Product ID already exists in inventory.");
        }
    }

    // Read - Retrieve inventory details
    public static Inventory getInventory(int productID) {
        return inventoryDB.get(productID);
    }

    // Update - Modify stock quantity
    public void updateStockQuantity(int newQuantity) {
        this.quantityInStock = newQuantity;
        System.out.println("Stock updated for " + product.getName() + " to " + newQuantity);
    }

    // Delete - Remove inventory item
    public static void removeInventory(int productID) {
        if (inventoryDB.containsKey(productID)) {
            Inventory removedInventory = inventoryDB.remove(productID);
            System.out.println("Inventory removed for product: " + removedInventory.product.getName());
        } else {
            System.out.println("Product ID not found.");
        }
    }

    // Get product
    public Product getProduct() {
        return this.product;
    }

    // Get quantity in stock
    public int getQuantityInStock() {
        return this.quantityInStock;
    }

    // Add stock
    public void addToInventory(int quantity) {
        this.quantityInStock += quantity;
        System.out.println(quantity + " units added to " + product.getName());
    }

    // Remove stock
    public void removeFromInventory(int quantity) {
        if (quantityInStock >= quantity) {
            this.quantityInStock -= quantity;
            System.out.println(quantity + " units removed from " + product.getName());
        } else {
            System.out.println("Not enough stock to remove. Available stock: " + quantityInStock);
        }
    }

    // Check availability
    public boolean isProductAvailable(int quantityToCheck) {
        return quantityInStock >= quantityToCheck;
    }

    // Get total inventory value
    public double getInventoryValue() {
        return product.getPrice() * quantityInStock;
    }

    // List all products in inventory
    public static void listAllProducts() {
        if (inventoryDB.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            for (Inventory inv : inventoryDB.values()) {
                System.out.println("Product ID: " + inv.product.getProductID());
                System.out.println("Product Name: " + inv.product.getName());
                System.out.println("Quantity In Stock: " + inv.quantityInStock);
                System.out.println("--------------------------------");
            }
        }
    }
}
