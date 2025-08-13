<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>PahanaEDU - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <script src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
</head>
<body>
<div class="login-container">
    <div class="login-header">
        <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="PahanaEDU Logo">
        <h1>Welcome to PahanaEDU</h1>
    </div>

    <form id="loginForm" action="${pageContext.request.contextPath}/login" method="post" onsubmit="return validateLoginForm()">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-login">Login</button>
        </div>

        <%-- Error message display --%>
        <div class="error-message">
            <% if (request.getAttribute("errorMessage") != null) { %>
            <p class="error">${errorMessage}</p>
            <% } %>
        </div>
    </form>
</div>
</body>
</html>