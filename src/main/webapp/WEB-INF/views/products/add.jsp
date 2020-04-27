<%--
  Created by IntelliJ IDEA.
  User: xeddin
  Date: 27.04.2020
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
<h1>Please add product</h1>
<form method="post" action="${pageContext.request.contextPath}/registration">
    Name: <input type="text" name="name">
    Login: <input type="text" name="price">

    <button type="submit">Register</button>
</form>
</body>
</html>
