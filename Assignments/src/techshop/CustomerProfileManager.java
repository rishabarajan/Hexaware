package techshop;

import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CustomerProfileManager {
    private DatabaseConnector dbConnector;

    public CustomerProfileManager() {
        dbConnector = new DatabaseConnector();
    }

    // Get customer details
    public void getCustomerDetails(int customerId) {
        Connection conn = dbConnector.openConnection();
        String query = "SELECT FirstName, LastName, Email, Phone, Address FROM Customers WHERE CustomerID = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Customer Details:");
                System.out.println("Name: " + rs.getString("FirstName") + " " + rs.getString("LastName"));
                System.out.println("Email: " + rs.getString("Email"));
                System.out.println("Phone: " + rs.getString("Phone"));
                System.out.println("Address: " + rs.getString("Address"));
            } else {
                System.out.println("Customer not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }

    // Update Email
    public void updateEmail(int customerId, String newEmail) {
        if (!isValidEmail(newEmail)) {
            System.out.println("Invalid email format.");
            return;
        }

        Connection conn = dbConnector.openConnection();
        String query = "UPDATE Customers SET Email = ? WHERE CustomerID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, customerId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Email updated successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: This email is already in use.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }

    // Update Phone
    public void updatePhone(int customerId, String newPhone) {
        if (!isValidPhone(newPhone)) {
            System.out.println("Invalid phone number format.");
            return;
        }

        Connection conn = dbConnector.openConnection();
        String query = "UPDATE Customers SET Phone = ? WHERE CustomerID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newPhone);
            pstmt.setInt(2, customerId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Phone number updated successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: This phone number is already in use.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }

    // Update Address
    public void updateAddress(int customerId, String newAddress) {
        Connection conn = dbConnector.openConnection();
        String query = "UPDATE Customers SET Address = ? WHERE CustomerID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newAddress);
            pstmt.setInt(2, customerId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Address updated successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }

    // Email Validation
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    // Phone Validation
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}"); // Assumes a 10-digit phone number
    }

    // Main method for testing
    public static void main(String[] args) {
        CustomerProfileManager manager = new CustomerProfileManager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("\nFetching current details...");
        manager.getCustomerDetails(customerId);

        System.out.println("\nChoose an option:");
        System.out.println("1. Update Email");
        System.out.println("2. Update Phone");
        System.out.println("3. Update Address");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter new Email: ");
                String newEmail = scanner.nextLine();
                manager.updateEmail(customerId, newEmail);
                break;
            case 2:
                System.out.print("Enter new Phone: ");
                String newPhone = scanner.nextLine();
                manager.updatePhone(customerId, newPhone);
                break;
            case 3:
                System.out.print("Enter new Address: ");
                String newAddress = scanner.nextLine();
                manager.updateAddress(customerId, newAddress);
                break;
            default:
                System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}
