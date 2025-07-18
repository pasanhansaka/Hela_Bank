<%--
  Created by IntelliJ IDEA.
  User: Pasan Hansaka
  Date: 7/16/2025
  Time: 3:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hela Bank | Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
<div class="error-page-container">
    <h1>An Error Occurred.</h1>
    <a href="${pageContext.request.contextPath}/index.jsp">Go to Home Page</a>
    <h5>
        <span>${requestScope["jakarta.servlet.error.status_code"]}</span> :
        <span>${requestScope["jakarta.servlet.error.message"]}</span>
    </h5>
</div>
</body>
</html>
