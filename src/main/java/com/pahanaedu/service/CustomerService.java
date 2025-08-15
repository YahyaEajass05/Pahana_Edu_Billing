package com.pahanaedu.service;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.model.CustomerAccount;
import java.sql.SQLException;
import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDao;

    public CustomerService() {
        this.customerDao = new CustomerDAO();
    }

    /**
     * Registers a new customer with validation
     */
    public void registerCustomer(CustomerAccount customer) throws IllegalArgumentException, SQLException {
        validateCustomer(customer);

        if (customerDao.emailExists(customer.getEmail(), null)) {
            throw new IllegalArgumentException("Email already registered");
        }

        customerDao.createCustomer(customer);
    }

    /**
     * Updates customer details with validation
     */
    public void updateCustomerDetails(CustomerAccount customer) throws IllegalArgumentException, SQLException {
        validateCustomer(customer);

        if (customerDao.emailExists(customer.getEmail(), customer.getCustomerId())) {
            throw new IllegalArgumentException("Email already in use by another customer");
        }

        customerDao.updateCustomer(customer);
    }

    /**
     * Retrieves customer by ID
     */
    public CustomerAccount getCustomerDetails(String customerId) throws SQLException {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        return customerDao.getCustomerById(customerId);
    }

    /**
     * Lists all active customers
     */
    public List<CustomerAccount> listAllCustomers() throws SQLException {
        return customerDao.getAllCustomers();
    }

    /**
     * Deletes a customer record
     */
    public void deleteCustomer(String customerId) throws SQLException {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        customerDao.deleteCustomer(customerId);
    }

    /**
     * Validates customer data integrity
     */
    private void validateCustomer(CustomerAccount customer) throws IllegalArgumentException {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (customer.getPhone() == null || customer.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required");
        }
    }

    /**
     * Checks if email is available
     */
    public boolean isEmailAvailable(String email, String excludeCustomerId) throws SQLException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        return !customerDao.emailExists(email, excludeCustomerId);
    }
}