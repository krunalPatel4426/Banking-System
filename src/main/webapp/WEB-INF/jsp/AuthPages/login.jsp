<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Bank Login</title>
        <link rel="stylesheet" href="/webjars/bootstrap/5.3.3/css/bootstrap.min.css">
        <style>
            body {
                background-color: #f0f2f5;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .login-card {
                width: 100%;
                max-width: 400px;
                padding: 20px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                background: white;
                border-radius: 8px;
            }
        </style>
    </head>
    <body>
        <div class="login-card">
            <h3 class="text-center mb-4">Secure Banking</h3>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">
                    ${errorMessage}
                </div>
            </c:if>

            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">
                    ${successMessage}
                </div>
            </c:if>

            <form method="POST" action="/perform_login">
                <div class="mb-3">
                    <label for="username" class="form-label">Username<span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="username" name="username" required>
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Password<span class="text-danger">*</span></label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>

                <button type="submit" class="btn btn-primary w-100">Login</button>

                <div class="mt-3 text-center">
                    <a href="/register">Create an Account</a>
                </div>
            </form>
        </div>
    <script src="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>

    </body>
</html>