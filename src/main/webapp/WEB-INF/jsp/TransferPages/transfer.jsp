<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Send Money</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="webjars/bootstrap/5.3.3/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        /* --- 1. GLOBAL LAYOUT --- */
        body {
            margin: 0;
            padding: 0;
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }

        /* --- 2. GLASS CARD CONTAINER --- */
        .transfer-card {
            background: rgba(255, 255, 255, 0.9);
            width: 100%;
            max-width: 420px;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 20px 50px rgba(0,0,0,0.1);
            backdrop-filter: blur(10px);
            transform: translateY(30px);
            opacity: 0;
            animation: slideUp 0.8s cubic-bezier(0.2, 0.8, 0.2, 1) forwards;
            position: relative;
        }

        @keyframes slideUp {
            to { transform: translateY(0); opacity: 1; }
        }

        /* --- 3. HEADER SECTION --- */
        .header {
            text-align: center;
            margin-bottom: 30px;
        }
        .header h2 {
            margin: 0;
            color: #333;
            font-weight: 600;
        }
        .balance-pill {
            display: inline-block;
            margin-top: 10px;
            padding: 6px 15px;
            background: #eef2f5;
            color: #666;
            border-radius: 50px;
            font-size: 0.9rem;
            font-weight: 500;
        }
        .balance-pill i {
            color: #28a745;
            margin-right: 5px;
        }

        /* --- 4. FLOATING INPUTS (Material Style) --- */
        .input-group {
            position: relative;
            margin-bottom: 30px;
        }
        .input-group input {
            width: 100%;
            padding: 15px 15px 15px 45px; /* Space for icon */
            border: 2px solid #e1e1e1;
            border-radius: 12px;
            font-size: 1rem;
            font-family: inherit;
            outline: none;
            transition: all 0.3s;
            background: transparent;
            box-sizing: border-box; /* Fix width issues */
        }
        .input-group i {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #aaa;
            transition: color 0.3s;
        }
        .input-group label {
            position: absolute;
            left: 45px;
            top: 50%;
            transform: translateY(-50%);
            color: #999;
            pointer-events: none;
            transition: 0.3s ease all;
            background: rgba(255,255,255,0.9); /* Hide line behind label */
            padding: 0 5px;
        }

        /* Focus Effects */
        .input-group input:focus,
        .input-group input:not(:placeholder-shown) {
            border-color: #4a90e2;
        }
        .input-group input:focus + i {
            color: #4a90e2;
        }
        .input-group input:focus ~ label,
        .input-group input:not(:placeholder-shown) ~ label {
            top: 0;
            font-size: 0.8rem;
            color: #4a90e2;
            font-weight: 600;
        }

        /* --- 5. SUBMIT BUTTON --- */
        .btn-send {
            width: 100%;
            padding: 15px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 12px;
            font-size: 1.1rem;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }
        .btn-send:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 20px rgba(118, 75, 162, 0.4);
        }
        .btn-send:active {
            transform: translateY(-1px);
        }

        /* --- 6. ALERTS --- */
        .alert {
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            text-align: center;
            font-size: 0.9rem;
            animation: shake 0.5s ease-in-out;
        }
        .alert-error {
            background-color: #fee2e2;
            color: #dc2626;
            border: 1px solid #fecaca;
        }

        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-5px); }
            75% { transform: translateX(5px); }
        }

        /* Back Link */
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #888;
            text-decoration: none;
            font-size: 0.9rem;
            transition: color 0.2s;
        }
        .back-link:hover {
            color: #333;
        }
        /* Add these at the bottom of your CSS */

        /* Error State (Red) */
        .input-group input.is-invalid {
            border-color: #dc3545 !important;
        }
        .input-group input.is-invalid + i {
            color: #dc3545 !important;
        }
        .input-group input.is-invalid ~ label {
            color: #dc3545 !important;
        }

        /* Success State (Green) */
        .input-group input.is-valid {
            border-color: #28a745 !important;
        }

        /* NEW: Perfect positioning for the feedback text */
        #account-checker-feedback {
            position: absolute;
            bottom: -22px; /* Pushes text exactly below the input */
            left: 15px;    /* Aligns with the icon/text */
            font-size: 0.8rem;
            font-weight: 500;
            margin: 0;
            white-space: nowrap; /* Prevents wrapping */
        }
    </style>
</head>
<body>

<div class="transfer-card">

    <div class="header">
        <h2>Send Money</h2>
        <div class="balance-pill">
            <i class="fas fa-wallet"></i> Available: <i class="fas fa-rupee-sign"></i> ${user.balance}
        </div>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">
            <i class="fas fa-exclamation-circle"></i> ${errorMessage}
        </div>
    </c:if>
    <form action="/perform_transfer" method="post" autocomplete="off">

        <div class="input-group">
            <input type="number" name="receiverId" id="receiverId" placeholder=" " required>
            <i class="fas fa-university"></i>
            <label for="receiverId">Receiver Account Number</label>

            <div class="form-text small" id="account-checker-feedback"></div>
        </div>

        <div class="input-group mt-4"> <input type="number" name="amount" id="amount" placeholder=" " min="1" step="0.01" required>
            <i class="fas fa-rupee-sign"></i>
            <label for="amount">Amount</label>
        </div>

        <button type="submit" class="btn-send">
            Transfer Now <i class="fas fa-arrow-right"></i>
        </button>

    </form>
    <a href="/dashboard" class="back-link">Cancel and go back</a>

</div>
<script src="/js/AccountChecker.js"></script>
</body>
</html>