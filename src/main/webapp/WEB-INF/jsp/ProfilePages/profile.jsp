<%--
  Created by IntelliJ IDEA.
  User: krunalshiyaniya
  Date: 16-01-2026
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <style>
        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            font-family: 'Poppins', sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
        }

        /* --- THE GLASS CARD --- */
        .profile-card {
            background: rgba(255, 255, 255, 0.95);
            width: 850px;
            max-width: 90%;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.1);
            display: flex;
            overflow: hidden;
            animation: fadeIn 0.8s ease-out;
        }

        /* --- LEFT SIDE (sidebar) --- */
        .sidebar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            width: 300px;
            padding: 40px 20px;
            text-align: center;
            color: white;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        /* Avatar Circle */
        .avatar-circle {
            width: 120px;
            height: 120px;
            background: rgba(255,255,255,0.2);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 3rem;
            font-weight: bold;
            border: 4px solid rgba(255,255,255,0.3);
            margin-bottom: 20px;
        }

        .username { font-size: 1.5rem; margin: 0; font-weight: 600; }
        .role { font-size: 0.9rem; opacity: 0.8; margin-top: 5px; }

        .btn-sidebar {
            margin-top: 30px;
            background: rgba(255,255,255,0.2);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 50px;
            text-decoration: none;
            transition: 0.3s;
            width: 80%;
            display: inline-block;
        }
        .btn-sidebar:hover { background: rgba(255,255,255,0.4); }

        /* --- RIGHT SIDE (Details) --- */
        .content {
            flex: 1;
            padding: 40px;
            position: relative;
        }

        .section-title {
            color: #333;
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
            margin-bottom: 25px;
            font-weight: 600;
        }

        .info-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 25px;
        }

        .info-item label {
            display: block;
            font-size: 0.85rem;
            color: #888;
            margin-bottom: 5px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        .info-item p {
            font-size: 1.1rem;
            color: #333;
            font-weight: 500;
            margin: 0;
            border-bottom: 1px solid #f0f0f0;
            padding-bottom: 5px;
        }

        .close-btn {
            position: absolute;
            top: 20px;
            right: 20px;
            color: #aaa;
            font-size: 1.5rem;
            text-decoration: none;
            transition: 0.3s;
        }
        .close-btn:hover { color: #dc3545; transform: rotate(90deg); }

        /* Responsive */
        @media (max-width: 768px) {
            .profile-card { flex-direction: column; }
            .sidebar { width: 100%; padding: 30px 0; }
            .content { padding: 20px; }
            .info-grid { grid-template-columns: 1fr; }
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
    <div class="profile-card">
        
        <div class="sidebar">
            <div class="avatar-circle">
                ${user.username.charAt(0).toString().toUpperCase()}
            </div>
            
            <h2 class="username">@${user.username}</h2>
            <p class="role">Premium Member</p>

            <a href="/logout" class="btn-sidebar">
                <i class="fas fa-sign-out-alt"></i>Logout
            </a>
        </div>

        <div class="content">
            <a href="/dashboard" class="close-btn"><i class="fas fa-times"></i></a>
            <h3 class="section-title">Account Information</h3>

            <div class="info-grid">
                <div class="info-item">
                    <lable>Account Number</lable>
                    <p> ${user.email}</p>
                </div>

                <div class="info-item">
                    <lable>Current balance</lable>
                    <p style="color: #28a745;">₹ ${user.balance}</p>
                </div>

                <div class="info-item">
                    <lable>Account Status</lable>
                    <p>Active <i class="fas fa-check-circle" style="color:#28a745; margin-left:5px;"></i></p>
                </div>
            </div>

            <h3 class="section-title" style="margin-top: 30px">Security</h3>
            <div class="info-item">
                <p>••••••••
                    <a href="/change-password" style="float:right; font-size:0.8rem; text-decoration:none; color:#667eea;">Change</a>
                </p>
            </div>
        </div>
    </div>
</body>
</html>
