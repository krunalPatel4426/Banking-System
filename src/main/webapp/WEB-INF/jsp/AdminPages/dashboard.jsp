<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.3.3/css/bootstrap.min.css">
    <style>
        body { background-color: #f4f6f9; }
        .admin-header { background: #343a40; color: white; padding: 15px; }
    </style>
</head>
<body>

<div class="admin-header d-flex justify-content-between align-items-center">
    <h3>🛠️ Bank Admin</h3>
    <a href="/logout" class="btn btn-outline-light btn-sm">Logout</a>
</div>

<div class="container mt-4">
    <div class="card">
        <div class="card-header bg-primary text-white">
            User Management
        </div>
        <div class="card-body">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Balance</th>
                    <th>Role</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="u" items="${users}">
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.username}</td>
                        <td>${u.email}</td>
                        <td>$${u.balance}</td>
                        <td>
                                <span class="badge ${u.role == 'ROLE_ADMIN' ? 'bg-danger' : 'bg-secondary'}">
                                        ${u.role}
                                </span>
                        </td>
                        <td>
                            <a href="#" class="btn btn-sm btn-warning">Freeze</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>