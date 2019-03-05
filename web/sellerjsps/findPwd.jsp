<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>找回密码</title>
        <link rel="stylesheet" href="<c:url value="/common/css/base.css"/>">
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/findPwd.css"/>">
        <link rel="shortcut icon" href="<c:url value="/sellerjsps/res/img/favicon.ico"/>">

    </head>
    <body>
        <p style="font-size: 18px; color: firebrick">${msg }</p>
        <div id="div1">
            <img src="<c:url value="/sellerjsps/res/img/main-banner.png"/>" alt="">
        </div>
        <form action="<c:url value="/SellerBeforeLoginServlet"/>" method="post">
            <input type="hidden" name="method" value="findPwd"/>
            <div id="div2">
                <a href="javascript:history.go(-1);" style="float: left; margin-left: 200px; font-size: 24px">&lt;&lt;返回</a>
                <br/><br/>
                <label>填写您的注册邮箱：</label><input type="email" name="s_email" value="${s_email}"/>
                <button type="submit">提&nbsp;&nbsp;交</button>
            </div>

        </form>
    </body>
</html>
