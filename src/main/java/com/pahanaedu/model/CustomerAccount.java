package com.pahanaedu.model;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents a customer account with extended personal information
 */
public final class CustomerAccount extends Account {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Timestamp dateOfBirth;

    // Constructors
    public CustomerAccount() {
        super();
    }

    public CustomerAccount(String accountId, String username, String passwordHash,
                           String customerId, String firstName, String lastName,
                           String email, String phone) {
        super(accountId, username, passwordHash, "CUSTOMER", true);
        setCustomerId(customerId);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhone(phone);
    }

    // Getters and Setters with validation
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        this.customerId = customerId.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (firstName.length() > 50) {
            throw new IllegalArgumentException("First name cannot exceed 50 characters");
        }
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (lastName.length() > 50) {
            throw new IllegalArgumentException("Last name cannot exceed 50 characters");
        }
        this.lastName = lastName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email.trim().toLowerCase();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        String cleaned = phone.replaceAll("[^0-9+]", "");
        if (cleaned.length() < 8 || cleaned.length() > 15) {
            throw new IllegalArgumentException("Phone number must be 8-15 digits");
        }
        this.phone = cleaned;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address != null ? address.trim() : null;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth != null ? new Timestamp(dateOfBirth.getTime()) : null;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth != null ? new Timestamp(dateOfBirth.getTime()) : null;
    }

    // Business methods
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isAdult() {
        if (dateOfBirth == null) return false;

        long eighteenYearsInMillis = 18L * 365 * 24 * 60 * 60 * 1000;
        return System.currentTimeMillis() - dateOfBirth.getTime() >= eighteenYearsInMillis;
    }

    // Security-sensitive equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomerAccount that = (CustomerAccount) o;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customerId, email);
    }

    // Secure toString
    @Override
    public String toString() {
        return "CustomerAccount{" +
                "customerId='" + customerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", active=" + isActive() +
                '}';
    }
}