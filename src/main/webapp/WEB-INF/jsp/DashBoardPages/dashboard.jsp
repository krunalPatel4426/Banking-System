<%--
  Created by IntelliJ IDEA.
  User: krunalshiyaniya
  Date: 16-01-2026
  Time: 12:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>${user.username}'s dashboard</title>
    <link rel="stylesheet" href="webjars/bootstrap/5.3.3/css/bootstrap.min.css"/>
    <style>
        body {
            background-color: #f8f9fa;
        }

        .action-btn{
            height: 100px;
            font-size: 1.2rem;
            display: flex;
            align-items: center;
            justify-content: center;
            box-shadow: 0 4px 6px 0 rgba(0,0,0,0.1);
            transition: transform 0.2s;
        }

        .action-btn:hover{
            transform: translateY(-5px);
        }
        .balance-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a href="#" class="navbar-brand">🏦 Spring Bank</a>
            <div class="d-flex text-white align-items-center justify-content-center">
                <span class="me-3">Welcome, <strong>${user.username}</strong></span>

                <form action="/logout" method="post" class="m-0">
                    <button type="submit" class="btn btn-outline-danger btn-sm justify-content-center">Logout</button>
                </form>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card balance-card p-4 mb-5">
                    <div class="card-body text-center">
                        <h5 class="card-title opacity-75">Total Amount</h5>
                        <h1 class="display-4 fw-bold">₹ ${user.balance}</h1>
                        <p class="mb-0">Account ID : #${user.id}</p>
                        <p class="small opacity-50">Email : ${user.email}</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row g-4">
            <div class="col-md-4">
                <a href="/banking/transfer" class="btn btn-light w-100 action-btn text-primary">💸 Transfer Money</a>
            </div>
            <div class="col-md-4">
                <a href="/history" class="btn btn-light w-100 action-btn text-success">📜 History</a>
            </div>
            <div class="col-md-4">
                <a href="/profile" class="btn btn-light w-100 action-btn text-secondary">⚙️ Profile</a>
            </div>
        </div>
    </div>
    <script src="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
