----------platform新框架目录结构 说明文档----------------

基于Maven，SpringMVC＋MyBatis＋PostgreSQL＋Redis+Velocity(or FreeMarker)+JSTL，分布式的Web架构

doc   设计文档、初始化sql、pdm数据模型

config  系统配置资源文件

com.framework   框架基础模块，不准随便改动

com.tinet.ccic.c3.app  这个为业务模块目录，下面包含各个业务目录子目录，开发人员在此目录下操作

当新增一个子业务后，需要在config/mybatis/mapper中配置你对应dao中的sql映射，具体见platform-User.xml

各个子业务下面的目录结构：(java)
    dao             mapper的目录
    pojo            数据库映射的bean
    servcie         业务处理层
    controller      控制层，相当于struts2的action
    util            工具类，当基础框架中没有包含自己想要的工具类时，可以在此目录扩展
    page            返回页面对象
    webservice      对外开放接口，json和xml的格式

各个子业务下面的目录结构：(前端)
webapp
    framework
        images              图片
        stylesheet          css样式文件
        scripts             Javascript文件
        login.jsp
    WEB-INF
        page                受限资源文件，jsp文件，各个子业务模块目录的jsp文件
        web.xml             配置文件
    index.jsp/html/htm/     首页


完整的例子，包含各个目录结构和前台页面的资源文件，见：com.tinet.ccic.c3.app.demo

webapp  为web页面目录，包含jsp、html、css、javascript等






----------platform新框架关键技术点 说明文档----------------

1.spring mvc

2.spring bean注入

3.spring 事物控制 @Transactional

4.spring 拦截器

5.过滤器

6.mybatis的使用，CURD基本操作、多表查询、组合查询、条件查询，读写分离结构

7.redis缓存框架

8.Tomcat sesion共享

9.jsp前端处理，JSTL标签、Velocity模版  (jQuery,AngularJS)

10.URLRewrite安全性过滤，前端静态化

11.REST风格的相关配置，web.xml-default

12.Spring全局Exception、404、500页面配置

13.Spring 转发与重定向

    return "forward:/order/add";
    return "redirect:/index.jsp";
    为了防止用户刷新浏览器(F5)导致表单重复提交，一般在保存或修改操作之后会redirect到一个结果页面（不是forward），
    同时携带参数，如操作成功的提示信息。因为是Redirect，Request里的attribute不会传递过去。
    Spring在3.1才提供了这个能力--RedirectAttributes。 反复按F5，操作成功的提示信息也不会再次出来（总共只出现一次），效果很理想。



----------platform新框架-log日志级别和编码注意事项 说明文档----------------
  <!--
    从小到大OFF、FATAL、ERROR、WARN、INFO、DEBUG、ALL
    如果root设置为info，则debug级别的日志信息将不被打印出来
    -->

------------------------------------开发环境------------------------------------------------------
在开发过程中，root的日志级别设置为debug

在开发过程中，一般情况下java代码中的log日志级别应为debug，如下：
        logger.debug("PlatformConstants.apacheTomcatPath============"+PlatformConstants.apacheTomcatPath);

在开发过程中，如果对于框架和各个技术点很熟悉的情况下，想不打印某些引入的包的日志，可以做如下设置：
        <logger name="net.sf.json.JSONObject" level="ERROR"/>
        <logger name="org.springframework" level="ERROR"/>


-------------------------------生产环境------------------------------------------------------
在生产环境中，root的日志级别设置为info

在生产环境中，如果要强制打印java代码中的某些信息，可以编码为info：
        logger.debug("PlatformConstants.apacheTomcatPath============"+PlatformConstants.apacheTomcatPath);

