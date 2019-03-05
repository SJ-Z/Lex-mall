<%--
  Created by IntelliJ IDEA.
  User: 炸弹人
  Date: 2018/8/22
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>主页</title>
    </head>
    <body>
        <h1>主页</h1>
        <ul>
            <li><a href="<c:url value='/sellerLogin.jsp'/>">商家登录</a></li>
            <li><a href="<c:url value='/sellerRegist.jsp'/>">商家注册</a></li>
            <li><a href="<c:url value='/seller/afterLogin.jsp'/>">点击这里去商家登录后能看到的界面</a></li>
            <li><a href="<c:url value='/adminjsps/admin/index.jsp'/>">超级管理员后台</a></li>
        </ul>
    </body>
</html>
