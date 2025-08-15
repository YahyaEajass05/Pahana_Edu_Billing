package com.pahanaedu.dao;

import com.pahanaedu.util.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Base Data Access Object with common database operations
 */
public abstract class BaseDAO {

    /**
     * Gets a database connection
     * @return A new Connection object
     * @throws SQLException if connection fails
     */
    protected Connection getConnection() throws SQLException {
        return DatabaseManager.getInstance().getConnection();
    }

    /**
     * Closes database resources safely
     * @param conn Connection to close
     * @param stmt Statement to close
     * @param rs ResultSet to close
     */
    protected void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing ResultSet: " + e.getMessage());
        }

        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing PreparedStatement: " + e.getMessage());
        }

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing Connection: " + e.getMessage());
        }
    }

    /**
     * Executes an update operation (INSERT/UPDATE/DELETE)
     * @param sql The SQL statement
     * @param params The parameters for the statement
     * @return Number of affected rows
     * @throws SQLException if database error occurs
     */
    protected int executeUpdate(String sql, Object... params) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeUpdate();
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Executes a query that returns a single value
     * @param sql The SQL query
     * @param params The parameters for the query
     * @return The result value or null if not found
     * @throws SQLException if database error occurs
     */
    protected <T> T executeQueryForSingleValue(String sql, Class<T> type, Object... params)
            throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            rs = stmt.executeQuery();
            return rs.next() ? rs.getObject(1, type) : null;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    /**
     * Checks if a record exists
     * @param table The table name
     * @param condition The WHERE condition
     * @param params The parameters for the condition
     * @return true if record exists
     * @throws SQLException if database error occurs
     */
    protected boolean exists(String table, String condition, Object... params)
            throws SQLException {
        String sql = "SELECT 1 FROM " + table + " WHERE " + condition;
        return executeQueryForSingleValue(sql, Integer.class, params) != null;
    }

    /**
     * Counts records matching a condition
     * @param table The table name
     * @param condition The WHERE condition
     * @param params The parameters for the condition
     * @return The count of matching records
     * @throws SQLException if database error occurs
     */
    protected int count(String table, String condition, Object... params)
            throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + table;
        if (condition != null && !condition.trim().isEmpty()) {
            sql += " WHERE " + condition;
        }
        Integer count = executeQueryForSingleValue(sql, Integer.class, params);
        return count != null ? count : 0;
    }
}