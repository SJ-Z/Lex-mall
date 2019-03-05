<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>top</title>
    <link rel="stylesheet" href="<c:url value="/adminjsps/css/top.css"/>">
</head>

<body>
<img src="<c:url value="/adminjsps/img/top-banner.png"/>" alt="">
<div>
    <c:choose>
        <c:when test="${empty sessionScope.session_admin}">
            <a href="<c:url value="/adminjsps/login.jsp"/>" target="body" style="display: block;margin-left: 20px;">登录</a>
        </c:when>
        <c:otherwise>
            您好：${sessionScope.session_admin.a_name}&nbsp;&nbsp;|&nbsp;&nbsp;
            <a href="<c:url value="/admin/AdminServlet?method=quit"/>" target="_parent">退出</a><br/>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
