<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Successful</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #ffffff;
            font-family: 'Poppins', sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            overflow: hidden;
            text-align: center;
        }

        /* --- 1. WRAPPER --- */
        .animation-wrapper {
            position: relative;
            width: 100px;
            height: 100px;
            margin-bottom: 30px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        /* --- 2. RIPPLE BACKGROUND --- */
        .ripple-background {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            z-index: 0;
            pointer-events: none;
        }

        .circle {
            position: absolute;
            border-radius: 50%;
            background: #28a745;
            opacity: 0.2;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            animation: scaleIn 2.5s infinite cubic-bezier(.36, .11, .89, .32);
        }

        @keyframes scaleIn {
            0% { transform: translate(-50%, -50%) scale(0.8); opacity: 0.5; }
            100% { transform: translate(-50%, -50%) scale(3.5); opacity: 0; }
        }

        /* --- 3. CHECKMARK CIRCLE --- */
        .circle-loader {
            border: 3px solid rgba(0, 0, 0, 0.2);
            border-left-color: #28a745;
            animation: loader-spin 1.2s infinite linear;
            position: relative;
            display: block;
            border-radius: 50%;
            width: 100%;
            height: 100%;
            z-index: 2;
            background-color: white;
        }

        .load-complete {
            -webkit-animation: none;
            animation: none;
            border-color: #28a745;
            transition: border 500ms ease-out;
        }

        /* --- 4. THE CHECKMARK (Corrected Position) --- */
        .checkmark {
            display: none;
        }
        .checkmark.draw {
            display: block;
        }

        .checkmark:after {
            opacity: 1;
            height: 50px;
            width: 25px;
            transform-origin: left top;
            border-right: 4px solid #28a745;
            border-top: 4px solid #28a745;
            content: '';

            /* ✅ FIXED: Moved Left to 17px (was 25px) to visual center */
            left: 17px;
            /* ✅ FIXED: Moved Top to 48px (was 50px) for balance */
            top: 48px;

            position: absolute;
            animation-duration: 800ms;
            animation-timing-function: ease;
            animation-name: checkmark;
            transform: scaleX(-1) rotate(135deg);
        }

        @keyframes loader-spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }

        @keyframes checkmark {
            0% { height: 0; width: 0; opacity: 1; }
            20% { height: 0; width: 25px; opacity: 1; }
            40% { height: 50px; width: 25px; opacity: 1; }
            100% { height: 50px; width: 25px; opacity: 1; }
        }

        /* --- 5. TEXT & BUTTON --- */
        .text-container {
            z-index: 10;
            position: relative;
            animation: fadeIn 0.8s ease forwards;
            animation-delay: 0.5s;
            opacity: 0;
            width: 90%;
            max-width: 400px;
        }

        h1 {
            font-size: 2.5rem;
            color: #333;
            margin: 0;
            font-weight: 600;
        }

        p {
            font-size: 1.1rem;
            color: #666;
            margin: 10px 0 35px 0;
        }

        .btn-home {
            display: inline-block;
            padding: 12px 40px;
            background-color: #f8f9fa;
            color: #333;
            border: 1px solid #ddd;
            border-radius: 50px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
        }

        .btn-home:hover {
            background-color: #28a745;
            color: white;
            border-color: #28a745;
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(40, 167, 69, 0.2);
        }

        @keyframes fadeIn {
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>

<div class="animation-wrapper">

    <div class="ripple-background">
        <div class="circle" style="width: 200px; height: 200px; animation-delay: 0s"></div>
        <div class="circle" style="width: 200px; height: 200px; animation-delay: 0.6s"></div>
        <div class="circle" style="width: 200px; height: 200px; animation-delay: 1.2s"></div>
    </div>

    <div class="circle-loader load-complete">
        <div class="checkmark draw"></div>
    </div>

</div>

<div class="text-container">
    <h1>₹ ${amount}</h1>
    <p>Paid to <strong>${receiver}</strong></p>

    <a href="/dashboard" class="btn-home">Done</a>
</div>

</body>
</html>