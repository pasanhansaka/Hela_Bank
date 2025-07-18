<%--
  Created by IntelliJ IDEA.
  User: Pasan Hansaka
  Date: 7/11/2025
  Time: 1:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hela Bank | Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="body1">
<div class="login-container">
    <h1>Sign In to Account</h1>
    <form action="${pageContext.request.contextPath}/login" method="POST">
        <table>
            <tr>
                <th>Username :</th>
                <td><input type="text" name="username" required></td>
            </tr>
            <tr>
                <th>Password :</th>
                <td><input type="password" name="password" required></td>
            </tr>
            <tr>
                <td colspan="2" class="form-submit-row"><input type="submit" value="Sign In"></td>
            </tr>
        </table>
    </form>
    <p class="register-link">Don't have an account? <a href="${pageContext.request.contextPath}/register.jsp">Register Here</a></p>
</div>
</body>
</html>