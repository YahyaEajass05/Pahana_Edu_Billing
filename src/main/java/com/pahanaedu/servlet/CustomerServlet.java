package com.pahanaedu.servlet;

import com.pahanaedu.model.CustomerAccount;
import com.pahanaedu.service.CustomerService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/customers/*")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        this.customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        try {
            switch (action == null ? "list" : action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/delete":
                    deleteCustomer(request, response);
                    break;
                default:
                    listCustomers(request, response);
                    break;
            }
        } catch (SQLException | IllegalArgumentException ex) {
            handleError(request, response, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/insert":
                    insertCustomer(request, response);
                    break;
                case "/update":
                    updateCustomer(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        } catch (SQLException | IllegalArgumentException ex) {
            handleError(request, response, ex);
        }
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<CustomerAccount> customers = customerService.listAllCustomers();
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("/WEB-INF/views/customer-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/customer-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String id = request.getParameter("id");
        CustomerAccount customer = customerService.getCustomerDetails(id);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/WEB-INF/views/customer-form.jsp").forward(request, response);
    }

    private void insertCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        CustomerAccount customer = new CustomerAccount();
        populateCustomerFromRequest(customer, request);

        customerService.registerCustomer(customer);
        response.sendRedirect("list");
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        String id = request.getParameter("id");
        CustomerAccount customer = customerService.getCustomerDetails(id);
        populateCustomerFromRequest(customer, request);

        customerService.updateCustomerDetails(customer);
        response.sendRedirect("list");
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        String id = request.getParameter("id");
        customerService.deleteCustomer(id);
        response.sendRedirect("../list");
    }

    private void populateCustomerFromRequest(CustomerAccount customer, HttpServletRequest request) {
        customer.setCustomerId(request.getParameter("id"));
        customer.setAccountId(request.getParameter("accountId"));
        customer.setFirstName(request.getParameter("firstName"));
        customer.setLastName(request.getParameter("lastName"));
        customer.setEmail(request.getParameter("email"));
        customer.setPhone(request.getParameter("phone"));
        customer.setAddress(request.getParameter("address"));
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception ex)
            throws ServletException, IOException {

        request.setAttribute("errorMessage", ex.getMessage());
        if (ex instanceof IllegalArgumentException) {
            CustomerAccount customer = new CustomerAccount();
            populateCustomerFromRequest(customer, request);
            request.setAttribute("customer", customer);
            request.getRequestDispatcher("/WEB-INF/views/customer-form.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}