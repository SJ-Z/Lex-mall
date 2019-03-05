<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
    <head>
        <title>Lex管理员登录页面</title>
        <link rel="stylesheet" href="<c:url value="/common/css/base.css"/>">
        <style>
            input{
                height: 30px;
                margin: 10px;
                outline: none;
                background-color: #ffffff;
                border: 1px solid gray;
                border-radius: 3px;
            }
            *{
                font-size: 18px;
            }
            #button{
                width: 80px;
                margin-left: 100px;
                background-color: #6699cc;
                border: none;
                color: #fff;
            }
            #button:hover{
                background-color: rgb(106,193,233);
            }


        </style>

        <script type="text/javascript">
            function _change() {
                var ele = document.getElementById("img_verifyCode");
                ele.src = "<c:url value="/VerifyCodeServlet" />?xxx=" + new Date().getTime();
            }
        </script>

    </head>

    <body>
        <h1>Lex管理员登录页面</h1>
        <hr/>
        <p style="font-size: 18px; color: firebrick">${msg }</p>
        <form action="<c:url value='/AdminLoginServlet'/>" method="post" target="_top">
            <input type="hidden" name="method" value="login"/>
            管理员账户：<input type="text" name="a_name" value="${form.a_name}"/><br/>
            密　　　码：<input type="password" name="a_pwd"/><br/>
            验&nbsp;&nbsp;&nbsp;&nbsp;证&nbsp;&nbsp;&nbsp;码：<input type="text" size="3" name="a_verifyCode" style="vertical-align: middle; width: 80px;height: 30px;"/>
            <img id="img_verifyCode" src="<c:url value="/VerifyCodeServlet" />" border="1" style="vertical-align: middle;"/>
            <a href="javascript:_change()" style="vertical-align: middle;">看不清，换一张</a><br/>
            <input type="submit" value="进入后台" id="button"/>
        </form>
    </body>
</html>
