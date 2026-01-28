<%--
  Created by IntelliJ IDEA.
  User: krunalshiyaniya
  Date: 16-01-2026
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction History</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <style>
        /* --- GLOBAL & LAYOUT --- */
        body {
            background-color: #f4f7f6;
            font-family: 'Poppins', sans-serif;
            color: #333;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        /* --- CARD CONTAINER --- */
        .history-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.05);
            overflow: hidden;
            animation: slideIn 0.6s ease-out;
        }

        /* --- HEADER --- */
        .card-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 25px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 15px;
        }

        .card-header h2 { margin: 0; font-size: 1.5rem; display: flex; align-items: center; gap: 10px; }

        /* Buttons in Header */
        .header-actions { display: flex; gap: 12px; align-items: center; }

        .btn-export {
            background-color: rgba(255, 255, 255, 0.95);
            color: #d32f2f;
            text-decoration: none;
            padding: 8px 18px;
            border-radius: 50px;
            font-size: 0.9rem;
            font-weight: 600;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }
        .btn-export:hover { transform: translateY(-2px); background: white; box-shadow: 0 4px 10px rgba(0,0,0,0.15); }

        .btn-back {
            background: rgba(255,255,255,0.2);
            color: white;
            text-decoration: none;
            padding: 8px 18px;
            border-radius: 50px;
            font-size: 0.9rem;
            transition: 0.3s;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }
        .btn-back:hover { background: rgba(255,255,255,0.35); }

        /* --- FILTER BAR (New Design) --- */
        .filter-container {
            padding: 20px 30px;
            background: #f8f9fa;
            border-bottom: 1px solid #eef2f5;
        }

        .filter-form {
            display: flex;
            align-items: flex-end; /* Aligns inputs and buttons to bottom */
            gap: 15px;
            flex-wrap: wrap;
        }

        .filter-group {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .filter-group label {
            font-size: 0.8rem;
            color: #8898aa;
            font-weight: 600;
            text-transform: uppercase;
            margin-left: 2px;
        }

        .form-control {
            padding: 10px 15px;
            border: 1px solid #e1e4e8;
            border-radius: 8px;
            font-family: inherit;
            color: #444;
            background: white;
            font-size: 0.9rem;
            transition: border 0.3s;
        }
        .form-control:focus { border-color: #667eea; outline: none; }

        select.form-control { padding-right: 30px; cursor: pointer; }

        .btn-search {
            padding: 10px 20px;
            background: #667eea;
            color: white;
            border: none;
            border-radius: 8px;
            font-weight: 500;
            cursor: pointer;
            transition: 0.3s;
            height: 42px; /* Matches input height */
        }
        .btn-search:hover { background: #5a6fd6; }

        .btn-reset {
            padding: 10px 15px;
            background: #e9ecef;
            color: #666;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 500;
            font-size: 0.9rem;
            height: 42px;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: 0.3s;
        }
        .btn-reset:hover { background: #dee2e6; color: #333; }

        /* --- TABLE --- */
        .table-responsive { width: 100%; overflow-x: auto; }
        table { width: 100%; border-collapse: collapse; }

        th {
            background: white;
            color: #8898aa;
            font-weight: 600;
            font-size: 0.8rem;
            text-transform: uppercase;
            padding: 15px 20px;
            text-align: left;
            border-bottom: 2px solid #e9ecef;
        }

        td {
            padding: 18px 20px;
            border-bottom: 1px solid #e9ecef;
            vertical-align: middle;
            color: #525f7f;
            font-size: 0.95rem;
        }
        tr:last-child td { border-bottom: none; }
        tr:hover { background-color: #fafbfc; }

        /* --- ICONS & BADGES --- */
        .icon-box {
            width: 36px; height: 36px;
            border-radius: 50%;
            display: flex; align-items: center; justify-content: center;
            font-size: 0.9rem; margin-right: 12px;
        }
        .icon-credit { background: #e6f9ed; color: #28a745; }
        .icon-debit { background: #ffebee; color: #dc3545; }

        .badge { padding: 5px 10px; border-radius: 6px; font-size: 0.75rem; font-weight: 600; text-transform: uppercase; letter-spacing: 0.5px; }
        .badge-success { background: #d1fae5; color: #065f46; }
        .badge-failed { background: #fee2e2; color: #991b1b; }

        .text-credit { color: #28a745; font-weight: 600; }
        .text-debit { color: #dc3545; font-weight: 600; }
        .text-muted { color: #8898aa; font-size: 0.85rem; }

        /* --- PAGINATION --- */
        .pagination { padding: 25px; display: flex; justify-content: center; gap: 8px; background: white; border-top: 1px solid #eef2f5; }
        .page-link {
            padding: 8px 14px;
            border: 1px solid #e1e4e8;
            border-radius: 6px;
            color: #525f7f;
            text-decoration: none;
            font-size: 0.9rem;
            transition: 0.2s;
            display: flex; align-items: center; gap: 5px;
        }
        .page-link:hover { background: #f6f9fc; border-color: #d1d5db; }
        .page-link.disabled { opacity: 0.5; pointer-events: none; background: #f8f9fa; }
        .page-info { display: flex; align-items: center; color: #8898aa; font-size: 0.9rem; margin: 0 10px; }

        /* --- EMPTY STATE --- */
        .empty-state { text-align: center; padding: 60px 20px; color: #8898aa; }
        .empty-state i { font-size: 3rem; margin-bottom: 15px; opacity: 0.3; display: block; }

        @media (max-width: 768px) {
            .filter-form { flex-direction: column; align-items: stretch; }
            .btn-search, .btn-reset { width: 100%; }
            .card-header { flex-direction: column; align-items: flex-start; }
            .header-actions { width: 100%; justify-content: space-between; margin-top: 15px; }
        }

        @keyframes slideIn { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: translateY(0); } }
    </style>
</head>
<body>

<div class="container">
    <div class="history-card">

        <div class="card-header">
            <h2><i class="fas fa-history"></i> Transactions</h2>
            <div class="header-actions">
                <c:url value="/export-transactions" var="exportUrl">
                    <c:if test="${not empty param.from}"><c:param name="from" value="${param.from}"/></c:if>
                    <c:if test="${empty param.from}"><c:param name="from" value=""></c:param></c:if>
                    <c:if test="${not empty param.to}"><c:param name="to" value="${param.to}"/></c:if>
                    <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
                </c:url>
                <a href="${exportUrl}" class="btn-export">
                    <i class="fas fa-file-pdf"></i> Export PDF
                </a>

                <a href="/dashboard" class="btn-back">
                    <i class="fas fa-arrow-left"></i> Dashboard
                </a>
            </div>
        </div>

        <div class="filter-container">
            <form action="/history" method="GET" class="filter-form">
                <jsp:useBean id="now" class="java.util.Date"/>
                <fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="todayDate"/>

                <div class="filter-group">
                    <label for="from">From</label>
                    <input type="date" id="from" name="from" class="form-control"
                           value="${not empty param.from ? param.from : ''}">
                </div>

                <div class="filter-group">
                    <label for="to">To</label>
                    <input type="date" id="to" name="to" class="form-control"
                           value="${not empty param.to ? param.to : todayDate}">
                </div>

                <div class="filter-group">
                    <label for="sort">Sort By</label>
                    <select name="sort" id="sort" class="form-control">
                        <option value="dateDesc" ${param.sort == 'dateDesc' ? 'selected' : ''}>Newest First</option>
                        <option value="dateAsc" ${param.sort == 'dateAsc' ? 'selected' : ''}>Oldest First</option>
                        <option value="amountDesc" ${param.sort == 'amountDesc' ? 'selected' : ''}>Amount: High to Low</option>
                        <option value="amountAsc" ${param.sort == 'amountAsc' ? 'selected' : ''}>Amount: Low to High</option>
                    </select>
                </div>

                <button type="submit" class="btn-search">
                    <i class="fas fa-search"></i> Apply
                </button>

                <c:if test="${not empty param.from or not empty param.to or not empty param.sort}">
                    <a href="/history" class="btn-reset">
                        <i class="fas fa-times"></i> Clear
                    </a>
                </c:if>
            </form>
        </div>

        <div class="table-responsive">
            <c:choose>
                <c:when test="${empty transactions}">
                    <div class="empty-state">
                        <i class="fas fa-receipt"></i>
                        <p>No transactions found for the selected filters.</p>
                        <c:if test="${not empty param.from}">
                            <a href="/history" style="color: #667eea; text-decoration: none; font-weight: 500;">Clear Filters</a>
                        </c:if>
                    </div>
                </c:when>

                <c:otherwise>
                    <table>
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Type</th>
                            <th>Description</th>
                            <th>Status</th>
                            <th>Amount</th>
                            <th>Balance</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="txn" items="${transactions}">
                            <tr>
                                <td class="text-muted">
                                    <i class="far fa-calendar-alt" style="margin-right: 5px;"></i> ${txn.date}
                                </td>

                                <td>
                                    <div style="display: flex; align-items: center;">
                                        <div class="icon-box ${txn.transactionType == 'CREDITED' ? 'icon-credit' : 'icon-debit'}">
                                            <i class="fas ${txn.transactionType == 'CREDITED' ? 'fa-arrow-down' : 'fa-arrow-up'}"></i>
                                        </div>
                                        <span style="font-weight: 500; font-size: 0.85rem;">
                                                ${txn.transactionType}
                                        </span>
                                    </div>
                                </td>

                                <td style="color: #555;">${txn.message}</td>

                                <td>
                                        <span class="badge ${txn.paymentStatus == 'SUCCESS' ? 'badge-success' : 'badge-failed'}">
                                                ${txn.paymentStatus}
                                        </span>
                                </td>

                                <td>
                                        <span class="${txn.transactionType == 'CREDITED' ? 'text-credit' : 'text-debit'}">
                                            ${txn.transactionType == 'CREDITED' ? '+' : '-'} ₹${txn.amount}
                                        </span>
                                </td>

                                <td class="text-muted">₹${txn.remainingBalance}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>

        <c:if test="${totalPages > 1}">
            <div class="pagination">
                <c:url value="/history" var="prevUrl">
                    <c:param name="page" value="${currentPage - 1}"/>
                    <c:if test="${not empty param.from}"><c:param name="from" value="${param.from}"/></c:if>
                    <c:if test="${not empty param.to}"><c:param name="to" value="${param.to}"/></c:if>
                    <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
                </c:url>

                <c:url value="/history" var="nextUrl">
                    <c:param name="page" value="${currentPage + 1}"/>
                    <c:if test="${not empty param.from}"><c:param name="from" value="${param.from}"/></c:if>
                    <c:if test="${not empty param.to}"><c:param name="to" value="${param.to}"/></c:if>
                    <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
                </c:url>

                <a href="${prevUrl}" class="page-link ${currentPage == 0 ? 'disabled' : ''}">
                    <i class="fas fa-chevron-left"></i> Prev
                </a>

                <span class="page-info">
                    Page ${currentPage + 1} of ${totalPages}
                </span>

                <a href="${nextUrl}" class="page-link ${currentPage + 1 == totalPages ? 'disabled' : ''}">
                    Next <i class="fas fa-chevron-right"></i>
                </a>
            </div>
        </c:if>
    </div>
</div>

</body>
</html>