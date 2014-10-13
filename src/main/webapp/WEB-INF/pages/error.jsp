<%--
  Created by IntelliJ IDEA.
  @author: 罗尧  Email:johnny_lx@yahoo.com
  @since 14-8-9 20:40  
  @version 1.0
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>错误页面</title>
</head>
<body>
<h1>出错了</h1>
<%
    Exception e = (Exception)request.getAttribute("exception");
    out.print(e.getMessage());
%>
</body>
</html>