package com.pahanaedu.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents a user account in the system
 */
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountId;
    private String username;
    private String passwordHash; // Should only store hashed passwords
    private String role;
    private Timestamp createdDate;
    private Timestamp lastLogin;
    private boolean active;
    private int failedLoginAttempts;
    private boolean locked;
    private Timestamp lockUntil;

    // Constructors
    public Account() {
        this.active = true;
    }

    public Account(String accountId, String username, String passwordHash,
                   String role, boolean active) {
        this.accountId = accountId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = active;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters with validation
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        this.accountId = accountId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (!username.matches("^[a-zA-Z0-9._-]{3,50}$")) {
            throw new IllegalArgumentException("Username must be 3-50 characters and contain only letters, numbers, dots, hyphens and underscores");
        }
        this.username = username.trim();
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Password hash cannot be null or empty");
        }
        this.passwordHash = passwordHash.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        this.role = role.trim();
    }

    public Timestamp getCreatedDate() {
        return createdDate != null ? new Timestamp(createdDate.getTime()) : null;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate != null ? new Timestamp(createdDate.getTime()) : null;
    }

    public Timestamp getLastLogin() {
        return lastLogin != null ? new Timestamp(lastLogin.getTime()) : null;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin != null ? new Timestamp(lastLogin.getTime()) : null;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        if (failedLoginAttempts < 0) {
            throw new IllegalArgumentException("Failed login attempts cannot be negative");
        }
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Timestamp getLockUntil() {
        return lockUntil != null ? new Timestamp(lockUntil.getTime()) : null;
    }

    public void setLockUntil(Timestamp lockUntil) {
        this.lockUntil = lockUntil != null ? new Timestamp(lockUntil.getTime()) : null;
    }

    // Business logic methods
    public boolean hasRole(String requiredRole) {
        if (requiredRole == null || role == null) {
            return false;
        }
        return role.equalsIgnoreCase(requiredRole);
    }

    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    public boolean isAccountNonExpired() {
        // Accounts never expire in this implementation
        return true;
    }

    public boolean isAccountNonLocked() {
        if (lockUntil == null) {
            return !locked;
        }
        return !locked || System.currentTimeMillis() > lockUntil.getTime();
    }

    // Security-sensitive equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) &&
                Objects.equals(username, account.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, username);
    }

    // Secure toString that excludes sensitive data
    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", createdDate=" + createdDate +
                ", active=" + active +
                '}';
    }
}