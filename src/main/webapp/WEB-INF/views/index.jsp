<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello world! ${time}</h1>
<a href="${pageContext.request.contextPath}/registration">Registration</a>
<a href="${pageContext.request.contextPath}/inject">Inject test users into DB</a>
<a href="${pageContext.request.contextPath}/users/all">All users</a>
<a href="${pageContext.request.contextPath}/products/all">All products</a>
<a href="${pageContext.request.contextPath}/products/add">Add product</a>
</body>
</html>
