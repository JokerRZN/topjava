<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">HOME</a></h3>

<hr>
<h2>Meals</h2>
    <table border="1" padding="5">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <c:forEach items="${meals}" var="meal">
            <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
            <fmt:formatDate value="${parsedDateTime}" var="dateTime" pattern="yyyy-MM-dd HH:mm" />
            <tr style="color:${meal.excess ? 'red' : 'green'}">
                <td>${dateTime}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><button>Update</button></td>
                <td><button>Delete</button></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
