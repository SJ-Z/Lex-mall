<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
    <head>
        <title>搜索商品</title>

        <style type="text/css">
            a:link {
                font-size: 16px;
                color: #6699cc;
                text-decoration: none;
            }
            a:visited {
                font-size: 16px;
                color: #00aeff;
                text-decoration: none;
            }
            a:hover {
                font-size: 16px;
                color: slateblue;
                text-decoration: none;
            }
            *{
                font-size: 20px;
            }
            .button{
                border: none;
                outline: none;
                width: 100px;
                height: 35px;
                background-color: #6699cc;
                color: #ffffff;
                border-radius: 4px;
            }
            .button:hover{
                background-color: rgb(106,193,233);
            }
        </style>

    </head>

    <body>
        <a href="javascript:history.go(-1);">&lt;&lt;返回</a>
        <h2>搜索商品</h2>
        <p style="font-size: 16px; color: firebrick">${msg }</p>
        <form action="<c:url value="/admin/AdminServlet"/>" method="post">
            <input type="hidden" name="method" value="findGoodsById"/>
            &nbsp;商&nbsp;&nbsp;品&nbsp;&nbsp;id：<input type="text" name="g_id" value="${g_id}" style="width: 280px;height: 30px;"/>
            <input type="submit" value="搜索商品" class="button"/>
        </form>
        <br/><hr/><br/>
        <form action="<c:url value="/admin/AdminServlet"/>" method="post">
            <input type="hidden" name="method" value="findGoodsByName"/>
            商品名称：<input type="text" name="g_name" value="${g_name}" style="width: 280px;height: 30px;"/>
            <input type="submit" value="搜索商品"class="button"/>
        </form>
    </body>
</html>
