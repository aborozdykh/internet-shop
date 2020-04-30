<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello world! ${time}</h1>
<p><a href="${pageContext.request.contextPath}/registration">Registration</a></p>
<p><a href="${pageContext.request.contextPath}/inject">Inject test data into DB</a></p>
<p><a href="${pageContext.request.contextPath}/users/all">All users</a></p>
<p><a href="${pageContext.request.contextPath}/products/all">All products</a></p>
<p><a href="${pageContext.request.contextPath}/orders/all">All orders</a></p>
<p><a href="${pageContext.request.contextPath}/products/add">Add product</a></p>
<p><a href="${pageContext.request.contextPath}/shoppingcart">Show shoppingcart</a></p>
<p></p>
<p><a href="${pageContext.request.contextPath}/admin/products/all">Admin page: All products</a></p>
</body>
</html>
