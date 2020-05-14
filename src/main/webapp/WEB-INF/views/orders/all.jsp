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
        <th>User ID</th>
        <th>Operation</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.orderId}"/>
            </td>
            <td>
                <c:out value="${order.userId}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/orders/show?id=${order.orderId}">Show order</a>
                <a href="${pageContext.request.contextPath}/orders/delete?id=${order.orderId}">Delete order</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
