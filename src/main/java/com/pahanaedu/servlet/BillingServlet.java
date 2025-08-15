package com.pahanaedu.servlet;

import com.pahanaedu.model.BillingService;
import com.pahanaedu.service.BillingServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/billings")
public class BillingServlet extends HttpServlet {

    private BillingServiceImpl billingService;

    @Override
    public void init() throws ServletException {
        // In production, inject BillingDAO through DI
        this.billingService = new BillingServiceImpl(null);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listBillings(request, response);
                return;
            }

            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "view":
                    viewBilling(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                default:
                    listBillings(request, response);
            }
        } catch (Exception ex) {
            handleError(request, response, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listBillings(request, response);
                return;
            }

            switch (action) {
                case "create":
                    createBilling(request, response);
                    break;
                case "update":
                    updateBilling(request, response);
                    break;
                case "pay":
                    markAsPaid(request, response);
                    break;
                case "cancel":
                    cancelBilling(request, response);
                    break;
                default:
                    listBillings(request, response);
            }
        } catch (Exception ex) {
            handleError(request, response, ex);
        }
    }

    private void listBillings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("billings", billingService.getPendingBillings());
        request.getRequestDispatcher("/WEB-INF/views/billing.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/billing-form.jsp").forward(request, response);
    }

    private void viewBilling(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int billingId = Integer.parseInt(request.getParameter("id"));
        BillingService billing = billingService.getBilling(billingId);
        request.setAttribute("billing", billing);
        request.getRequestDispatcher("/WEB-INF/views/billing-view.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int billingId = Integer.parseInt(request.getParameter("id"));
        BillingService billing = billingService.getBilling(billingId);
        request.setAttribute("billing", billing);
        request.getRequestDispatcher("/WEB-INF/views/billing-form.jsp").forward(request, response);
    }

    private void createBilling(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        BillingService billing = new BillingService();
        populateBillingFromRequest(billing, request);

        if (billingService.createBilling(billing)) {
            response.sendRedirect("billings?action=view&id=" + billing.getBillingId());
        } else {
            throw new ServletException("Failed to create billing");
        }
    }

    private void updateBilling(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int billingId = Integer.parseInt(request.getParameter("id"));
        BillingService billing = billingService.getBilling(billingId);
        populateBillingFromRequest(billing, request);

        if (billingService.updateBilling(billing)) {
            response.sendRedirect("billings?action=view&id=" + billingId);
        } else {
            throw new ServletException("Failed to update billing");
        }
    }

    private void markAsPaid(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int billingId = Integer.parseInt(request.getParameter("id"));
        if (billingService.markAsPaid(billingId)) {
            response.sendRedirect("billings?action=view&id=" + billingId);
        } else {
            throw new ServletException("Failed to mark billing as paid");
        }
    }

    private void cancelBilling(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int billingId = Integer.parseInt(request.getParameter("id"));
        if (billingService.cancelBilling(billingId)) {
            response.sendRedirect("billings");
        } else {
            throw new ServletException("Failed to cancel billing");
        }
    }

    private void populateBillingFromRequest(BillingService billing, HttpServletRequest request) {
        billing.setAccountId(Integer.parseInt(request.getParameter("accountId")));
        billing.setBillingDate(request.getParameter("billingDate"));
        // Note: In production, you would populate items from request parameters
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception ex)
            throws ServletException, IOException {
        request.setAttribute("error", ex.getMessage());
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }
}