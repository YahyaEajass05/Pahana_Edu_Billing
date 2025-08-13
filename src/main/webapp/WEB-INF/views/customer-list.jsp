<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>PahanaEDU - Customers</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div class="list-container">
    <div class="list-header">
        <h2>Customer Management</h2>
        <a href="${pageContext.request.contextPath}/customers/new" class="btn-add">Add New Customer</a>
    </div>

    <div class="search-bar">
        <form action="${pageContext.request.contextPath}/customers" method="get">
            <input type="text" name="search" placeholder="Search customers..." value="${param.search}">
            <button type="submit" class="btn-search">Search</button>
            <a href="${pageContext.request.contextPath}/customers" class="btn-reset">Reset</a>
        </form>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="success-message">
                ${successMessage}
        </div>
    </c:if>

    <table class="data-table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Username</th>
            <th>Created Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${customers}" var="customer">
            <tr>
                <td>${customer.customerId}</td>
                <td>${customer.firstName} ${customer.lastName}</td>
                <td>${customer.email}</td>
                <td>${customer.phone}</td>
                <td>${customer.account.username}</td>
                <td><fmt:formatDate value="${customer.account.createdDate}" pattern="yyyy-MM-dd"/></td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/customers/edit?id=${customer.customerId}"
                       class="btn-edit">Edit</a>
                    <a href="${pageContext.request.contextPath}/customers/delete?id=${customer.customerId}"
                       class="btn-delete"
                       onclick="return confirm('Are you sure you want to delete this customer?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="${pageContext.request.contextPath}/customers?page=${currentPage - 1}&search=${param.search}"
               class="page-link">&laquo; Previous</a>
        </c:if>

        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span class="page-link current">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/customers?page=${i}&search=${param.search}"
                       class="page-link">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <a href="${pageContext.request.contextPath}/customers?page=${currentPage + 1}&search=${param.search}"
               class="page-link">Next &raquo;</a>
        </c:if>
    </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>
</body>
</html>