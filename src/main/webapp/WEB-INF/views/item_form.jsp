<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>PahanaEDU - Inventory Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
            color: #333;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f8f9fa;
            font-weight: bold;
            position: sticky;
            top: 0;
        }
        tr:hover {
            background-color: #f9f9f9;
        }
        .stock-high {
            color: #4CAF50;
            font-weight: bold;
        }
        .stock-low {
            color: #FF9800;
            font-weight: bold;
        }
        .stock-out {
            color: #F44336;
            font-weight: bold;
        }
        .action-btn {
            padding: 6px 12px;
            margin: 0 3px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
        }
        .view-btn {
            background-color: #2196F3;
            color: white;
        }
        .edit-btn {
            background-color: #FFC107;
            color: black;
        }
        .delete-btn {
            background-color: #F44336;
            color: white;
        }
        .new-btn {
            background-color: #673AB7;
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 4px;
            display: inline-block;
        }
        .search-filter {
            margin: 20px 0;
            display: flex;
            gap: 10px;
            align-items: center;
        }
        input, select {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .pagination {
            margin-top: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 10px;
        }
        .pagination button {
            padding: 8px 12px;
            border: 1px solid #ddd;
            background-color: white;
            cursor: pointer;
            border-radius: 4px;
        }
        .pagination button.active {
            background-color: #2196F3;
            color: white;
            border-color: #2196F3;
        }
        .pagination button:disabled {
            opacity: 0.5;
            cursor: not-allowed;
        }
        .status-active {
            color: #4CAF50;
        }
        .status-inactive {
            color: #9E9E9E;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Inventory Management</h1>
        <a href="${pageContext.request.contextPath}/items?action=new" class="new-btn">Add New Item</a>
    </div>

    <div class="search-filter">
        <input type="text" placeholder="Search items..." style="flex-grow: 1;">
        <select>
            <option value="">All Categories</option>
            <option value="BOOK">Books</option>
            <option value="SUPPLY">Supplies</option>
            <option value="EQUIPMENT">Equipment</option>
            <option value="DIGITAL">Digital</option>
        </select>
        <select>
            <option value="">All Status</option>
            <option value="ACTIVE">Active</option>
            <option value="INACTIVE">Inactive</option>
        </select>
        <button>Filter</button>
    </div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Item Name</th>
            <th>Description</th>
            <th>Category</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <!-- Sample data - would be replaced with JSTL/EL in production -->
        <tr>
            <td>1001</td>
            <td>Java Programming</td>
            <td>Introduction to Java programming</td>
            <td>BOOK</td>
            <td>$49.99</td>
            <td class="stock-high">25</td>
            <td class="status-active">ACTIVE</td>
            <td>
                <a href="${pageContext.request.contextPath}/items?action=view&id=1001" class="action-btn view-btn">View</a>
                <a href="${pageContext.request.contextPath}/items?action=edit&id=1001" class="action-btn edit-btn">Edit</a>
                <a href="${pageContext.request.contextPath}/items?action=delete&id=1001" class="action-btn delete-btn">Delete</a>
            </td>
        </tr>
        <tr>
            <td>1002</td>
            <td>Laptop</td>
            <td>15-inch business laptop</td>
            <td>EQUIPMENT</td>
            <td>$899.99</td>
            <td class="stock-low">3</td>
            <td class="status-active">ACTIVE</td>
            <td>
                <a href="${pageContext.request.contextPath}/items?action=view&id=1002" class="action-btn view-btn">View</a>
                <a href="${pageContext.request.contextPath}/items?action=edit&id=1002" class="action-btn edit-btn">Edit</a>
                <a href="${pageContext.request.contextPath}/items?action=delete&id=1002" class="action-btn delete-btn">Delete</a>
            </td>
        </tr>
        <tr>
            <td>1003</td>
            <td>Notebook</td>
            <td>College-ruled notebook</td>
            <td>SUPPLY</td>
            <td>$3.99</td>
            <td class="stock-out">0</td>
            <td class="status-inactive">INACTIVE</td>
            <td>
                <a href="${pageContext.request.contextPath}/items?action=view&id=1003" class="action-btn view-btn">View</a>
                <a href="${pageContext.request.contextPath}/items?action=edit&id=1003" class="action-btn edit-btn">Edit</a>
                <a href="${pageContext.request.contextPath}/items?action=delete&id=1003" class="action-btn delete-btn">Delete</a>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="pagination">
        <button disabled>Previous</button>
        <button class="active">1</button>
        <button>2</button>
        <button>3</button>
        <button>Next</button>
    </div>
</div>

<script>
    // Basic client-side interactivity
    document.addEventListener('DOMContentLoaded', function() {
        // Confirm before deleting
        const deleteButtons = document.querySelectorAll('.delete-btn');
        deleteButtons.forEach(button => {
            button.addEventListener('click', function(e) {
                if (!confirm('Are you sure you want to delete this item?')) {
                    e.preventDefault();
                }
            });
        });

        // Highlight low stock items
        const stockCells = document.querySelectorAll('td[class^="stock-"]');
        stockCells.forEach(cell => {
            const stock = parseInt(cell.textContent);
            if (stock > 10) {
                cell.className = 'stock-high';
            } else if (stock > 0) {
                cell.className = 'stock-low';
            } else {
                cell.className = 'stock-out';
            }
        });
    });
</script>
</body>
</html>