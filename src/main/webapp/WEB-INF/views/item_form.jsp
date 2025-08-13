<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>PahanaEDU - <c:out value="${not empty item.itemId ? 'Edit' : 'Add'}"/> Item</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <script src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div class="form-container">
    <h2><c:out value="${not empty item.itemId ? 'Edit Item' : 'Add New Item'}"/></h2>

    <form id="itemForm" action="${pageContext.request.contextPath}/items/save" method="post"
          onsubmit="return validateItemForm()">

        <input type="hidden" name="itemId" value="${item.itemId}">

        <div class="form-group">
            <label for="name">Item Name:</label>
            <input type="text" id="name" name="name" value="${item.name}" required
                   maxlength="100" placeholder="Enter item name">
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" name="description" rows="3"
                      maxlength="500" placeholder="Enter item description">${item.description}</textarea>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="price">Price:</label>
                <input type="number" id="price" name="price" value="${item.price}"
                       min="0" step="0.01" required placeholder="0.00">
            </div>

            <div class="form-group">
                <label for="quantity">Quantity:</label>
                <input type="number" id="quantity" name="quantity" value="${item.quantity}"
                       min="0" required placeholder="0">
            </div>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-submit">Save</button>
            <a href="${pageContext.request.contextPath}/items" class="btn-cancel">Cancel</a>
        </div>
    </form>

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
    function validateItemForm() {
        const name = document.getElementById('name').value.trim();
        const price = document.getElementById('price').value;
        const quantity = document.getElementById('quantity').value;

        // Name validation
        if (name === '') {
            alert('Item name is required');
            return false;
        }

        // Price validation
        if (price === '' || parseFloat(price) < 0) {
            alert('Please enter a valid price (must be positive)');
            return false;
        }

        // Quantity validation
        if (quantity === '' || parseInt(quantity) < 0) {
            alert('Please enter a valid quantity (must be positive integer)');
            return false;
        }

        return true;
    }
</script>
</body>
</html>