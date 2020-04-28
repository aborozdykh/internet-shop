<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ShoppingCart</title>
</head>
<body>
<h1>Products in shopping cart</h1>
<h2 style="color: blue">${messageEmptyData}</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Operation</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <c:out value="${product.productId}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/deletefromshoppingcart?id=${product.productId}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
