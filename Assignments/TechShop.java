import java.util.*;

class Customer {
    int customerID;
    String firstName, lastName, email, phone, address;
    List<Order> orders = new ArrayList<>();

    public Customer(int customerID, String firstName, String lastName, String email, String phone, String address) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int calculateTotalOrders() {
        return orders.size();
    }

    public void getCustomerDetails() {
        System.out.println("Customer ID: " + customerID + "\nName: " + firstName + " " + lastName +
                "\nEmail: " + email + "\nPhone: " + phone + "\nAddress: " + address +
                "\nTotal Orders: " + calculateTotalOrders());
    }

    public void updateCustomerInfo(String email, String phone, String address) {
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}

class Product {
    int productID;
    String productName, description;
    double price;

    public Product(int productID, String productName, String description, double price) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    public void getProductDetails() {
        System.out.println("Product ID: " + productID + "\nName: " + productName + 
                "\nDescription: " + description + "\nPrice: $" + price);
    }

    public void updateProductInfo(String description, double price) {
        this.description = description;
        this.price = price;
    }
}

class Order {
    int orderID;
    Customer customer;
    Date orderDate;
    double totalAmount;
    String status;

    public Order(int orderID, Customer customer) {
        this.orderID = orderID;
        this.customer = customer;
        this.orderDate = new Date();
        this.totalAmount = 0;
        this.status = "Processing";
        customer.orders.add(this);
    }

    public void calculateTotalAmount(List<OrderDetail> orderDetails) {
        totalAmount = 0;
        for (OrderDetail detail : orderDetails) {
            totalAmount += detail.calculateSubtotal();
        }
    }

    public void getOrderDetails(List<OrderDetail> orderDetails) {
        System.out.println("Order ID: " + orderID + "\nCustomer: " + customer.firstName + " " + customer.lastName +
                "\nDate: " + orderDate + "\nStatus: " + status + "\nTotal Amount: $" + totalAmount);
        for (OrderDetail detail : orderDetails) {
            detail.getOrderDetailInfo();
        }
    }

    public void updateOrderStatus(String status) {
        this.status = status;
    }
}

class OrderDetail {
    int orderDetailID;
    Order order;
    Product product;
    int quantity;

    public OrderDetail(int orderDetailID, Order order, Product product, int quantity) {
        this.orderDetailID = orderDetailID;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public double calculateSubtotal() {
        return quantity * product.price;
    }

    public void getOrderDetailInfo() {
        System.out.println("Product: " + product.productName + " | Quantity: " + quantity + 
                " | Subtotal: $" + calculateSubtotal());
    }
}

class Inventory {
    int inventoryID;
    Product product;
    int quantityInStock;

    public Inventory(int inventoryID, Product product, int quantityInStock) {
        this.inventoryID = inventoryID;
        this.product = product;
        this.quantityInStock = quantityInStock;
    }

    public void addToInventory(int quantity) {
        quantityInStock += quantity;
    }

    public void removeFromInventory(int quantity) {
        if (quantityInStock >= quantity) {
            quantityInStock -= quantity;
        } else {
            System.out.println("Not enough stock available!");
        }
    }

    public boolean isProductAvailable(int quantityToCheck) {
        return quantityInStock >= quantityToCheck;
    }

    public void getInventoryValue() {
        System.out.println("Total Value: $" + (product.price * quantityInStock));
    }
}

public class TechShop {
    public static void main(String[] args) {
        
        Customer c1 = new Customer(1, "John", "Doe", "john@example.com", "1234567890", "123 Street, NY");
        Product p1 = new Product(101, "Smartphone", "Latest model with 5G", 699.99);
        Inventory inv1 = new Inventory(1, p1, 50);

        Order o1 = new Order(1001, c1);
        OrderDetail od1 = new OrderDetail(1, o1, p1, 2);
        
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(od1);
        
        o1.calculateTotalAmount(orderDetails);
        c1.getCustomerDetails();
        o1.getOrderDetails(orderDetails);
        p1.getProductDetails();
        inv1.getInventoryValue();
    }
}
