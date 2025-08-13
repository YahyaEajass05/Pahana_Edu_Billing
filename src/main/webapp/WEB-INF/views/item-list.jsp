<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>PahanaEDU - Items</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <script src="${pageContext.request.contextPath}/resources/js/sort-table.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div class="list-container">
    <div class="list-header">
        <h2>Item Management</h2>
        <a href="${pageContext.request.contextPath}/items/new" class="btn-add">Add New Item</a>
    </div>

    <div class="list-controls">
        <form class="search-form" action="${pageContext.request.contextPath}/items" method="get">
            <input type="text" name="search" placeholder="Search items..." value="${param.search}">
            <button type="submit" class="btn-search">Search</button>
            <a href="${pageContext.request.contextPath}/items" class="btn-reset">Reset</a>
        </form>

        <div class="sort-options">
            <span>Sort by:</span>
            <a href="${pageContext.request.contextPath}/items?sort=name&dir=${sortDir == 'asc' ? 'desc' : 'asc'}&search=${param.search}"
               class="${sortField == 'name' ? 'active' : ''}">Name</a>
            <a href="${pageContext.request.contextPath}/items?sort=price&dir=${sortDir == 'asc' ? 'desc' : 'asc'}&search=${param.search}"
               class="${sortField == 'price' ? 'active' : ''}">Price</a>
            <a href="${pageContext.request.contextPath}/items?sort=quantity&dir=${sortDir == 'asc' ? 'desc' : 'asc'}&search=${param.search}"
               class="${sortField == 'quantity' ? 'active' : ''}">Quantity</a>
        </div>
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

    <table class="data-table sortable">
        <thead>
        <tr>
            <th>ID</th>
            <th data-sort="string">Name</th>
            <th data-sort="string">Description</th>
            <th data-sort="float">Price</th>
            <th data-sort="int">Quantity</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${items}" var="item">
            <tr>
                <td>${item.itemId}</td>
                <td>${item.name}</td>
                <td>${item.description}</td>
                <td><fmt:formatNumber value="${item.price}" type="currency"/></td>
                <td>${item.quantity}</td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/items/edit?id=${item.itemId}"
                       class="btn-edit">Edit</a>
                    <a href="${pageContext.request.contextPath}/items/delete?id=${item.itemId}"
                       class="btn-delete"
                       onclick="return confirm('Are you sure you want to delete this item?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="${pageContext.request.contextPath}/items?page=${currentPage - 1}&sort=${sortField}&dir=${sortDir}&search=${param.search}"
               class="page-link">&laquo; Previous</a>
        </c:if>

        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span class="page-link current">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/items?page=${i}&sort=${sortField}&dir=${sortDir}&search=${param.search}"
                       class="page-link">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <a href="${pageContext.request.contextPath}/items?page=${currentPage + 1}&sort=${sortField}&dir=${sortDir}&search=${param.search}"
               class="page-link">Next &raquo;</a>
        </c:if>
    </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>
</body>
</html>