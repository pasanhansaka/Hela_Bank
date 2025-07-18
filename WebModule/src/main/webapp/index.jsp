<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.pasanhansaka.bank.core.service.BankAccountService" %>
<%@ page import="lk.pasanhansaka.bank.core.entity.CustomerAccount" %>
<%@ page import="lk.pasanhansaka.bank.core.entity.BankAccount" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.pasanhansaka.bank.core.service.TransactionHistory" %>
<%@ page import="lk.pasanhansaka.bank.core.entity.Transaction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hela Bank</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Hela Bank</h1>

<c:if test="${empty pageContext.request.userPrincipal}">
    <div style="text-align: center; margin-top: 50px;">
        <a href="${pageContext.request.contextPath}/login.jsp" class="btn">Login</a>
        <a href="${pageContext.request.contextPath}/register.jsp" class="btn">Register</a>
    </div>
</c:if>

<c:if test="${not empty pageContext.request.userPrincipal}">
    <h3>Hello, ${pageContext.request.userPrincipal.name}</h3>
    <div style="text-align: center; margin-bottom: 30px;">
        <a href="${pageContext.request.contextPath}/logout" class="btn" style="background-color: red">Logout</a>
    </div>

    <%--   Bank Accounts--%>
    <%
        try {
            CustomerAccount customer = (CustomerAccount) request.getSession().getAttribute("customer");

            InitialContext initialContext = new InitialContext();
            BankAccountService bankAccountService = (BankAccountService) initialContext.lookup("lk.pasanhansaka.bank.core.service.BankAccountService");
            List<BankAccount> bankAccounts = bankAccountService.getCustomersAllBankAccountsByNIC(customer.getNic());
            pageContext.setAttribute("bankAccounts", bankAccounts);

            if (bankAccounts.isEmpty()) {
    %>
    <div style="text-align: center; margin-top: 30px;">
        <a href="${pageContext.request.contextPath}/customer/newAccount" class="btn">Open New Savings Bank Account</a>
    </div>
    <%
    } else {
    %>
    <h2>Your Bank Accounts</h2>
    <table>
        <tr>
            <th>Account Number</th>
            <th>Account Balance(Rs.)</th>
            <th>Account Type</th>
            <th>Account Status</th>
        </tr>
        <c:forEach var="account" items="${bankAccounts}">
            <tr>
                <td>${account.accountNumber}</td>
                <td>${account.balance}</td>
                <td>${account.bankAccountType}</td>
                <td>${account.bankAccountStatus}</td>
                <td>
                    <div style="text-align: center; margin-top: 20px;">
                        <c:choose>
                            <c:when test="${account.bankAccountStatus == 'ACTIVE'}">
                                <a href="${pageContext.request.contextPath}/customer/account/status?action=disable&accountNumber=${account.accountNumber}"
                                   class="btn" style="background-color: red">Disable</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/customer/account/status?action=enable&accountNumber=${account.accountNumber}"
                                   class="btn" style="background-color: #2ecc71">Enable</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div style="text-align: center; margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/customer/newAccount" class="btn">Open New Savings Bank Account</a>
    </div>
    <%
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    %>

    <hr>

    <%--Transaction Buttns--%>
    <div class="action-button-container">
        <button class="btn" onclick="openModal('depositModal')">Deposit</button>
        <button class="btn" onclick="openModal('withdrawModal')">Withdraw</button>
        <button class="btn" onclick="openModal('transferModal')">Transfer</button>
        <button class="btn" onclick="openModal('scheduleTransferModal')">Schedule Transfer</button>
    </div>

    <%--    Deposit Model--%>
    <div id="depositModal" class="modal">
        <div class="modal-content">
            <span class="close-button" onclick="closeModal('depositModal')">&times;</span>
            <form action="${pageContext.request.contextPath}/customer/deposit" method="POST">
                <table>
                    <thead>Deposit</thead>
                    <tr>
                        <th>Your Account</th>
                        <td>
                            <label>
                                <select name="accountNumber">
                                    <c:forEach var="account" items="${bankAccounts}">
                                        <option value="${account.accountNumber}">${account.accountNumber}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <th>Amount</th>
                        <td><input type="number" name="amount" required min="1"></td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td><textarea name="description" required></textarea></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit" value="Deposit"></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <%--    Withdraw Model    --%>
    <div id="withdrawModal" class="modal">
        <div class="modal-content">
            <span class="close-button" onclick="closeModal('withdrawModal')">&times;</span>
            <form action="${pageContext.request.contextPath}/customer/withdraw" method="POST">
                <table>
                    <thead>Withdraw</thead>
                    <tr>
                        <th>Your Account</th>
                        <td>
                            <select name="accountNumber">
                                <c:forEach var="account" items="${bankAccounts}">
                                    <option value="${account.accountNumber}">${account.accountNumber}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Amount</th>
                        <td><input type="number" name="amount" required min="1"></td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td><textarea name="description" required></textarea></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit" value="Withdraw"></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <%--    Fund Transfer Model--%>
    <div id="transferModal" class="modal">
        <div class="modal-content">
            <span class="close-button" onclick="closeModal('transferModal')">&times;</span>
            <form action="${pageContext.request.contextPath}/customer/fundTransfer" method="POST">
                <table>
                    <thead>Money Transfer</thead>
                    <tr>
                        <th>Your Account</th>
                        <td>
                            <select name="accountNumber">
                                <c:forEach var="account" items="${bankAccounts}">
                                    <option value="${account.accountNumber}">${account.accountNumber}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Receiver's Account Number</th>
                        <td><input type="text" name="receiverAccountNumber" required></td>
                    </tr>
                    <tr>
                        <th>Amount</th>
                        <td><input type="number" name="amount" required min="1"></td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td><textarea name="description" required></textarea></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit" value="Transfer"></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <%--    Schedule Fund Transfer Model--%>
    <div id="scheduleTransferModal" class="modal">
        <div class="modal-content">
            <span class="close-button" onclick="closeModal('scheduleTransferModal')">&times;</span>
            <form action="${pageContext.request.contextPath}/customer/scheduleFundTransfer" method="POST">
                <table>
                    <thead>Schedule Money Transfer</thead>
                    <tr>
                        <th>Your Account</th>
                        <td>
                            <select name="accountNumber">
                                <c:forEach var="account" items="${bankAccounts}">
                                    <option value="${account.accountNumber}">${account.accountNumber}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Receiver's Account Number</th>
                        <td><input type="text" name="receiverAccountNumber" required></td>
                    </tr>
                    <tr>
                        <th>Amount</th>
                        <td><input type="number" name="amount" required min="1"></td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td><textarea name="description" required></textarea></td>
                    </tr>
                    <tr>
                        <th>Date & Time</th>
                        <td><input type="datetime-local" name="datetime" required></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit" value="Schedule Transfer"></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <%--    Transaction History--%>
    <%
        try {
            CustomerAccount customer = (CustomerAccount) request.getSession().getAttribute("customer");

            InitialContext initialContext = new InitialContext();
            TransactionHistory transactionHistory = (TransactionHistory) initialContext.lookup("lk.pasanhansaka.bank.core.service.TransactionHistory");
            List<Transaction> transactions = transactionHistory.getTransactionHistoryByCustomerNIC(customer.getNic());
            pageContext.setAttribute("transactions", transactions);

            if (!transactions.isEmpty()) {
    %>
    <h2>Transaction History</h2>
    <table>
        <tr>
            <th>Sender's Bank Account</th>
            <th>Receiver's Bank Account</th>
            <th>Amount</th>
            <th>Transaction Type</th>
            <th>Transaction Date</th>
            <th>Transaction Description</th>
        </tr>
        <c:forEach var="transaction" items="${transactions}">
            <tr>
                <td>${transaction.fromBankAccount.accountNumber}</td>
                <td>${transaction.toBankAccount.accountNumber}</td>
                <td>${transaction.amount}</td>
                <td>${transaction.transactionType}</td>
                <td>${transaction.date}</td>
                <td>${transaction.description}</td>
            </tr>
        </c:forEach>
    </table>
    <%
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    %>

</c:if>

<script src="js/script.js"></script>
</body>
</html>