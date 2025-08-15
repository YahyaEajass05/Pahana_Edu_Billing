package com.pahanaedu.service;

import com.pahanaedu.dao.BillingDAO;
import com.pahanaedu.model.BillingService;
import java.util.List;

/**
 * Service layer implementation for billing operations
 */
public class BillingServiceImpl {

    private final BillingDAO billingDao;

    public BillingServiceImpl(BillingDAO billingDao) {
        this.billingDao = billingDao;
    }

    // Core billing operations
    public boolean createBilling(BillingService billing) {
        validateBilling(billing);
        return billingDao.createBilling(billing);
    }

    public BillingService getBilling(int billingId) {
        if (billingId <= 0) {
            throw new IllegalArgumentException("Invalid billing ID");
        }
        return billingDao.getBillingById(billingId);
    }

    public List<BillingService> getBillingsForAccount(int accountId) {
        if (accountId <= 0) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        return billingDao.getBillingsByAccount(accountId);
    }

    public boolean updateBilling(BillingService billing) {
        validateBilling(billing);
        if (billing.getBillingId() <= 0) {
            throw new IllegalStateException("Billing must have an ID to update");
        }
        return billingDao.updateBilling(billing);
    }

    // Status management
    public boolean markAsPaid(int billingId) {
        return updateBillingStatus(billingId, "PAID");
    }

    public boolean cancelBilling(int billingId) {
        return updateBillingStatus(billingId, "CANCELLED");
    }

    private boolean updateBillingStatus(int billingId, String status) {
        if (billingId <= 0) {
            throw new IllegalArgumentException("Invalid billing ID");
        }
        return billingDao.updateBillingStatus(billingId, status);
    }

    // Item management
    public boolean addItemToBilling(int billingId, BillingService.BillingItem item) {
        validateBillingItem(item);
        return billingDao.addBillingItem(billingId, item);
    }

    public boolean updateItemQuantity(int billingId, int itemId, int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return billingDao.updateBillingItemQuantity(billingId, itemId, newQuantity);
    }

    // Reporting
    public List<BillingService> getPendingBillings() {
        return billingDao.getPendingBillings();
    }

    public double calculateTotalRevenue() {
        return billingDao.getTotalRevenue();
    }

    // Validation
    private void validateBilling(BillingService billing) {
        if (billing == null) {
            throw new IllegalArgumentException("Billing cannot be null");
        }
        if (billing.getAccountId() <= 0) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        if (billing.getItems() == null || billing.getItems().isEmpty()) {
            throw new IllegalArgumentException("Billing must contain items");
        }
        if (billing.getStatus() == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
    }

    private void validateBillingItem(BillingService.BillingItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (item.getItemId() <= 0) {
            throw new IllegalArgumentException("Invalid item ID");
        }
        if (item.getUnitPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }
}