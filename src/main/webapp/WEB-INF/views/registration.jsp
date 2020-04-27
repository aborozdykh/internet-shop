<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Hello on registration.</h1>
<form method="post" action="${pageContext.request.contextPath}/registration">
    Name: <input type="text" name="name">
    Login: <input type="text" name="login">
    Password: <input type="text" name="password">
    Repeat password: <input type="text" name="password2">

    <button type="submit">Register</button>
</form>
</body>
</html>
