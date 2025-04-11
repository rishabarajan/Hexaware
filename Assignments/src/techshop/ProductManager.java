package techshop;

import java.util.*;
import java.util.stream.*;

public class ProductManager {
    private List<Product> productList = new ArrayList<>();

    
    public void addProduct(Product product) throws ProductException {
        if (product == null || product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ProductException("Invalid product details.");
        }

       
        for (Product p : productList) {
            if (p.getProductID() == product.getProductID()) {
                throw new ProductException("Duplicate product ID: " + product.getProductID());
            }

           
            if (p.getName().equalsIgnoreCase(product.getName())) {
                throw new ProductException("Duplicate product name: " + product.getName());
            }
        }

        productList.add(product);
        System.out.println("Product added successfully: " + product.getName());
    }

 
    public void updateProduct(int productId, String newName, double newPrice, String newDescription, int newInventory) throws ProductException {
        Optional<Product> productOpt = productList.stream()
                .filter(p -> p.getProductID() == productId)
                .findFirst();

        if (productOpt.isEmpty()) {
            throw new ProductException("Product not found with ID: " + productId);
        }

        Product product = productOpt.get();
        if (newName != null && !newName.trim().isEmpty()) {
            product.setName(newName);
        }
        if (newPrice > 0) {
            product.setPrice(newPrice);
        }
        if (newInventory >= 0) {
            product.setInventory(newInventory);  // Update inventory as well
        }

        System.out.println("Product updated successfully: " + product.getName());
    }

   
    public void removeProduct(int productId, List<Order> orders) throws ProductException {
        Optional<Product> productOpt = productList.stream()
                .filter(p -> p.getProductID() == productId)
                .findFirst();

        if (productOpt.isEmpty()) {
            throw new ProductException("Product not found with ID: " + productId);
        }

    
        for (Order order : orders) {
            if (order.getProductReference() != null && order.getProductReference().getProductID() == productId) {
                throw new ProductException("Cannot remove product. It is associated with an existing order.");
            }
        }

        productList.remove(productOpt.get());
        System.out.println("Product removed successfully.");
    }

   
    public void listProducts() {
        if (productList.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        System.out.println("Available Products:");
        for (Product product : productList) {
            System.out.println("ID: " + product.getProductID() + ", Name: " + product.getName() + ", Price: " + product.getPrice() + ", Inventory: " + product.getInventory());
        }
    }

  
    public List<Product> searchProductsByName(String name) throws ProductException {
        if (name == null || name.trim().isEmpty()) {
            throw new ProductException("Search criteria cannot be null or empty.");
        }

        List<Product> result = productList.stream()
                                          .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                                          .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new ProductException("No products found for the name: " + name);
        }

        return result;
    }

       public List<Product> searchProductsByCategory(String category) throws ProductException {
        if (category == null || category.trim().isEmpty()) {
            throw new ProductException("Search criteria cannot be null or empty.");
        }

        List<Product> result = productList.stream()
                                          .filter(p -> p.getDescription().toLowerCase().contains(category.toLowerCase())) // Assuming description holds category info
                                          .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new ProductException("No products found in category: " + category);
        }

        return result;
    }

    public List<Product> searchProductsByPriceRange(double minPrice, double maxPrice) throws ProductException {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            throw new ProductException("Invalid price range.");
        }

        List<Product> result = productList.stream()
                                          .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                                          .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new ProductException("No products found in the price range: " + minPrice + " to " + maxPrice);
        }

        return result;
    }

    public void processOrder(int productId, int quantity) throws ProductException {
        Optional<Product> productOpt = productList.stream()
                .filter(p -> p.getProductID() == productId)
                .findFirst();

        if (productOpt.isEmpty()) {
            throw new ProductException("Product not found with ID: " + productId);
        }

        Product product = productOpt.get();
        product.reduceInventory(quantity);  
    }
}
