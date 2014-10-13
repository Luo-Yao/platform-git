<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 14-7-13
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>


<%--
出于安全性考虑，webroot根目录下面有且仅有一个jsp，即当前index.jsp
所有这个界面永远不会被访问到，当请求到这个界面会Redirect--->framework/login.jsp

--%>
<script type="text/javascript">
    window.location="/framework/login.jsp";
</script>
