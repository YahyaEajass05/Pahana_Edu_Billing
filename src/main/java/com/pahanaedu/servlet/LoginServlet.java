package com.pahanaedu.servlet;

import com.pahanaedu.dao.AuthDAO;
import com.pahanaedu.model.Account;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Error message constants
    private static final String ERR_INVALID_CREDENTIALS = "Invalid username or password";
    private static final String ERR_ACCOUNT_LOCKED = "Account is temporarily locked";
    private static final String ERR_SYSTEM_ERROR = "System error occurred. Please try again.";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            forwardToLogin(request, response, ERR_INVALID_CREDENTIALS);
            return;
        }

        try {
            AuthDAO authDAO = new AuthDAO();
            Account account = authDAO.validateUser(username, password);

            if (account == null) {
                authDAO.recordLoginFailure(username, ipAddress, userAgent);
                forwardToLogin(request, response, ERR_INVALID_CREDENTIALS);
                return;
            }

            if (account.isLocked() && account.getLockUntil() != null
                    && account.getLockUntil().after(new java.util.Date())) {
                forwardToLogin(request, response, ERR_ACCOUNT_LOCKED);
                return;
            }

            // Successful login
            authDAO.recordLoginSuccess(account.getAccountId(), ipAddress, userAgent);
            createUserSession(request, account);
            redirectToDashboard(response, account.getRole());

        } catch (SQLException e) {
            log("Database error during login", e);
            forwardToLogin(request, response, ERR_SYSTEM_ERROR);
        }
    }

    private void forwardToLogin(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    private void createUserSession(HttpServletRequest request, Account account) {
        HttpSession session = request.getSession(true);
        session.setAttribute("user", account);
        session.setAttribute("username", account.getUsername());
        session.setAttribute("role", account.getRole());
        session.setMaxInactiveInterval(30 * 60); // 30 minute session timeout
    }

    private void redirectToDashboard(HttpServletResponse response, String role) throws IOException {
        String dashboardPath = "/dashboard"; // Default dashboard
        if ("ADMIN".equals(role)) {
            dashboardPath = "/admin/dashboard";
        } else if ("MANAGER".equals(role)) {
            dashboardPath = "/manager/dashboard";
        }
        response.sendRedirect(dashboardPath);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Show login form for GET requests
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
}