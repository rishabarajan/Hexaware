package techshop;

import java.util.*;

public class PaymentManager {
    private List<Payment> paymentRecords = new ArrayList<>();

    // Method to record a payment
    public void recordPayment(Payment payment) {
        try {
            // Attempt to process the payment through PaymentProcessor
            boolean isProcessed = PaymentProcessor.processPayment(payment);

            // If payment is processed successfully, add it to the records
            if (isProcessed) {
                paymentRecords.add(payment);
                System.out.println("Payment recorded successfully for Order ID: " + payment.getOrderID());
            }
        } catch (PaymentFailedException e) {
            // Handle payment failure by throwing a PaymentFailedException
            System.out.println("Payment failed for Order ID " + payment.getOrderID() + ": " + e.getMessage());
        }
    }

    // Method to update the payment status
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

    // Method to list all payment records
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
