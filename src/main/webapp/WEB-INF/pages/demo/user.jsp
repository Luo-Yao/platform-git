<%--
  Created by IntelliJ IDEA.
  User: 罗尧
  Date: 14-8-6
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>111</title>
</head>
<body>
<c:if test="${empty result}" >
        没有查到任何数据
</c:if>

<c:forEach items="${result}" var="item">
    test --::::--${item.id}--${item.name}--${item.address}--
    <fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/><br />

</c:forEach>
</body>
</html>
