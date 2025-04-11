package techshop;

import java.util.*;

public class PaymentManager {
    private List<Payment> paymentRecords = new ArrayList<>();

    
    public void recordPayment(Payment payment) {
        try {
            
            boolean isProcessed = PaymentProcessor.processPayment(payment);

            
            if (isProcessed) {
                paymentRecords.add(payment);
                System.out.println("Payment recorded successfully for Order ID: " + payment.getOrderID());
            }
        } catch (PaymentFailedException e) {
            
            System.out.println("Payment failed for Order ID " + payment.getOrderID() + ": " + e.getMessage());
        }
    }

    
    public void updatePaymentStatus(int paymentID, PaymentStatus newStatus) throws PaymentFailedException {
        Optional<Payment> paymentOpt = paymentRecords.stream()
                .filter(p -> p.getPaymentID() == paymentID)
                .findFirst();

        if (paymentOpt.isEmpty()) {
            throw new PaymentFailedException("Payment not found with ID: " + paymentID);
        }

        Payment payment = paymentOpt.get();
        payment.setPaymentStatus(newStatus);
        System.out.println("Payment status updated to: " + newStatus);
    }

    
    public void listPayments() {
        if (paymentRecords.isEmpty()) {
            System.out.println("No payments recorded.");
            return;
        }

        System.out.println("Payment Records:");
        for (Payment payment : paymentRecords) {
            payment.getPaymentDetails();
        }
    }
}
