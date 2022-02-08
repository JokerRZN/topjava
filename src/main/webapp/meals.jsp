<%--
  Created by IntelliJ IDEA.
  User: i.folomkin
  Date: 07.02.2022
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h3><a href="index.html">HOME</a></h3>

<hr>
<h2>Meals</h2>
    <table border="1" cellpadding="5">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
            <fmt:formatDate value="${parsedDateTime}" var="dateTime" pattern="yyyy-MM-dd hh:mm" />
            <tr style="color:${meal.excess ? 'green' : 'red'}">
                <td><c:out value="${dateTime}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><button>Update</button></td>
                <td><button>Delete</button></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
