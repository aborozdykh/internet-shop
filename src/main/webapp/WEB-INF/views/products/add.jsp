<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
<h1>Add product</h1>
<h2 style="color: blue">${messageEmptyData}</h2>
<form method="post" action="${pageContext.request.contextPath}/products/add">
    Name: <input type="text" name="name" placeholder="Product name" value="${name}">
    Login: <input type="text" name="price" placeholder="price" value="${price}">

    <button type="submit">Add product</button>
</form>
</body>
</html>
