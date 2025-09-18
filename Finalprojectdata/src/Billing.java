import java.util.ArrayList;
import java.util.List;

public class Billing {
    private int patientID;
    private double billingAmount;
    private double totalBilledAmount;  // Track the original billed amount
    private List<Payment> paymentHistory;  // Use a custom Payment class for better tracking
    private Patient patient;  // Link to the Patient object

    // Constructor to initialize billing with a linked patient and amount
    public Billing(Patient patient, double billingAmount) {
        this.patientID = patient.getPatientID();
        this.patient = patient;
        this.billingAmount = billingAmount;
        this.totalBilledAmount = billingAmount;  // Set the original billed amount
        this.paymentHistory = new ArrayList<>();
    }

    // Add a payment to the billing and update the remaining balance
    public void addPayment(String date, double amount) {
        if (amount <= billingAmount) {
            billingAmount -= amount;  // Deduct from the remaining balance
            paymentHistory.add(new Payment(date, amount));
        } else {
            System.out.println("Payment exceeds the remaining balance.");
        }
    }

    // Returns the payment status (Paid in full or remaining balance)
    public String getPaymentStatus() {
        // Check if the total billed amount has been paid in full
        return totalBilledAmount - billingAmount <= 0 ? "Paid in full" : "Remaining balance: $" + billingAmount;
    }

    // Get the payment history as a formatted string
    public String getPaymentHistory() {
        StringBuilder history = new StringBuilder();
        for (Payment payment : paymentHistory) {
            history.append("Payment of $").append(payment.getAmount())
                    .append(" on ").append(payment.getDate()).append("\n");
        }
        return history.toString();
    }

    // Get the name of the patient linked to the billing
    public String getPatientName() {
        return patient.getName();
    }

    // Get the patient ID
    public int getPatientID() {
        return patientID;
    }

    // Get the remaining billing amount
    public double getBillingAmount() {
        return billingAmount;
    }

    // Inner class to represent a payment
    static class Payment {
        private String date;
        private double amount;

        public Payment(String date, double amount) {
            this.date = date;
            this.amount = amount;
        }

        public String getDate() {
            return date;
        }

        public double getAmount() {
            return amount;
        }
    }

    // Get the total billed amount (before payments)
    public double getTotalBilledAmount() {
        return totalBilledAmount;
    }

    // Get the list of payments
    public List<Payment> getPayments() {
        return paymentHistory;
    }
}
