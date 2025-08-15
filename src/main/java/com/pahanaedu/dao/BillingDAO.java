package com.pahanaedu.dao;

import com.pahanaedu.model.BillingService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface BillingDAO {
    // Basic CRUD operations
    boolean createBilling(BillingService billing);
    BillingService getBillingById(int billingId);
    List<BillingService> getBillingsByAccount(int accountId);
    boolean updateBilling(BillingService billing);
    boolean updateBillingStatus(int billingId, String status);
    boolean deleteBilling(int billingId);

    // Item-level operations
    boolean addBillingItem(int billingId, BillingService.BillingItem item);
    boolean removeBillingItem(int billingId, int itemId);
    boolean updateBillingItemQuantity(int billingId, int itemId, int newQuantity);

    // Query operations
    List<BillingService> getPendingBillings();
    List<BillingService> getBillingsByStatus(String status);
    double getTotalRevenue();
}

class BillingDAOImpl implements BillingDAO {
    private static final String BILLING_TABLE = "billings";
    private static final String ITEMS_TABLE = "billing_items";

    // SQL Queries
    private static final String INSERT_BILLING =
            "INSERT INTO " + BILLING_TABLE +
                    "(account_id, billing_date, total_amount, status) VALUES (?, ?, ?, ?)";

    private static final String INSERT_ITEM =
            "INSERT INTO " + ITEMS_TABLE +
                    "(billing_id, item_id, description, unit_price, quantity) VALUES (?, ?, ?, ?, ?)";

    private static final String GET_BILLING_BY_ID =
            "SELECT * FROM " + BILLING_TABLE + " WHERE billing_id = ?";

    // Other SQL queries would be defined here...

    @Override
    public boolean createBilling(BillingService billing) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_BILLING, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, billing.getAccountId());
            stmt.setString(2, billing.getBillingDate());
            stmt.setDouble(3, billing.getTotalAmount());
            stmt.setString(4, billing.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    billing.setBillingId(generatedKeys.getInt(1));
                    return addBillingItems(billing);
                }
            }
            return false;
        } catch (SQLException e) {
            // Log error in production
            return false;
        }
    }

    private boolean addBillingItems(BillingService billing) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_ITEM)) {

            for (BillingService.BillingItem item : billing.getItems()) {
                stmt.setInt(1, billing.getBillingId());
                stmt.setInt(2, item.getItemId());
                stmt.setString(3, item.getDescription());
                stmt.setDouble(4, item.getUnitPrice());
                stmt.setInt(5, item.getQuantity());
                stmt.addBatch();
            }

            int[] results = stmt.executeBatch();
            for (int result : results) {
                if (result != PreparedStatement.EXECUTE_FAILED) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public BillingService getBillingById(int billingId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BILLING_BY_ID)) {

            stmt.setInt(1, billingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BillingService billing = new BillingService();
                    billing.setBillingId(rs.getInt("billing_id"));
                    billing.setAccountId(rs.getInt("account_id"));
                    billing.setBillingDate(rs.getString("billing_date"));
                    billing.setTotalAmount(rs.getDouble("total_amount"));
                    billing.setStatus(rs.getString("status"));

                    // Load items in a separate query
                    billing.setItems(getBillingItems(billingId));
                    return billing;
                }
            }
            return null;
        } catch (SQLException e) {
            // Log error
            return null;
        }
    }

    @Override
    public List<BillingService> getBillingsByAccount(int accountId) {
        return List.of();
    }

    @Override
    public boolean updateBilling(BillingService billing) {
        return false;
    }

    @Override
    public boolean updateBillingStatus(int billingId, String status) {
        return false;
    }

    @Override
    public boolean deleteBilling(int billingId) {
        return false;
    }

    @Override
    public boolean addBillingItem(int billingId, BillingService.BillingItem item) {
        return false;
    }

    @Override
    public boolean removeBillingItem(int billingId, int itemId) {
        return false;
    }

    @Override
    public boolean updateBillingItemQuantity(int billingId, int itemId, int newQuantity) {
        return false;
    }

    @Override
    public List<BillingService> getPendingBillings() {
        return List.of();
    }

    @Override
    public List<BillingService> getBillingsByStatus(String status) {
        return List.of();
    }

    @Override
    public double getTotalRevenue() {
        return 0;
    }

    private List<BillingService.BillingItem> getBillingItems(int billingId) throws SQLException {
        List<BillingService.BillingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM " + ITEMS_TABLE + " WHERE billing_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billingId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(new BillingService.BillingItem(
                            rs.getInt("item_id"),
                            rs.getString("description"),
                            rs.getDouble("unit_price"),
                            rs.getInt("quantity")
                    ));
                }
            }
        }
        return items;
    }

    // Other interface method implementations would follow similar patterns

    private Connection getConnection() throws SQLException {
        // In a real implementation, this would come from your DatabaseManager
        // For this self-contained example, we just show the structure
        return null;
    }
}