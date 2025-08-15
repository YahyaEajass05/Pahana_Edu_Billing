package com.pahanaedu.dao;

import com.pahanaedu.model.Account;
import com.pahanaedu.util.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Data Access Object for authentication operations
 */
public class AuthDAO extends BaseDAO {

    // SQL queries
    private static final String SQL_VALIDATE_USER =
            "SELECT password_hash, account_id, role FROM accounts WHERE username = ? AND active = TRUE";
    private static final String SQL_GET_ACCOUNT =
            "SELECT username, role, created_date, last_login, failed_attempts, locked, lock_until " +
                    "FROM accounts WHERE account_id = ?";
    private static final String SQL_UPDATE_LOGIN_SUCCESS =
            "UPDATE accounts SET last_login = ?, failed_attempts = 0, locked = FALSE, lock_until = NULL " +
                    "WHERE account_id = ?";
    private static final String SQL_UPDATE_LOGIN_FAILURE =
            "UPDATE accounts SET last_failed_login = ?, failed_attempts = failed_attempts + 1 " +
                    "WHERE username = ?";
    private static final String SQL_INSERT_LOGIN_ATTEMPT =
            "INSERT INTO login_attempts (account_id, ip_address, user_agent, successful) " +
                    "VALUES (?, ?, ?, ?)";

    /**
     * Validates user credentials
     * @param username The username
     * @param password The plaintext password
     * @return Account if valid, null otherwise
     * @throws SQLException if database error occurs
     */
    public Account validateUser(String username, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_VALIDATE_USER);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (PasswordHasher.verifyPassword(password, storedHash)) {
                    return getAccountById(rs.getString("account_id"));
                }
            }
            return null;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    /**
     * Gets account details by ID
     */
    public Account getAccountById(String accountId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_GET_ACCOUNT);
            stmt.setString(1, accountId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccountId(accountId);
                account.setUsername(rs.getString("username"));
                account.setRole(rs.getString("role"));
                account.setCreatedDate(rs.getTimestamp("created_date"));
                account.setLastLogin(rs.getTimestamp("last_login"));
                account.setFailedLoginAttempts(rs.getInt("failed_attempts"));
                account.setLocked(rs.getBoolean("locked"));
                account.setLockUntil(rs.getTimestamp("lock_until"));
                return account;
            }
            return null;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    /**
     * Records a successful login attempt
     */
    public void recordLoginSuccess(String accountId, String ipAddress, String userAgent)
            throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            // Update account
            stmt = conn.prepareStatement(SQL_UPDATE_LOGIN_SUCCESS);
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setString(2, accountId);
            stmt.executeUpdate();
            closeResources(null, stmt, null);

            // Record attempt
            stmt = conn.prepareStatement(SQL_INSERT_LOGIN_ATTEMPT);
            stmt.setString(1, accountId);
            stmt.setString(2, ipAddress);
            stmt.setString(3, userAgent);
            stmt.setBoolean(4, true);
            stmt.executeUpdate();
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Records a failed login attempt
     */
    public void recordLoginFailure(String username, String ipAddress, String userAgent)
            throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            // Update account
            stmt = conn.prepareStatement(SQL_UPDATE_LOGIN_FAILURE);
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setString(2, username);
            stmt.executeUpdate();
            closeResources(null, stmt, null);

            // Record attempt (if we can find the account)
            Account account = findAccountByUsername(username);
            if (account != null) {
                stmt = conn.prepareStatement(SQL_INSERT_LOGIN_ATTEMPT);
                stmt.setString(1, account.getAccountId());
                stmt.setString(2, ipAddress);
                stmt.setString(3, userAgent);
                stmt.setBoolean(4, false);
                stmt.executeUpdate();
            }
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Finds account by username
     */
    private Account findAccountByUsername(String username) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT account_id FROM accounts WHERE username = ?");
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            return rs.next() ? getAccountById(rs.getString("account_id")) : null;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
}