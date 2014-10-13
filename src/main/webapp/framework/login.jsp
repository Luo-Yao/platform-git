<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 14-7-13
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>托管型呼叫中心座席登录平台 - 天润融通</title>
</head>
<body>
<img src="images/logo.jpg"/>

<br><form action="../p/user/login" method="post">
    <table align="center">

        <tr>
            <td>user:<input name="name" type="text"/></td>
        </tr>

        <tr>
            <td>pwd:<input name="pwd" type="password"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="login"/><input type="reset" value="reset"/></td>
        </tr>
    </table>

</form>
</body>
</html>
