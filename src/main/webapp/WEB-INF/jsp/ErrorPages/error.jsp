<%--
  Created by IntelliJ IDEA.
  User: krunalshiyaniya
  Date: 16-01-2026
  Time: 12:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Error - Spring Bank</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.3.3/css/bootstrap.min.css">
    <style>
        body{
            background-color: #f8d7da;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .error-card{
            max-width: 500px;
            text-align: center;
            border: none;
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }

        .error-code{
            font-size: 5rem;
            font-weight: bold;
            color: #dc3545;
        }
    </style>
</head>
<body>
    <div class="card error-card p-5">
        <h2 class="mb-3">Oops! Something went wrong.</h2>
        
        <p class="lead text-muted mb-4">
            ${errorMessage}
        </p>
        
        <div class="d-flex justify-content-center gap-3">
            <button onclick="window.location.href='/dashboard'" class="btn btn-outline-danger">
                Go to Dashboard
            </button>
        </div>
    </div>
</body>
</html>
