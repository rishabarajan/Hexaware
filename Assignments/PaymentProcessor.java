package techshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentProcessor {

    // Method to process a payment
    public static boolean processPayment(Payment payment) throws PaymentFailedException {
        // Validate payment amount
        if (payment.getAmountPaid() <= 0) {
            throw new PaymentFailedException("Payment amount must be greater than zero.");
        }

        // Simulate random payment failure for small amounts
        if (payment.getAmountPaid() < 100) {
            throw new PaymentFailedException("Payment failed due to insufficient amount.");
        }

        // Simulated success
        System.out.println("Payment of " + payment.getAmountPaid() + " processed successfully.");

        // Record the payment in the database
        return recordPayment(payment);
    }

    // Method to record the payment in the database
    private static boolean recordPayment(Payment payment) {
        String sql = "INSERT INTO Payments (OrderID, PaymentMethod, AmountPaid, PaymentDate) VALUES (?, ?, ?, NOW())";
        
        try (Connection conn = new DatabaseConnector().openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, payment.getOrderID());
            pstmt.setString(2, payment.getPaymentMethod());
            pstmt.setDouble(3, payment.getAmountPaid());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
