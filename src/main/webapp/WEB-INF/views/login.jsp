<%--
  Created by IntelliJ IDEA.
  User: xeddin
  Date: 30.04.2020
  Time: 12:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login page</h1>
<h2 style="color: red">${messageWrongLogin}</h2>
<form action="${pageContext.request.contextPath}/login" method="post">
    Login: <input type="text" name="login" placeholder="Login" value="${login}">
    Password: <input type="password" name="pwd" required placeholder="Password">

    <button type="submit">Login</button>
</form>
</body>
</html>
