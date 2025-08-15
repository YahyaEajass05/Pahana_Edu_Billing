package com.pahanaedu.dao;

import com.pahanaedu.model.Item;
import java.util.List;

/**
 * Data Access Object for Item operations
 */
public interface ItemDAO {

    // Create
    boolean addItem(Item item);

    // Read
    Item getItemById(int itemId);
    List<Item> getAllItems();
    List<Item> getActiveItems();
    List<Item> getItemsByCategory(String category);

    // Update
    boolean updateItem(Item item);
    boolean updateStock(int itemId, int quantityChange);

    // Delete (soft delete by marking inactive)
    boolean deactivateItem(int itemId);

    // Search
    List<Item> searchItemsByName(String namePattern);
    List<Item> searchItemsByDescription(String descriptionPattern);

    // Utility
    boolean itemExists(int itemId);
    int countItems();
    int countItemsInCategory(String category);
}