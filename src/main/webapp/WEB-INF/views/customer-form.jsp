<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>PahanaEDU - Customer Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            color: #333;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            margin-top: 0;
            color: #2c3e50;
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="email"],
        input[type="tel"],
        input[type="date"],
        select,
        textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-row {
            display: flex;
            gap: 15px;
        }
        .form-row .form-group {
            flex: 1;
        }
        .btn-group {
            margin-top: 20px;
            display: flex;
            justify-content: flex-end;
            gap: 10px;
        }
        button {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }
        .btn-save {
            background-color: #4CAF50;
            color: white;
        }
        .btn-cancel {
            background-color: #f44336;
            color: white;
        }
        .required:after {
            content: " *";
            color: red;
        }
        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>${empty customer.customerId ? 'Create New Customer' : 'Edit Customer'}</h1>

    <form action="${pageContext.request.contextPath}/customers" method="post">
        <input type="hidden" name="action" value="${empty customer.customerId ? 'create' : 'update'}">
        <input type="hidden" name="customerId" value="${customer.customerId}">

        <div class="form-row">
            <div class="form-group">
                <label for="firstName" class="required">First Name</label>
                <input type="text" id="firstName" name="firstName"
                       value="${customer.firstName}" required>
            </div>

            <div class="form-group">
                <label for="lastName" class="required">Last Name</label>
                <input type="text" id="lastName" name="lastName"
                       value="${customer.lastName}" required>
            </div>
        </div>

        <div class="form-group">
            <label for="email" class="required">Email</label>
            <input type="email" id="email" name="email"
                   value="${customer.email}" required>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="phone">Phone</label>
                <input type="tel" id="phone" name="phone"
                       value="${customer.phone}">
            </div>

            <div class="form-group">
                <label for="dob">Date of Birth</label>
                <input type="date" id="dob" name="dob"
                       value="${customer.dob}">
            </div>
        </div>

        <div class="form-group">
            <label for="address">Address</label>
            <textarea id="address" name="address" rows="3">${customer.address}</textarea>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="city">City</label>
                <input type="text" id="city" name="city"
                       value="${customer.city}">
            </div>

            <div class="form-group">
                <label for="state">State</label>
                <input type="text" id="state" name="state"
                       value="${customer.state}">
            </div>

            <div class="form-group">
                <label for="zipCode">Zip Code</label>
                <input type="text" id="zipCode" name="zipCode"
                       value="${customer.zipCode}">
            </div>
        </div>

        <div class="form-group">
            <label for="customerType">Customer Type</label>
            <select id="customerType" name="customerType">
                <option value="INDIVIDUAL" ${customer.customerType == 'INDIVIDUAL' ? 'selected' : ''}>Individual</option>
                <option value="BUSINESS" ${customer.customerType == 'BUSINESS' ? 'selected' : ''}>Business</option>
                <option value="EDUCATIONAL" ${customer.customerType == 'EDUCATIONAL' ? 'selected' : ''}>Educational</option>
            </select>
        </div>

        <div class="form-group">
            <label>
                <input type="checkbox" name="active" ${customer.active ? 'checked' : ''}>
                Active Customer
            </label>
        </div>

        <div class="btn-group">
            <button type="button" class="btn-cancel"
                    onclick="window.location.href='${pageContext.request.contextPath}/customers'">
                Cancel
            </button>
            <button type="submit" class="btn-save">
                ${empty customer.customerId ? 'Create Customer' : 'Update Customer'}
            </button>
        </div>
    </form>
</div>

<script>
    // Basic client-side validation
    document.querySelector('form').addEventListener('submit', function(e) {
        const firstName = document.getElementById('firstName').value.trim();
        const lastName = document.getElementById('lastName').value.trim();
        const email = document.getElementById('email').value.trim();

        if (!firstName || !lastName || !email) {
            e.preventDefault();
            alert('Please fill in all required fields');
            return false;
        }

        if (email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
            e.preventDefault();
            alert('Please enter a valid email address');
            return false;
        }
    });
</script>
</body>
</html>