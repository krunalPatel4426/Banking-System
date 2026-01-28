<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <style>
        /* --- GLOBAL & BACKGROUND --- */
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            font-family: 'Poppins', sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
            overflow: hidden; /* Prevent scroll if content fits */
        }

        /* --- THE CARD --- */
        .security-card {
            background: rgba(255, 255, 255, 0.95);
            width: 100%;
            max-width: 450px;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.2);
            text-align: center;
            position: relative;
            animation: slideUp 0.6s cubic-bezier(0.2, 0.8, 0.2, 1);
            max-height: 90vh; /* Ensure it fits on small screens */
            overflow-y: auto; /* Scroll if validation list gets long */
        }

        /* --- ICON HEADER --- */
        .icon-header {
            width: 80px;
            height: 80px;
            background: #eef2f5;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 20px auto;
            color: #667eea;
            font-size: 2rem;
            box-shadow: inset 0 0 10px rgba(0,0,0,0.05);
        }

        h2 { margin: 0 0 10px 0; color: #333; font-weight: 600; }
        p { margin: 0 0 30px 0; color: #888; font-size: 0.9rem; }

        /* --- INPUTS --- */
        .input-group {
            position: relative;
            margin-bottom: 20px;
            text-align: left;
        }
        .input-group input {
            width: 100%;
            padding: 15px 15px 15px 45px;
            border: 2px solid #e1e1e1;
            border-radius: 12px;
            font-size: 1rem;
            outline: none;
            transition: 0.3s;
            box-sizing: border-box;
        }
        .input-group i {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #aaa;
        }

        .input-group input:focus { border-color: #667eea; }
        .input-group input:focus + i { color: #667eea; }

        /* --- BUTTON --- */
        .btn-update {
            width: 100%;
            padding: 15px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 12px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: 0.3s;
            margin-top: 10px;
            opacity: 0.5; /* Dimmed by default */
            pointer-events: none; /* Disabled by default */
        }

        /* State for Enabled Button */
        .btn-update.active {
            opacity: 1;
            pointer-events: auto;
        }

        .btn-update:hover { transform: translateY(-3px); box-shadow: 0 10px 20px rgba(102, 126, 234, 0.4); }

        /* --- ALERTS --- */
        .alert {
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 0.9rem;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }
        .alert-error { background: #fee2e2; color: #dc2626; border: 1px solid #fecaca; }

        .back-link {
            display: block;
            margin-top: 20px;
            color: #888;
            text-decoration: none;
            font-size: 0.9rem;
        }
        .back-link:hover { color: #333; }

        /* --- VALIDATION STYLES (New) --- */
        .validation-box {
            text-align: left;
            margin-bottom: 20px;
            background: #f8f9fa;
            padding: 15px;
            border-radius: 10px;
            display: none; /* Hidden by default */
        }
        .validation-item {
            font-size: 0.85rem;
            color: #aaa;
            margin-bottom: 5px;
            display: flex;
            align-items: center;
            transition: color 0.3s;
        }
        .validation-item i { margin-right: 8px; font-size: 0.7rem; }

        .validation-item.valid { color: #28a745; font-weight: 500; }
        .validation-item.valid i { color: #28a745; }

        .match-feedback {
            font-size: 0.8rem;
            text-align: right;
            margin-top: -15px;
            margin-bottom: 15px;
            display: none;
        }
        .text-success { color: #28a745; }
        .text-danger { color: #dc3545; }

        @keyframes slideUp {
            from { opacity: 0; transform: translateY(40px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
<div class="security-card">
    <div class="icon-header">
        <i class="fas fa-lock"></i>
    </div>

    <h2>Change Password</h2>
    <p>Ensure your account stays secure.</p>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">
            <i class="fas fa-exclamation-circle"></i>
                ${errorMessage}
        </div>
    </c:if>

    <form action="/perform_change_password" method="post">

        <div class="input-group">
            <input type="password" name="oldPassword" placeholder="Current Password" required>
            <i class="fas fa-key"></i>
        </div>

        <div class="input-group">
            <input type="password" id="newPassword" name="newPassword" placeholder="New Password" required>
            <i class="fas fa-unlock-alt"></i>
        </div>

        <div class="validation-box" id="password-validation">
            <div id="rule-length" class="validation-item"><i class="fas fa-circle"></i> Min 8 characters</div>
            <div id="rule-uppercase" class="validation-item"><i class="fas fa-circle"></i> One Uppercase</div>
            <div id="rule-number" class="validation-item"><i class="fas fa-circle"></i> One Number</div>
            <div id="rule-special" class="validation-item"><i class="fas fa-circle"></i> One Special Char</div>
        </div>

        <div class="input-group">
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" required>
            <i class="fas fa-check-circle"></i>
        </div>

        <div class="match-feedback" id="match-feedback">Passwords do not match</div>

        <button type="submit" id="submitBtn" class="btn-update">Update Password</button>
    </form>

    <a href="/profile" class="back-link">Cancel</a>
</div>

<script src="/js/ValidationForNewPassword.js"></script>
</body>
</html>