<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 14-8-6
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录成功</title>
</head>
<body>
登录成功:
<c:forEach items="${message}" var="item">
    ${item.name}，你好<br>
    企业id：${item.enterpriseId},密码：${item.pwd}
</c:forEach>

</body>
</html>
