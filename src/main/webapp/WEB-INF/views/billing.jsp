<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>PahanaEDU - Billing</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <script src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div class="billing-container">
    <h2>Generate New Bill</h2>

    <form id="billingForm" action="${pageContext.request.contextPath}/billing/create" method="post">
        <div class="form-group">
            <label for="customerId">Customer:</label>
            <select id="customerId" name="customerId" required>
                <option value="">Select Customer</option>
                <c:forEach items="${customers}" var="customer">
                    <option value="${customer.customerId}">
                            ${customer.firstName} ${customer.lastName} (${customer.email})
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Items:</label>
            <table id="itemsTable">
                <thead>
                <tr>
                    <th>Item</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${items}" var="item">
                    <tr>
                        <td>${item.name}</td>
                        <td>
                            <input type="number" name="itemQty_${item.itemId}"
                                   min="1" max="100" value="1" class="qty-input">
                        </td>
                        <td class="price"><fmt:formatNumber value="${item.price}" type="currency"/></td>
                        <td>
                            <input type="checkbox" name="selectedItems" value="${item.itemId}">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-generate">Generate Bill</button>
        </div>
    </form>

    <div class="total-section">
        <h3>Total: <span id="totalAmount">$0.00</span></h3>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="success-message">
                ${successMessage}
        </div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
                ${errorMessage}
        </div>
    </c:if>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>

<script>
    // Calculate total when items are selected
    document.querySelectorAll('input[name="selectedItems"]').forEach(item => {
        item.addEventListener('change', calculateTotal);
    });

    // Quantity change listeners
    document.querySelectorAll('.qty-input').forEach(input => {
        input.addEventListener('change', calculateTotal);
    });

    function calculateTotal() {
        let total = 0;
        document.querySelectorAll('input[name="selectedItems"]:checked').forEach(checkbox => {
            const itemId = checkbox.value;
            const row = checkbox.closest('tr');
            const priceText = row.querySelector('.price').textContent;
            const price = parseFloat(priceText.replace(/[^0-9.-]+/g,""));
            const qty = parseInt(row.querySelector('.qty-input').value);
            total += price * qty;
        });
        document.getElementById('totalAmount').textContent = '$' + total.toFixed(2);
    }
</script>
</body>
</html>