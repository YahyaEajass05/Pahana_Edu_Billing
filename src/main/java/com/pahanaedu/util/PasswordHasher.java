package com.pahanaedu.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Secure password hashing using PBKDF2 with HMAC-SHA256
 */
public final class PasswordHasher {
    // Security parameters
    private static final int ITERATIONS = 100_000;
    private static final int SALT_LENGTH = 32; // bytes
    private static final int KEY_LENGTH = 256; // bits
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    // Format: algorithm:iterations:salt:hash
    private static final String HASH_FORMAT = "%s:%d:%s:%s";

    private PasswordHasher() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    /**
     * Hashes a password with a randomly generated salt
     * @param password The plaintext password to hash
     * @return The hashed password in format "algorithm:iterations:salt:hash"
     * @throws IllegalArgumentException if password is null or empty
     */
    public static String hashPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        byte[] salt = generateSalt();
        byte[] hash = pbkdf2(password.toCharArray(), salt);

        return String.format(HASH_FORMAT,
                ALGORITHM,
                ITERATIONS,
                Base64.getEncoder().encodeToString(salt),
                Base64.getEncoder().encodeToString(hash)
        );
    }

    /**
     * Verifies a password against a stored hash
     * @param password The plaintext password to verify
     * @param storedHash The stored hash in format "algorithm:iterations:salt:hash"
     * @return true if the password matches, false otherwise
     * @throws IllegalArgumentException if inputs are invalid
     */
    public static boolean verifyPassword(String password, String storedHash) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (storedHash == null || storedHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Stored hash cannot be null or empty");
        }

        String[] parts = storedHash.split(":");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid hash format");
        }

        try {
            String algorithm = parts[0];
            int iterations = Integer.parseInt(parts[1]);
            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] originalHash = Base64.getDecoder().decode(parts[3]);

            if (!algorithm.equals(ALGORITHM)) {
                throw new IllegalArgumentException("Algorithm mismatch");
            }

            byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations);
            return constantTimeEquals(originalHash, testHash);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid hash encoding", e);
        }
    }

    /**
     * Checks if a hash needs to be rehashed with updated parameters
     */
    public static boolean needsRehash(String storedHash) {
        if (storedHash == null || !storedHash.startsWith(ALGORITHM + ":")) {
            return true;
        }

        try {
            int iterations = Integer.parseInt(storedHash.split(":")[1]);
            return iterations < ITERATIONS;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private static byte[] pbkdf2(char[] password, byte[] salt) {
        return pbkdf2(password, salt, ITERATIONS);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
        try {
            PBEKeySpec spec = new PBEKeySpec(
                    password,
                    salt,
                    iterations,
                    KEY_LENGTH
            );
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Constant-time comparison to prevent timing attacks
     */
    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a == null || b == null || a.length != b.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
}