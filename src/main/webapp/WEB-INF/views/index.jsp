<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title>Title</title>
</head>
<body>
<h1>All part!</h1>
<p><a href="${pageContext.request.contextPath}/registration">Registration</a></p>
<p><a href="${pageContext.request.contextPath}/inject">Inject test data into DB</a></p>
<p><a href="${pageContext.request.contextPath}/logout">Logout</a></p>
<p></p>
<h1>User Part!</h1>
<p><a href="${pageContext.request.contextPath}/products/all">All products</a></p>
<p><a href="${pageContext.request.contextPath}/orders/all">All users orders</a></p>
<p><a href="${pageContext.request.contextPath}/shoppingcart">Show shoppingcart</a></p>
<p></p>
<h1>Admin Part!</h1>
<p><a href="${pageContext.request.contextPath}/admin/users/all">Admin page: All users</a></p>
<p><a href="${pageContext.request.contextPath}/admin/products/all">Admin page: All products</a></p>
<p><a href="${pageContext.request.contextPath}/admin/products/add">Admin page: Add product</a></p>
</body>
</html>
