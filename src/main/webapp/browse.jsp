<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 19.11.2017
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>User management/Browse</title></head>
<body>
<form action="${requestScope.contextPath}/browse" method="post"><!--<%=request.getContextPath()%>-->
<table id="userTable" border="1">
    <tr>
        <th></th>
        <th>First name</th>
        <th>Last name</th>
        <th>Date of birth</th>
    </tr>
    <c:forEach var="user" items="${sessionScope.users}">
        <tr>
            <td><input type="radio" name="id" id="id" value="${user.id}"></td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.dateOfBirth}</td>
        </tr>
    </c:forEach>
</table>
    <input type="submit" name="add" value="Add">
    <br>
    <input type="submit" name="edit" value="Edit">
    <br>
    <input type="submit" name="delete" value="Delete">
    <br>
    <input type="submit" name="details" value="Details">
    <br>
</form>
<c:if test="${requestScope.error != null}">
    <script>
        alert("${requestScope.error}")
    </script>
</c:if>
</body>
</html>