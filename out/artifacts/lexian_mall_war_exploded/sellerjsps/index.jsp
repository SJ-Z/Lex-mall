<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>商家登录</title>
        <link rel="stylesheet" href="<c:url value="/common/css/base.css"/>">
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/index.css"/>">
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/manager-main.css"/>">
        <link rel="shortcut icon" href="<c:url value="/sellerjsps/res/img/favicon.ico"/>">

        <script type="text/javascript">
            function _change() {
                var ele = document.getElementById("img_verifyCode");
                ele.src = "<c:url value="/VerifyCodeServlet" />?xxx=" + new Date().getTime();
            }
        </script>
    </head>
    <body>
        <img id="logo" src="<c:url value="/sellerjsps/res/img/1535620552_377615.png"/>" alt="">
        <img id="ad" src="<c:url value="/sellerjsps/res/img/lexian-ad.png"/>" alt="">
        <div class="log_on fr">
            <div>
                <c:choose>
                    <c:when test="${msg != null}"><p style="text-align: center;height: 20px; width: 150px; margin-top: 5px;
                margin-left: 28%; border-radius: 4px; color: #ffffff; box-shadow: 0 8px 16px rgba(235, 125, 117, 0.5);
                background-color: rgb(235, 125, 117);">${msg }</p></c:when>
                    <c:otherwise><p>&nbsp;&nbsp;&nbsp;</p></c:otherwise>
                </c:choose>
            </div>
            <div class="top">
                <label id="logon-c">登录 </label> <label id="logon-e">&nbsp;login</label>
            </div>
            <form action="<c:url value='/SellerBeforeLoginServlet'/>" method="post">
                <input type="hidden" name="method" value="login"/>
                <div class="username" id="username-b">
                    <img src="<c:url value="/sellerjsps/res/img/name.png"/>" alt="">
                    <c:choose>
                        <c:when test="${s_name != null}">
                            <input value="${s_name}" type="text" id="username" name="s_name" tabindex="1"
                                   autocomplete="off">
                        </c:when>
                        <c:when test="${form.s_name != null}">
                            <input value="${form.s_name}" type="text" id="username" name="s_name" tabindex="1"
                                   autocomplete="off">
                        </c:when>
                        <c:otherwise>
                            <input value="用户名" type="text" id="username" name="s_name" tabindex="1" autocomplete="off">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="password" id="password-b">
                    <img src="<c:url value="/sellerjsps/res/img/newPwd.png"/>" alt="">
                    <c:choose>
                        <c:when test="${s_pwd != null}">
                            <input value="${s_pwd}" type="password" id="password" name="s_pwd" tabindex="2" autocomplete="off"/>
                        </c:when>
                        <c:when test="${form.s_pwd != null}">
                            <input value="${form.s_pwd}" type="password" id="password" name="s_pwd" tabindex="2" autocomplete="off"/>
                        </c:when>
                        <c:otherwise>
                            <input value="密码" type="input" id="password" name="s_pwd" tabindex="2" autocomplete="off"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="vertification">
                    <input value="验证码" type="text" id="vertification" name="s_verifyCode" size="6px" tabindex="3"
                           autocomplete="off" class="fl">
                    <a href="javascript:_change()">
                        <img id="img_verifyCode" src="<c:url value="/VerifyCodeServlet" />" alt="验证码" border="1"/>
                    </a>
                </div>
                <label id="remember-l">
                    <c:choose>
                        <c:when test="${s_pwd != null}">
                            <input type="checkbox" id="remember" name="remember" checked="checked"/>记住密码
                        </c:when>
                        <c:when test="${remember eq 'true'}">
                            <input type="checkbox" id="remember" name="remember" checked="checked"/>记住密码
                        </c:when>
                        <c:otherwise><input type="checkbox" id="remember" name="remember"/>记住密码</c:otherwise>
                    </c:choose>
                </label>
                <div class="submit">
                    <button id="submit">&nbsp;登&nbsp;录&nbsp;</button>
                    <button id="reset" type="reset">&nbsp;重&nbsp;置&nbsp;</button>
                </div>
            </form>
            <div class="bottom fr" style="padding-right: 5px">
                <a href="<c:url value="/sellerjsps/findPwd.jsp"/>">找回密码</a>
                <a href="<c:url value="/sellerjsps/seller-register.jsp"/>">免费注册</a>
            </div>
        </div>
        <script type="text/javascript" src="<c:url value="/sellerjsps/works/js/index.js"/>"></script>

    </body>
</html>