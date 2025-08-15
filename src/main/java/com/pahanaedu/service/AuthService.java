package com.pahanaedu.service;

import com.pahanaedu.dao.AuthDAO;
import com.pahanaedu.model.Account;
import java.sql.SQLException;

public class AuthService {

    private final AuthDAO authDao;

    public AuthService() {
        this.authDao = new AuthDAO();
    }

    /**
     * Authenticates a user with credentials
     * @param username The login username
     * @param password The plaintext password
     * @return Authenticated account if successful, null otherwise
     * @throws SQLException If database error occurs
     * @throws IllegalArgumentException If credentials are invalid
     */
    public Account authenticate(String username, String password) throws SQLException, IllegalArgumentException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        Account account = authDao.validateUser(username, password);
        if (account == null) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        if (account.isLocked()) {
            throw new IllegalStateException("Account is locked. Please try again later.");
        }
        return account;
    }

    /**
     * Checks if a user has the required role
     * @param account The account to check
     * @param requiredRole The required role
     * @return true if user has the role, false otherwise
     */
    public boolean hasRole(Account account, String requiredRole) {
        if (account == null || requiredRole == null) {
            return false;
        }
        return account.getRole().equalsIgnoreCase(requiredRole);
    }

    /**
     * Checks if a user is an administrator
     */
    public boolean isAdmin(Account account) {
        return hasRole(account, "ADMIN");
    }

    /**
     * Records a successful login attempt
     */
    public void recordLoginSuccess(Account account, String ipAddress, String userAgent) throws SQLException {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        authDao.recordLoginSuccess(account.getAccountId(), ipAddress, userAgent);
    }

    /**
     * Records a failed login attempt
     */
    public void recordLoginFailure(String username, String ipAddress, String userAgent) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        authDao.recordLoginFailure(username, ipAddress, userAgent);
    }

    /**
     * Gets account details by ID
     */
    public Account getAccountDetails(String accountId) throws SQLException {
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be empty");
        }
        return authDao.getAccountById(accountId);
    }

    /**
     * Checks if an account is active and not locked
     */
    public boolean isAccountActive(Account account) {
        return account != null && account.isActive() && !account.isLocked();
    }
}