package com.pahanaedu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Manages database connections using a simple connection pool
 */
public final class DatabaseManager {
    private static final int MAX_POOL_SIZE = 10;
    private static DatabaseManager instance;
    private final BlockingQueue<Connection> connectionPool;
    private final String dbUrl;
    private final Properties dbProperties;

    // Private constructor to prevent instantiation
    private DatabaseManager() throws SQLException {
        // Initialize database properties
        dbProperties = new Properties();
        dbProperties.setProperty("user", "sa");
        dbProperties.setProperty("password", "");
        dbProperties.setProperty("ssl", "false");

        // Configure connection pool
        dbUrl = "jdbc:h2:mem:pahanaedu;DB_CLOSE_DELAY=-1";
        connectionPool = new LinkedBlockingQueue<>(MAX_POOL_SIZE);

        // Pre-create connections
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            connectionPool.add(createNewConnection());
        }
    }

    /**
     * Gets the singleton instance
     */
    public static synchronized DatabaseManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Gets a database connection from the pool
     */
    public Connection getConnection() throws SQLException {
        Connection conn = connectionPool.poll();
        if (conn == null || conn.isClosed()) {
            return createNewConnection();
        }
        return conn;
    }

    /**
     * Returns a connection to the pool
     */
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            connectionPool.offer(connection);
        }
    }

    /**
     * Creates a new database connection
     */
    private Connection createNewConnection() throws SQLException {
        try {
            return DriverManager.getConnection(dbUrl, dbProperties);
        } catch (SQLException e) {
            throw new SQLException("Failed to create new database connection", e);
        }
    }

    /**
     * Closes all connections in the pool
     */
    public synchronized void shutdown() throws SQLException {
        SQLException firstException = null;

        for (Connection conn : connectionPool) {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                if (firstException == null) {
                    firstException = e;
                }
            }
        }

        connectionPool.clear();

        if (firstException != null) {
            throw firstException;
        }
    }
}