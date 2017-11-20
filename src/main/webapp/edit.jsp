<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 19.11.2017
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="user" class="ua.nure.usermanagement.User" scope="session">
    <html>
    <head>
        <title>User Management/Edit</title>
    </head>
    <body>
    <form method="post" action="${requestScope.contextPath}/edit">
        <input type="hidden" name="id" value="${user.id}">
        <input type="text" name="firstName" value="${user.firstName}">
        <input type="text" name="lastName" value="${user.lastName}">
        <input type="text" value="<fmt:formatDate value="$(user.dateOfBirth)" type="date" dateStyle="medium"/>">
        <input type="submit" name="ok" value="Ok">
    </form>
    <c:if test="${requestScope.error != null}">
        <script>
            alert("${requestScope.error}")
        </script>
    </c:if>
    </body>
    </html>
</jsp:useBean>