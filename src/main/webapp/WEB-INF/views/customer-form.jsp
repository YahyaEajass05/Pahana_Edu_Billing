<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>PahanaEDU - Customer Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <script src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div class="form-container">
    <h2>
        <c:choose>
            <c:when test="${not empty customer.customerId}">Edit Customer</c:when>
            <c:otherwise>Add New Customer</c:otherwise>
        </c:choose>
    </h2>

    <form id="customerForm" action="${pageContext.request.contextPath}/customers/save" method="post"
          onsubmit="return validateCustomerForm()">

        <input type="hidden" name="customerId" value="${customer.customerId}">
        <input type="hidden" name="accountId" value="${customer.account.accountId}">

        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" value="${customer.firstName}" required>
        </div>

        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" value="${customer.lastName}" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${customer.email}" required>
        </div>

        <div class="form-group">
            <label for="phone">Phone:</label>
            <input type="tel" id="phone" name="phone" value="${customer.phone}" required>
        </div>

        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username"
                   value="${customer.account.username}"
                   <c:if test="${not empty customer.account.username}">readonly</c:if> required>
        </div>

        <c:if test="${empty customer.customerId}">
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
        </c:if>

        <div class="form-actions">
            <button type="submit" class="btn-submit">Save</button>
            <a href="${pageContext.request.contextPath}/customers" class="btn-cancel">Cancel</a>
        </div>
    </form>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
                ${errorMessage}
        </div>
    </c:if>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>
</body>
</html>