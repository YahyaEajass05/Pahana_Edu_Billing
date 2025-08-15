package com.pahanaedu.model;

/**
 * Represents an inventory item in the system
 */
public class Item {
    private int itemId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String category;
    private boolean isActive;

    // Constructors
    public Item() {
        this.isActive = true; // Default to active
    }

    public Item(int itemId, String name, String description, double price,
                int stockQuantity, String category, boolean isActive) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.isActive = isActive;
    }

    // Getters
    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public boolean isActive() {
        return isActive;
    }

    // Setters with basic validation
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty or null");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.stockQuantity = stockQuantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Business logic methods
    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Increase amount must be positive");
        }
        this.stockQuantity += amount;
    }

    public void decreaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Decrease amount must be positive");
        }
        if (this.stockQuantity - amount < 0) {
            throw new IllegalStateException("Insufficient stock available");
        }
        this.stockQuantity -= amount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stockQuantity +
                ", category='" + category + '\'' +
                ", active=" + isActive +
                '}';
    }
}