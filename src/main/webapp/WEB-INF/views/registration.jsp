<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<div align="center">
    <h1>User registration</h1>
    <h2 style="color: red">${messageDifferentPassword}</h2>
    <form action="${pageContext.request.contextPath}/registration" method="post" >
        Name: <input type="text" name="name" placeholder="Name" value="${name}">
        Login: <input type="text" name="login" placeholder="Login" value="${login}">
        Password: <input type="password" name="pwd" required placeholder="Password">
        Repeat password: <input type="password" name="pwd-repeat" required placeholder="Repeat password">

        <button type="submit">Register</button>
    </form>
</div>
</body>
</html>
