import java.util.*;

class Customer {
    private int customerID;
    private String firstName, lastName, email, phone, address;
    private List<Order> orders = new ArrayList<>();

    public Customer(int customerID, String firstName, String lastName, String email, String phone, String address) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        setEmail(email);
        setPhone(phone);
        this.address = address;
    }

    public int getCustomerID() { return customerID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public List<Order> getOrders() { return orders; }

    public void setEmail(String email) {
        if (email.contains("@")) this.email = email;
        else System.out.println("Invalid email!");
    }

    public void setPhone(String phone) {
        if (phone.matches("\\d{10}")) this.phone = phone;
        else System.out.println("Invalid phone number!");
    }

    public void setAddress(String address) { this.address = address; }

    public int calculateTotalOrders() { return orders.size(); }

    public void getCustomerDetails() {
        System.out.println("Customer ID: " + customerID + "\nName: " + firstName + " " + lastName +
                "\nEmail: " + email + "\nPhone: " + phone + "\nAddress: " + address +
                "\nTotal Orders: " + calculateTotalOrders());
    }
}

class Product {
    private int productID;
    private String productName, description;
    private double price;

    public Product(int productID, String productName, String description, double price) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        setPrice(price);
    }

    public int getProductID() { return productID; }
    public String getProductName() { return productName; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }

    public void setDescription(String description) { this.description = description; }

    public void setPrice(double price) {
        if (price >= 0) this.price = price;
        else System.out.println("Price cannot be negative!");
    }

    public void getProductDetails() {
        System.out.println("Product ID: " + productID + "\nName: " + productName +
                "\nDescription: " + description + "\nPrice: $" + price);
    }
}

class Order {
    private int orderID;
    private Customer customer;
    private Date orderDate;
    private double totalAmount;
    private String status;

    public Order(int orderID, Customer customer) {
        this.orderID = orderID;
        this.customer = customer;
        this.orderDate = new Date();
        this.totalAmount = 0;
        this.status = "Processing";
        customer.getOrders().add(this);
    }

    public int getOrderID() { return orderID; }
    public Customer getCustomer() { return customer; }
    public Date getOrderDate() { return orderDate; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public void calculateTotalAmount(List<OrderDetail> orderDetails) {
        totalAmount = 0;
        for (OrderDetail detail : orderDetails) {
            totalAmount += detail.calculateSubtotal();
        }
    }

    public void getOrderDetails(List<OrderDetail> orderDetails) {
        System.out.println("Order ID: " + orderID + "\nCustomer: " + customer.getFirstName() + " " + customer.getLastName() +
                "\nDate: " + orderDate + "\nStatus: " + status + "\nTotal Amount: $" + totalAmount);
        for (OrderDetail detail : orderDetails) {
            detail.getOrderDetailInfo();
        }
    }
}

class OrderDetail {
    private int orderDetailID;
    private Order order;
    private Product product;
    private int quantity;

    public OrderDetail(int orderDetailID, Order order, Product product, int quantity) {
        this.orderDetailID = orderDetailID;
        this.order = order;
        this.product = product;
        setQuantity(quantity);
    }

    public double calculateSubtotal() {
        return quantity * product.getPrice();
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) this.quantity = quantity;
        else System.out.println("Quantity must be positive!");
    }

    public void getOrderDetailInfo() {
        System.out.println("Product: " + product.getProductName() + " | Quantity: " + quantity +
                " | Subtotal: $" + calculateSubtotal());
    }
}

class Inventory {
    private int inventoryID;
    private Product product;
    private int quantityInStock;

    public Inventory(int inventoryID, Product product, int quantityInStock) {
        this.inventoryID = inventoryID;
        this.product = product;
        setQuantityInStock(quantityInStock);
    }

    public void setQuantityInStock(int quantity) {
        if (quantity >= 0) this.quantityInStock = quantity;
        else System.out.println("Stock quantity cannot be negative!");
    }

    public void addToInventory(int quantity) {
        setQuantityInStock(this.quantityInStock + quantity);
    }

    public void removeFromInventory(int quantity) {
        if (quantityInStock >= quantity) setQuantityInStock(quantityInStock - quantity);
        else System.out.println("Not enough stock available!");
    }

    public boolean isProductAvailable(int quantityToCheck) {
        return quantityInStock >= quantityToCheck;
    }

    public void getInventoryValue() {
        System.out.println("Total Value: $" + (product.getPrice() * quantityInStock));
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
