package com.pahanaedu.service;

import com.pahanaedu.dao.ItemDAO;
import com.pahanaedu.model.Item;
import java.util.List;

/**
 * Service layer for item business logic
 */
public class ItemService {

    private final ItemDAO itemDao;

    public ItemService(ItemDAO itemDao) {
        this.itemDao = itemDao;
    }

    // Core Item Operations
    public boolean createItem(Item item) {
        validateItem(item);
        return itemDao.addItem(item);
    }

    public Item getItem(int itemId) {
        return itemDao.getItemById(itemId);
    }

    public List<Item> getAllItems() {
        return itemDao.getAllItems();
    }

    public boolean updateItem(Item item) {
        validateItem(item);
        return itemDao.updateItem(item);
    }

    public boolean deleteItem(int itemId) {
        return itemDao.deactivateItem(itemId);
    }

    // Inventory Management
    public boolean increaseStock(int itemId, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Increase amount must be positive");
        }
        return itemDao.updateStock(itemId, amount);
    }

    public boolean decreaseStock(int itemId, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Decrease amount must be positive");
        }
        Item item = itemDao.getItemById(itemId);
        if (item.getStockQuantity() < amount) {
            throw new IllegalStateException("Insufficient stock available");
        }
        return itemDao.updateStock(itemId, -amount);
    }

    // Search Operations
    public List<Item> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }
        return itemDao.searchItemsByName("%" + name + "%");
    }

    public List<Item> getItemsByCategory(String category) {
        return itemDao.getItemsByCategory(category);
    }

    // Validation
    private void validateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (item.getName() == null || item.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty");
        }
        if (item.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (item.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
    }

    // Business Reports
    public int getTotalItemCount() {
        return itemDao.countItems();
    }

    public int getCategoryItemCount(String category) {
        return itemDao.countItemsInCategory(category);
    }
}