package techshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerRegistration {

    public static void registerCustomer() {
        Scanner scanner = new Scanner(System.in);
        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection connection = dbConnector.openConnection();

        try {
            // Take user input
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();

            // Check if email already exists
            if (isEmailExists(connection, email)) {
                System.out.println("Error: Email already exists. Please use a different email.");
                return;
            }

            // Insert new customer into the database
            String query = "INSERT INTO Customers (FirstName, LastName, Email, Phone, Address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, address);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Customer registered successfully!");
            } else {
                System.out.println("Registration failed. Please try again.");
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }

        scanner.close();
    }

    // Method to check if an email already exists
    private static boolean isEmailExists(Connection connection, String email) throws SQLException {
        String query = "SELECT Email FROM Customers WHERE Email = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet resultSet = stmt.executeQuery();

        boolean exists = resultSet.next(); // If a record is found, email exists
        stmt.close();
        return exists;
    }

    public static void main(String[] args) {
        registerCustomer();
    }
}
