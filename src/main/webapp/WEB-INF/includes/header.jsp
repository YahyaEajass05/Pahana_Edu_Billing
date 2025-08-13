<c:set var="pageTitle" value="Your Page Title"/>
<c:set var="activeTab" value="tabname"/> <!-- customers/items/billing/reports -->
<<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${not empty pageTitle}">PahanaEDU - ${pageTitle}</c:when>
            <c:otherwise>PahanaEDU - Admin Portal</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/logo.png">
</head>
<body>
<header class="main-header">
    <div class="header-container">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/resources/images/logo.png"
                 alt="PahanaEDU Logo" width="40" height="40">
            <h1>PahanaEDU Admin Portal</h1>
        </div>

        <nav class="main-nav">
            <ul>
                <li><a href="${pageContext.request.contextPath}/customers"
                       class="${param.activeTab eq 'customers' ? 'active' : ''}">Customers</a></li>
                <li><a href="${pageContext.request.contextPath}/items"
                       class="${param.activeTab eq 'items' ? 'active' : ''}">Items</a></li>
                <li><a href="${pageContext.request.contextPath}/billing"
                       class="${param.activeTab eq 'billing' ? 'active' : ''}">Billing</a></li>
                <li><a href="${pageContext.request.contextPath}/reports"
                       class="${param.activeTab eq 'reports' ? 'active' : ''}">Reports</a></li>
            </ul>
        </nav>

        <div class="user-controls">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <span class="welcome-msg">Welcome, <c:out value="${sessionScope.user.username}"/></span>
                    <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login" class="btn-login">Login</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
