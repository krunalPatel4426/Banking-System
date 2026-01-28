<%--
  Created by IntelliJ IDEA.
  User: krunalshiyaniya
  Date: 19-01-2026
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Create Account</title>
  <link rel="stylesheet" href="/webjars/bootstrap/5.3.3/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

  <style>
    body {
      background-color: #f0f2f5;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh; /* min-height handles content overflow better */
      font-family: 'Poppins', sans-serif;
      padding: 20px;
    }
    .register-card {
      width: 100%;
      max-width: 450px;
      padding: 30px;
      box-shadow: 0 10px 25px rgba(0,0,0,0.1);
      background: white;
      border-radius: 12px;
    }
    /* Validation Styles from previous chat */
    .validation-box {
      margin-top: 10px;
      padding: 10px;
      background: #f8f9fa;
      border-radius: 8px;
      font-size: 0.85rem;
      display: none;
    }
    .validation-item {
      margin-bottom: 5px;
      color: #6c757d;
      display: flex;
      align-items: center;
      transition: color 0.3s;
    }
    .validation-item i { margin-right: 8px; font-size: 0.8rem; }
    .validation-item.valid { color: #198754; font-weight: 500; }
    .validation-item.valid i { color: #198754; }
  </style>
</head>
<body>
  <div class="register-card">
      <h3 class="text-center mb-4 fw-bold text-primary">
        Join Spring Bank
      </h3>

    <c:if test="${not empty errorMessage}">
      <div class="alert alert-danger">
        <i class="fas fa-exclamation-triangle"></i>${errorMessage}
      </div>
    </c:if>


    <form action="/perform_register" method="post">
      <div class="mb-3">
        <label class="form-label">Username<span class="text-danger">*</span></label>
        <input type="text" class="form-control" name="username" id="username" required>
        <div class="form-text small mt-1" id="username-feedback"></div>
      </div>

      <div class="mb-3">
        <label class="form-label">Email<span class="text-danger">*</span></label>
        <input type="text" class="form-control" name="email" id="email" placeholder="name@example.com" required>
        <div id="email-feedback" class="form-text small mt-1"></div>
      </div>

      <div class="mb-3">
        <label class="form-label">Password<span class="text-danger">*</span></label>
        <div class="input-group">
        <input type="password" id="password" class="form-control" name="password" required>
        <span class="input-group-text bg-white" id="togglePassword" style="cursor: pointer;">
          <i class="fas fa-eye"></i>
        </span>
        </div>
        <div class="validation-box" id="password-validation">
          <div id="rule-length" class="validation-item"><i class="fas fa-circle"></i> At least 8 chars</div>
          <div id="rule-uppercase" class="validation-item"><i class="fas fa-circle"></i> One Uppercase</div>
          <div id="rule-number" class="validation-item"><i class="fas fa-circle"></i> One Number</div>
          <div id="rule-special" class="validation-item"><i class="fas fa-circle"></i> One Special Char</div>
        </div>
      </div>

      <button type="submit" id="submitBtn" class="btn btn-primary w-100 py-2 fw-bold" disabled>
        Create Account
      </button>
      <div class="mt-3 text-center">
        Already have an account? <a href="/login" class="text-decoration-none">Login</a>
      </div>
    </form>

  </div>
  <script src="/js/validation.js"></script>
</body>
</html>
