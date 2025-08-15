package com.pahanaedu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for handling help documentation and FAQs
 */
@WebServlet("/help")
public class HelpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String topic = request.getParameter("topic");
        String page = determineHelpPage(topic);

        request.getRequestDispatcher("/WEB-INF/views/" + page).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("search");

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            request.setAttribute("searchResults", performHelpSearch(searchQuery));
            request.getRequestDispatcher("/WEB-INF/views/help.jsp").forward(request, response);
        } else {
            response.sendRedirect("help");
        }
    }

    private String determineHelpPage(String topic) {
        if (topic == null || topic.isEmpty()) {
            return "help.jsp"; // Default help page
        }

        switch (topic.toLowerCase()) {
            case "billing":
                return "help-billing.jsp";
            case "account":
                return "help-account.jsp";
            case "inventory":
                return "help-inventory.jsp";
            case "reporting":
                return "help-reporting.jsp";
            case "faq":
                return "help-faq.jsp";
            default:
                return "help.jsp";
        }
    }

    private String performHelpSearch(String query) {
        // In a real implementation, this would search a knowledge base
        // For this self-contained example, we return a simple message
        return "Search results for: " + query;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "PUT method not supported");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "DELETE method not supported");
    }
}