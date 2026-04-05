package carservice.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Invoice {

    private static final double TAX_RATE = 0.18;

    private final String invoiceId;
    private final Customer customer;
    private final List<ServiceRecord> records;
    private final LocalDate issueDate;
    private double discountPercent;

    public Invoice(Customer customer, List<ServiceRecord> records) {
        if (customer == null)             throw new IllegalArgumentException("Customer cannot be null.");
        if (records == null || records.isEmpty())
            throw new IllegalArgumentException("Invoice must have at least one service record.");
        this.invoiceId       = "INV" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.customer        = customer;
        this.records         = new ArrayList<>(records);
        this.issueDate       = LocalDate.now();
        this.discountPercent = 0.0;
    }

    public void applyDiscount(double percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }
        this.discountPercent = percent;
    }

    public double getSubtotal() {
        return records.stream().mapToDouble(ServiceRecord::getAgreedCost).sum();
    }

    public double getDiscountAmount() {
        return getSubtotal() * (discountPercent / 100.0);
    }

    public double getTaxAmount() {
        return (getSubtotal() - getDiscountAmount()) * TAX_RATE;
    }

    public double getGrandTotal() {
        return getSubtotal() - getDiscountAmount() + getTaxAmount();
    }

    public void print() {
        System.out.println("═".repeat(70));
        System.out.printf("  INVOICE: %-12s  Date: %s%n", invoiceId, issueDate);
        System.out.println("─".repeat(70));
        System.out.printf("  Customer : %s (%s)%n", customer.getName(), customer.getCustomerId());
        System.out.printf("  Email    : %s%n", customer.getEmail());
        System.out.println("─".repeat(70));
        System.out.printf("  %-20s %-20s %-12s %10s%n", "Service", "Vehicle", "Status", "Amount");
        System.out.println("─".repeat(70));
        for (ServiceRecord r : records) {
            System.out.printf("  %-20s %-20s %-12s %10.2f%n",
                truncate(r.getService().getServiceName(), 20),
                truncate(r.getVehicle().getLicensePlate(), 20),
                r.getStatus().getDisplayName(),
                r.getAgreedCost());
        }
        System.out.println("─".repeat(70));
        System.out.printf("  %-52s %10.2f%n", "Subtotal:", getSubtotal());
        if (discountPercent > 0) {
            System.out.printf("  %-52s %10.2f%n",
                String.format("Discount (%.1f%%):", discountPercent), -getDiscountAmount());
        }
        System.out.printf("  %-52s %10.2f%n",
            String.format("Tax (%.0f%%):", TAX_RATE * 100), getTaxAmount());
        System.out.println("─".repeat(70));
        System.out.printf("  %-52s %10.2f%n", "GRAND TOTAL:", getGrandTotal());
        System.out.println("═".repeat(70));
    }

    public String getInvoiceId()          { return invoiceId; }
    public Customer getCustomer()         { return customer; }
    public List<ServiceRecord> getRecords() { return Collections.unmodifiableList(records); }
    public LocalDate getIssueDate()       { return issueDate; }
    public double getDiscountPercent()    { return discountPercent; }

    private static String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}
