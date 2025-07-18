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
    <title>Hela Bank | Register</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body  class="body1">
<div class="register-container">
    <h1>Create New Account</h1>
    <form action="${pageContext.request.contextPath}/register" method="POST">
        <table>
            <tr>
                <th>First Name :</th>
                <td><input type="text" name="fname" required/></td>
            </tr>
            <tr>
                <th>Last Name :</th>
                <td><input type="text" name="lname" required/></td>
            </tr>
            <tr>
                <th>Username :</th>
                <td><input type="text" name="username" required/></td>
            </tr>
            <tr>
                <th>NIC :</th>
                <td><input type="text" name="nic" required/></td>
            </tr>
            <tr>
                <th>Email :</th>
                <td><input type="email" name="email" required/></td>
            </tr>
            <tr>
                <th>Mobile :</th>
                <td><input type="text" name="mobile" required/></td>
            </tr>
            <tr>
                <th>Password :</th>
                <td><input type="password" name="password" required></td>
            </tr>
            <tr>
                <td colspan="2" class="form-submit-row"><input type="submit" value="Create Account"/></td>
            </tr>
        </table>
    </form>
    <p class="login-link">Already have an account? <a href="${pageContext.request.contextPath}/login.jsp">Sign In Here</a></p>
</div>
</body>
</html>