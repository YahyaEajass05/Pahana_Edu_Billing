<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>PahanaEDU - Help Center</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div class="help-container">
    <h2>Help Center</h2>

    <div class="help-section">
        <h3>Frequently Asked Questions</h3>
        <div class="faq-item">
            <div class="faq-question">How do I create a new customer?</div>
            <div class="faq-answer">
                <p>Navigate to the Customers page and click "Add New Customer". Fill in the required fields and click Save.</p>
            </div>
        </div>

        <div class="faq-item">
            <div class="faq-question">How do I generate a bill?</div>
            <div class="faq-answer">
                <p>Go to the Billing section, select a customer and items, then click "Generate Bill".</p>
            </div>
        </div>

        <div class="faq-item">
            <div class="faq-question">Where can I view reports?</div>
            <div class="faq-answer">
                <p>Reports are available in the Reports section. You can filter by date range and report type.</p>
            </div>
        </div>
    </div>

    <div class="help-section">
        <h3>Contact Support</h3>
        <div class="contact-info">
            <p><strong>Email:</strong> support@pahanaedu.com</p>
            <p><strong>Phone:</strong> +1 (555) 123-4567</p>
            <p><strong>Hours:</strong> Monday-Friday, 9am-5pm EST</p>
        </div>
    </div>

    <div class="help-section">
        <h3>System Information</h3>
        <div class="system-info">
            <p><strong>Version:</strong> 1.0.0</p>
            <p><strong>Last Updated:</strong> <jsp:useBean id="date" class="java.util.Date" />
                <fmt:formatDate value="${date}" pattern="MMMM d, yyyy" /></p>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>

<script>
    // Toggle FAQ answers
    document.querySelectorAll('.faq-question').forEach(question => {
        question.addEventListener('click', () => {
            const answer = question.nextElementSibling;
            answer.style.display = answer.style.display === 'none' ? 'block' : 'none';
        });
    });
</script>
</body>
</html>