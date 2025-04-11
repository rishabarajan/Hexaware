package techshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    public Customer(int customerID, String firstName, String lastName, String email, String phone, String address) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        try {
            setEmail(email);
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
        this.phone = phone;
        this.address = address;
    }

    
    public int getCustomerID() {
        return customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setEmail(String email) throws InvalidDataException {
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            throw new InvalidDataException("Invalid email format: " + email);
        }
    }

    public void updateCustomerInfo(String newEmail, String newPhone, String newAddress) {
        try {
            setEmail(newEmail);
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
        this.phone = newPhone;
        this.address = newAddress;
    }

    public void getCustomerDetails() {
        System.out.println("Customer ID: " + customerID);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Address: " + address);
    }

    public int calculateTotalOrders() {
        return 10; 
    }

    public void createCustomer(DatabaseConnector db) {
        Connection conn = db.openConnection();
        String sql = "INSERT INTO Customers (firstName, lastName, email, phone, address) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.firstName);
            stmt.setString(2, this.lastName);
            stmt.setString(3, this.email);
            stmt.setString(4, this.phone);
            stmt.setString(5, this.address);
            stmt.executeUpdate();
            System.out.println("Customer added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Customer getCustomerById(int id, DatabaseConnector db) {
        Connection conn = db.openConnection();
        String sql = "SELECT * FROM Customers WHERE customerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                    rs.getInt("customerID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCustomer(DatabaseConnector db) {
        Connection conn = db.openConnection();
        String sql = "UPDATE Customers SET firstName = ?, lastName = ?, email = ?, phone = ?, address = ? WHERE customerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.firstName);
            stmt.setString(2, this.lastName);
            stmt.setString(3, this.email);
            stmt.setString(4, this.phone);
            stmt.setString(5, this.address);
            stmt.setInt(6, this.customerID);
            stmt.executeUpdate();
            System.out.println("Customer updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(DatabaseConnector db) {
        Connection conn = db.openConnection();
        String sql = "DELETE FROM Customers WHERE customerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.customerID);
            stmt.executeUpdate();
            System.out.println("Customer deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
