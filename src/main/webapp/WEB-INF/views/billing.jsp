<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>PahanaEDU - Billing Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            color: #333;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
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
            background-color: #f5f5f5;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f9f9f9;
        }
        .status-pending {
            color: #ff9800;
            font-weight: bold;
        }
        .status-paid {
            color: #4caf50;
            font-weight: bold;
        }
        .status-cancelled {
            color: #f44336;
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
            background-color: #ffc107;
            color: black;
        }
        .pay-btn {
            background-color: #4CAF50;
            color: white;
        }
        .cancel-btn {
            background-color: #f44336;
            color: white;
        }
        .new-btn {
            background-color: #673ab7;
            color: white;
            padding: 10px 15px;
            margin-bottom: 20px;
        }
        .search-filter {
            margin: 20px 0;
            display: flex;
            gap: 10px;
        }
        input, select {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Billing Management</h1>
        <a href="${pageContext.request.contextPath}/billings?action=new" class="action-btn new-btn">Create New Billing</a>
    </div>

    <div class="search-filter">
        <input type="text" placeholder="Search billings...">
        <select>
            <option value="">All Status</option>
            <option value="PENDING">Pending</option>
            <option value="PAID">Paid</option>
            <option value="CANCELLED">Cancelled</option>
        </select>
        <input type="date">
        <input type="date">
        <button>Filter</button>
    </div>

    <table>
        <thead>
        <tr>
            <th>Billing ID</th>
            <th>Account</th>
            <th>Date</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <!-- Sample data - would be replaced with JSTL/EL in production -->
        <tr>
            <td>1001</td>
            <td>Acme Corp (ID: 45)</td>
            <td>2023-05-15</td>
            <td>$1,250.00</td>
            <td class="status-pending">PENDING</td>
            <td>
                <a href="${pageContext.request.contextPath}/billings?action=view&id=1001" class="action-btn view-btn">View</a>
                <a href="${pageContext.request.contextPath}/billings?action=edit&id=1001" class="action-btn edit-btn">Edit</a>
                <a href="${pageContext.request.contextPath}/billings?action=pay&id=1001" class="action-btn pay-btn">Mark Paid</a>
                <a href="${pageContext.request.contextPath}/billings?action=cancel&id=1001" class="action-btn cancel-btn">Cancel</a>
            </td>
        </tr>
        <tr>
            <td>1002</td>
            <td>Global Edu (ID: 32)</td>
            <td>2023-05-10</td>
            <td>$890.50</td>
            <td class="status-paid">PAID</td>
            <td>
                <a href="${pageContext.request.contextPath}/billings?action=view&id=1002" class="action-btn view-btn">View</a>
            </td>
        </tr>
        <tr>
            <td>1003</td>
            <td>Tech Institute (ID: 28)</td>
            <td>2023-05-05</td>
            <td>$2,150.75</td>
            <td class="status-cancelled">CANCELLED</td>
            <td>
                <a href="${pageContext.request.contextPath}/billings?action=view&id=1003" class="action-btn view-btn">View</a>
            </td>
        </tr>
        </tbody>
    </table>

    <div style="margin-top: 20px; text-align: center;">
        <span>Showing 1 to 3 of 3 entries</span>
        <div style="margin-top: 10px;">
            <button disabled>Previous</button>
            <button style="background-color: #ddd;" disabled>1</button>
            <button disabled>Next</button>
        </div>
    </div>
</div>
</body>
</html>