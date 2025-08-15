package com.pahanaedu.model;

import java.util.List;

/**
 * Represents a billing service or invoice in the system
 */
public class BillingService {
    private int billingId;
    private int accountId;
    private String billingDate;
    private double totalAmount;
    private String status; // PENDING, PAID, CANCELLED
    private List<BillingItem> items;

    public BillingService() {
        this.status = "PENDING"; // Default status
    }

    public void setTotalAmount(double totalAmount) {
    }

    // Nested class for billing items
    public static class BillingItem {
        private int itemId;
        private String description;
        private double unitPrice;
        private int quantity;
        private double subtotal;

        public BillingItem(int itemId, String description, double unitPrice, int quantity) {
            this.itemId = itemId;
            this.description = description;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
            this.subtotal = unitPrice * quantity;
        }

        // Getters
        public int getItemId() { return itemId; }
        public String getDescription() { return description; }
        public double getUnitPrice() { return unitPrice; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return subtotal; }

        // Setters with validation
        public void setQuantity(int quantity) {
            if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
            this.quantity = quantity;
            this.subtotal = this.unitPrice * quantity;
        }
    }

    // Getters
    public int getBillingId() { return billingId; }
    public int getAccountId() { return accountId; }
    public String getBillingDate() { return billingDate; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public List<BillingItem> getItems() { return items; }

    // Setters with validation
    public void setBillingId(int billingId) { this.billingId = billingId; }

    public void setAccountId(int accountId) {
        if (accountId <= 0) throw new IllegalArgumentException("Invalid account ID");
        this.accountId = accountId;
    }

    public void setBillingDate(String billingDate) {
        if (billingDate == null || billingDate.isEmpty()) {
            throw new IllegalArgumentException("Billing date cannot be empty");
        }
        this.billingDate = billingDate;
    }

    public void setStatus(String status) {
        if (!List.of("PENDING", "PAID", "CANCELLED").contains(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        this.status = status;
    }

    public void setItems(List<BillingItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Billing must contain items");
        }
        this.items = items;
        calculateTotal();
    }

    // Business logic methods
    public void addItem(BillingItem item) {
        this.items.add(item);
        calculateTotal();
    }

    public void removeItem(int itemId) {
        items.removeIf(item -> item.getItemId() == itemId);
        calculateTotal();
    }

    public void updateItemQuantity(int itemId, int newQuantity) {
        items.stream()
                .filter(item -> item.getItemId() == itemId)
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(newQuantity);
                    calculateTotal();
                });
    }

    private void calculateTotal() {
        this.totalAmount = items.stream()
                .mapToDouble(BillingItem::getSubtotal)
                .sum();
    }

    public void markAsPaid() {
        this.status = "PAID";
    }

    public void cancel() {
        this.status = "CANCELLED";
    }

    @Override
    public String toString() {
        return "BillingService{" +
                "billingId=" + billingId +
                ", accountId=" + accountId +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", itemCount=" + (items != null ? items.size() : 0) +
                '}';
    }
}