package techshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentProcessor {

    
    public static boolean processPayment(Payment payment) throws PaymentFailedException {
       
        if (payment.getAmountPaid() <= 0) {
            throw new PaymentFailedException("Payment amount must be greater than zero.");
        }

      
        if (payment.getAmountPaid() < 100) {
            throw new PaymentFailedException("Payment failed due to insufficient amount.");
        }

       
        System.out.println("Payment of " + payment.getAmountPaid() + " processed successfully.");

        
        return recordPayment(payment);
    }

   
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
