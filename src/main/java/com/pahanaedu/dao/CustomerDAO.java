package com.pahanaedu.dao;

import com.pahanaedu.model.CustomerAccount;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends BaseDAO {

    private static final String SQL_INSERT = "INSERT INTO customer_accounts " +
            "(customer_id, account_id, first_name, last_name, email, phone, address) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE customer_accounts SET " +
            "first_name = ?, last_name = ?, email = ?, phone = ?, address = ? " +
            "WHERE customer_id = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM customer_accounts WHERE customer_id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM customer_accounts ORDER BY last_name, first_name";
    private static final String SQL_DELETE = "DELETE FROM customer_accounts WHERE customer_id = ?";
    private static final String SQL_EMAIL_EXISTS = "SELECT 1 FROM customer_accounts WHERE email = ? AND customer_id != ?";

    public void createCustomer(CustomerAccount customer) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            stmt.setString(1, customer.getCustomerId());
            stmt.setString(2, customer.getAccountId());
            stmt.setString(3, customer.getFirstName());
            stmt.setString(4, customer.getLastName());
            stmt.setString(5, customer.getEmail());
            stmt.setString(6, customer.getPhone());
            stmt.setString(7, customer.getAddress());

            stmt.executeUpdate();
        }
    }

    public void updateCustomer(CustomerAccount customer) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getCustomerId());

            stmt.executeUpdate();
        }
    }

    public CustomerAccount getCustomerById(String customerId) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            stmt.setString(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
                return null;
            }
        }
    }

    public List<CustomerAccount> getAllCustomers() throws SQLException {
        List<CustomerAccount> customers = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }
        }
        return customers;
    }

    public void deleteCustomer(String customerId) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {

            stmt.setString(1, customerId);
            stmt.executeUpdate();
        }
    }

    public boolean emailExists(String email, String excludeCustomerId) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_EMAIL_EXISTS)) {

            stmt.setString(1, email);
            stmt.setString(2, excludeCustomerId != null ? excludeCustomerId : "");

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private CustomerAccount mapResultSetToCustomer(ResultSet rs) throws SQLException {
        CustomerAccount customer = new CustomerAccount();
        customer.setCustomerId(rs.getString("customer_id"));
        customer.setAccountId(rs.getString("account_id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        return customer;
    }
}