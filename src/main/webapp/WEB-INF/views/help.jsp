<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>PahanaEDU - Help Center</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
            color: #333;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .header {
            margin-bottom: 30px;
            padding-bottom: 15px;
            border-bottom: 1px solid #eee;
        }
        h1 {
            color: #2c3e50;
            margin-top: 0;
        }
        .search-box {
            margin: 20px 0;
            display: flex;
        }
        .search-box input {
            flex-grow: 1;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px 0 0 4px;
            font-size: 16px;
        }
        .search-box button {
            padding: 12px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 0 4px 4px 0;
            cursor: pointer;
        }
        .help-sections {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .help-card {
            border: 1px solid #eee;
            border-radius: 8px;
            padding: 20px;
            transition: transform 0.2s;
        }
        .help-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .help-card h3 {
            margin-top: 0;
            color: #3498db;
        }
        .popular-topics {
            margin-top: 40px;
        }
        .topic-list {
            list-style-type: none;
            padding: 0;
            columns: 2;
            column-gap: 30px;
        }
        .topic-list li {
            margin-bottom: 10px;
            break-inside: avoid;
        }
        .topic-list a {
            color: #3498db;
            text-decoration: none;
            display: block;
            padding: 8px 0;
        }
        .topic-list a:hover {
            text-decoration: underline;
        }
        .contact-support {
            margin-top: 40px;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 8px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Help Center</h1>
        <p>Find answers to common questions or contact our support team</p>

        <form action="${pageContext.request.contextPath}/help" method="post" class="search-box">
            <input type="text" name="search" placeholder="Search help articles...">
            <button type="submit">Search</button>
        </form>
    </div>

    <div class="help-sections">
        <div class="help-card">
            <h3>Getting Started</h3>
            <p>New to PahanaEDU? Learn how to set up your account and navigate the system.</p>
            <a href="${pageContext.request.contextPath}/help?topic=getting-started" class="btn">View Guide</a>
        </div>

        <div class="help-card">
            <h3>Account Management</h3>
            <p>Learn how to update your profile, change passwords, and manage account settings.</p>
            <a href="${pageContext.request.contextPath}/help?topic=account" class="btn">Learn More</a>
        </div>

        <div class="help-card">
            <h3>Billing & Payments</h3>
            <p>Understand billing cycles, payment methods, and invoice management.</p>
            <a href="${pageContext.request.contextPath}/help?topic=billing" class="btn">View Details</a>
        </div>

        <div class="help-card">
            <h3>Inventory Management</h3>
            <p>How to add, edit, and track inventory items in the system.</p>
            <a href="${pageContext.request.contextPath}/help?topic=inventory" class="btn">Read Guide</a>
        </div>

        <div class="help-card">
            <h3>Reporting</h3>
            <p>Generate and interpret various system reports for your organization.</p>
            <a href="${pageContext.request.contextPath}/help?topic=reporting" class="btn">Explore Reports</a>
        </div>

        <div class="help-card">
            <h3>Troubleshooting</h3>
            <p>Common issues and solutions for technical problems.</p>
            <a href="${pageContext.request.contextPath}/help?topic=troubleshooting" class="btn">Find Solutions</a>
        </div>
    </div>

    <div class="popular-topics">
        <h2>Popular Topics</h2>
        <ul class="topic-list">
            <li><a href="${pageContext.request.contextPath}/help?topic=password-reset">How to reset your password</a></li>
            <li><a href="${pageContext.request.contextPath}/help?topic=two-factor">Setting up two-factor authentication</a></li>
            <li><a href="${pageContext.request.contextPath}/help?topic=export-data">Exporting your data</a></li>
            <li><a href="${pageContext.request.contextPath}/help?topic=permissions">Understanding user permissions</a></li>
            <li><a href="${pageContext.request.contextPath}/help?topic=notifications">Managing email notifications</a></li>
            <li><a href="${pageContext.request.contextPath}/help?topic=keyboard-shortcuts">Keyboard shortcuts</a></li>
            <li><a href="${pageContext.request.contextPath}/help?topic=mobile-access">Accessing from mobile devices</a></li>
            <li><a href="${pageContext.request.contextPath}/help?topic=data-import">Importing data from CSV</a></li>
        </ul>
    </div>

    <div class="contact-support">
        <h2>Still Need Help?</h2>
        <p>Our support team is available to assist you with any questions or issues.</p>
        <a href="mailto:support@pahanaedu.com" class="btn">Contact Support</a>
        <p style="margin-top: 15px;">Or call us at: <strong>1-800-PAHANA-EDU</strong></p>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Simple search enhancement
        const searchForm = document.querySelector('.search-box');
        const searchInput = searchForm.querySelector('input[name="search"]');

        searchForm.addEventListener('submit', function(e) {
            if (searchInput.value.trim() === '') {
                e.preventDefault();
                searchInput.focus();
            }
        });

        // Add animation to cards when they come into view
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.style.opacity = 1;
                    entry.target.style.transform = 'translateY(0)';
                }
            });
        }, { threshold: 0.1 });

        document.querySelectorAll('.help-card').forEach(card => {
            card.style.opacity = 0;
            card.style.transform = 'translateY(20px)';
            card.style.transition = 'opacity 0.3s, transform 0.3s';
            observer.observe(card);
        });
    });
</script>
</body>
</html>