<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>重设密码</title>
        <link rel="stylesheet" href="<c:url value="/common/css/base.css"/>">
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/resetPwd.css"/>">
        <link rel="shortcut icon" href="<c:url value="/sellerjsps/res/img/favicon.ico"/>">

    </head>
    <body>
        <p style="font-size: 18px; color: firebrick">${msg }</p>
        <img src="<c:url value="/sellerjsps/res/img/banner1.png"/>" alt="" id="pic1">
        <img src="<c:url value="/sellerjsps/res/img/lexian-ad.png"/>" alt="" id="pic2">
        <div class="resetPwd">
            <label id="top">重设密码&nbsp;</label>
            <form action="<c:url value="/SellerBeforeLoginServlet"/>" method="post">
                <input type="hidden" name="method" value="resetPwd"/>
                <input type="hidden" name="s_id" value="${s_id}"/>
                <div class="password">
                    <label>&nbsp;新&nbsp;密&nbsp;码：</label><input type="password" name="s_pwd"/>
                </div>
               <div class="password">
                   <label>再次输入：</label><input type="password" name="_pwd"/>
               </div>
                <button type="submit">提交修改</button>
            </form>
        </div>

    </body>
</html>