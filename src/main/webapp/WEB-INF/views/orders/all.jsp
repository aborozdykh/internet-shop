<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders</title>
</head>
<body>
<table border="1">
    <tr>
        <th>Order ID</th>
        <th>User</th>
        <th>Operation</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.orderId}"/>
            </td>
            <td>
                <c:out value="${order.user.name}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/orders/addtoshoppingcart?id=${order.orderId}">Edit</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
