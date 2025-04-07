package techshop;

import java.util.Date;

public class Payment {
    private int paymentID;
    private int orderID;
    private double amountPaid;
    private PaymentStatus paymentStatus;
    private Date paymentDate;
    private String paymentMethod;  // New field

    public Payment(int paymentID, int orderID, double amountPaid, PaymentStatus paymentStatus, Date paymentDate, String paymentMethod) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.amountPaid = amountPaid;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public int getPaymentID() { return paymentID; }
    public void setPaymentID(int paymentID) { this.paymentID = paymentID; }

    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }

    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentMethod() { return paymentMethod; }  // New getter
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    // Display payment details
    public void getPaymentDetails() {
        System.out.println("Payment ID: " + paymentID);
        System.out.println("Order ID: " + orderID);
        System.out.println("Amount Paid: " + amountPaid);
        System.out.println("Payment Status: " + paymentStatus);
        System.out.println("Payment Date: " + paymentDate);
        System.out.println("Payment Method: " + paymentMethod);  // Added this
    }
}
