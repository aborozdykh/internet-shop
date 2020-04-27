<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Hello on registration.</h1>
<h2 style="color: red">${messageDifferentPassword}</h2>
<h2 style="color: blue">${messageEmptyData}</h2>
<form method="post" action="${pageContext.request.contextPath}/registration">
    Name: <input type="text" name="name" placeholder="Имя" value="${name}">
    Login: <input type="text" name="login" placeholder="Логин" value="${login}">
    Password: <input type="password" name="pwd" required placeholder="Пароль">
    Repeat password: <input type="password" required placeholder="Повторите пароль" name="pwd-repeat">

    <button type="submit">Register</button>
</form>
</body>
</html>
