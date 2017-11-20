<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 19.11.2017
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>User Management/Edit</title>
    <style>
        input[type="button"]{
            width:20px;
        }
    </style>
</head>
<body>
<jsp:useBean id="user" class="ua.nure.usermanagement.User" scope="session"/>
<form method="post" action="${requestScope.contextPath}/edit">
    <fmt:formatDate value='${user.dateOfBirth}' type='date' dateStyle='medium' var="dateStr"/>
    <input type="hidden" name="id" value="${user.id}">
    <table>
        <tr>
            <th>First name</th>
            <td><input type="text" name="firstName" value="${user.firstName}"></td>
        </tr>
        <tr>
            <th>Last name</th>
            <td><input type="text" name="lastName" value="${user.lastName}"></td>
        </tr>
        <tr>
            <th>Date of birth</th>
            <td><input type="text" name="dateOfBirth" value="${dateStr}"></td>
        </tr>
    </table>
    <input type="submit" name="ok" value="Ok">
    <input type="submit" name="cancel" value="Cancel">
</form>
<c:if test="${requestScope.error != null}">
    <script>
        alert("${requestScope.error}")
    </script>
</c:if>
</body>
</html>
