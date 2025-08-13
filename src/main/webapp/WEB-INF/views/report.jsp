<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>PahanaEDU - Reports</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div class="report-container">
    <h2>Sales Reports</h2>

    <div class="report-filters">
        <form id="reportForm" action="${pageContext.request.contextPath}/reports" method="get">
            <div class="filter-row">
                <div class="form-group">
                    <label for="reportType">Report Type:</label>
                    <select id="reportType" name="reportType">
                        <option value="sales" ${param.reportType == 'sales' ? 'selected' : ''}>Sales Summary</option>
                        <option value="items" ${param.reportType == 'items' ? 'selected' : ''}>Item Performance</option>
                        <option value="customers" ${param.reportType == 'customers' ? 'selected' : ''}>Customer Activity</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="startDate">From:</label>
                    <input type="date" id="startDate" name="startDate" value="${param.startDate}">
                </div>

                <div class="form-group">
                    <label for="endDate">To:</label>
                    <input type="date" id="endDate" name="endDate" value="${param.endDate}">
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-generate">Generate Report</button>
                <button type="button" class="btn-export" onclick="exportReport()">Export as PDF</button>
            </div>
        </form>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
                ${errorMessage}
        </div>
    </c:if>

    <div class="report-results">
        <c:choose>
            <c:when test="${param.reportType == 'sales' or empty param.reportType}">
                <h3>Sales Summary</h3>
                <div class="chart-container">
                    <canvas id="salesChart"></canvas>
                </div>
                <table class="report-table">
                    <thead>
                    <tr>
                        <th>Period</th>
                        <th>Total Sales</th>
                        <th>Number of Orders</th>
                        <th>Avg. Order Value</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${salesData}" var="data">
                        <tr>
                            <td>${data.period}</td>
                            <td><fmt:formatNumber value="${data.totalSales}" type="currency"/></td>
                            <td>${data.orderCount}</td>
                            <td><fmt:formatNumber value="${data.averageOrderValue}" type="currency"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>

            <c:when test="${param.reportType == 'items'}">
                <h3>Item Performance</h3>
                <div class="chart-container">
                    <canvas id="itemsChart"></canvas>
                </div>
                <table class="report-table">
                    <thead>
                    <tr>
                        <th>Item</th>
                        <th>Quantity Sold</th>
                        <th>Total Revenue</th>
                        <th>% of Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${itemData}" var="item">
                        <tr>
                            <td>${item.name}</td>
                            <td>${item.quantitySold}</td>
                            <td><fmt:formatNumber value="${item.totalRevenue}" type="currency"/></td>
                            <td><fmt:formatNumber value="${item.percentage}" type="percent"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>

            <c:when test="${param.reportType == 'customers'}">
                <h3>Customer Activity</h3>
                <div class="chart-container">
                    <canvas id="customersChart"></canvas>
                </div>
                <table class="report-table">
                    <thead>
                    <tr>
                        <th>Customer</th>
                        <th>Orders</th>
                        <th>Total Spent</th>
                        <th>Last Purchase</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${customerData}" var="customer">
                        <tr>
                            <td>${customer.name}</td>
                            <td>${customer.orderCount}</td>
                            <td><fmt:formatNumber value="${customer.totalSpent}" type="currency"/></td>
                            <td><fmt:formatDate value="${customer.lastPurchase}" pattern="yyyy-MM-dd"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
        </c:choose>
    </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>

<script>
    // Initialize charts based on report type
    document.addEventListener('DOMContentLoaded', function() {
        const reportType = '${param.reportType}';

        if (reportType === 'sales' || reportType === '') {
            initSalesChart();
        } else if (reportType === 'items') {
            initItemsChart();
        } else if (reportType === 'customers') {
            initCustomersChart();
        }
    });

    function initSalesChart() {
        const ctx = document.getElementById('salesChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: [<c:forEach items="${salesData}" var="data">"${data.period}",</c:forEach>],
                datasets: [{
                    label: 'Total Sales',
                    data: [<c:forEach items="${salesData}" var="data">${data.totalSales},</c:forEach>],
                    backgroundColor: 'rgba(54, 162, 235, 0.5)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            callback: function(value) {
                                return '$' + value;
                            }
                        }
                    }
                }
            }
        });
    }

    function initItemsChart() {
        const ctx = document.getElementById('itemsChart').getContext('2d');
        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: [<c:forEach items="${itemData}" var="item">"${item.name}",</c:forEach>],
                datasets: [{
                    data: [<c:forEach items="${itemData}" var="item">${item.totalRevenue},</c:forEach>],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(255, 206, 86, 0.5)',
                        'rgba(75, 192, 192, 0.5)',
                        'rgba(153, 102, 255, 0.5)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true
            }
        });
    }

    function initCustomersChart() {
        const ctx = document.getElementById('customersChart').getContext('2d');
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: [<c:forEach items="${customerData}" var="customer">"${customer.name}",</c:forEach>],
                datasets: [{
                    label: 'Total Spent',
                    data: [<c:forEach items="${customerData}" var="customer">${customer.totalSpent},</c:forEach>],
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            callback: function(value) {
                                return '$' + value;
                            }
                        }
                    }
                }
            }
        });
    }

    function exportReport() {
        // In a real implementation, this would call a servlet to generate PDF
        alert('PDF export functionality would be implemented here');
    }
</script>
</body>
</html>