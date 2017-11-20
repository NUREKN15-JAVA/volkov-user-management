<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 20.11.2017
  Time: 7:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>User Management/Add</title>
</head>
<body>
<form method="post" action="${requestScope.contextPath}/add">
    <input type="text" name="firstName">
    <br>
    <input type="text" name="lastName">
    <br>
    <input type="text" name="dateOfBirth">
    <br>
    <input type="submit" name="ok" value="Ok">
</form>
<c:if test="${requestScope.error != null}">
    <script>
        alert("${requestScope.error}")
    </script>
</c:if>
</body>
</html>
