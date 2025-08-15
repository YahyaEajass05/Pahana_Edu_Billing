package com.pahanaedu.servlet;

import com.pahanaedu.model.Item;
import com.pahanaedu.service.ItemService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/items")
public class ItemServlet extends HttpServlet {

    private ItemService itemService;

    @Override
    public void init() throws ServletException {
        // In a real app, this would be injected
        this.itemService = new ItemService(null); // Initialize with your ItemDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                action = "list";
            }

            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteItem(request, response);
                    break;
                case "list":
                default:
                    listItems(request, response);
                    break;
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
            if ("create".equals(action)) {
                createItem(request, response);
            } else if ("update".equals(action)) {
                updateItem(request, response);
            }
        } catch (Exception ex) {
            handleError(request, response, ex);
        }
    }

    private void listItems(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("items", itemService.getAllItems());
        request.getRequestDispatcher("/WEB-INF/views/item-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/item-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Item item = itemService.getItem(id);
        request.setAttribute("item", item);
        request.getRequestDispatcher("/WEB-INF/views/item-form.jsp").forward(request, response);
    }

    private void createItem(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Item item = new Item();
        item.setName(request.getParameter("name"));
        item.setDescription(request.getParameter("description"));
        item.setPrice(Double.parseDouble(request.getParameter("price")));
        item.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity")));
        item.setCategory(request.getParameter("category"));

        itemService.createItem(item);
        response.sendRedirect("items?action=list");
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Item item = new Item();
        item.setItemId(id);
        item.setName(request.getParameter("name"));
        item.setDescription(request.getParameter("description"));
        item.setPrice(Double.parseDouble(request.getParameter("price")));
        item.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity")));
        item.setCategory(request.getParameter("category"));

        itemService.updateItem(item);
        response.sendRedirect("items?action=list");
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        itemService.deleteItem(id);
        response.sendRedirect("items?action=list");
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception ex)
            throws ServletException, IOException {
        request.setAttribute("error", ex.getMessage());
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }
}