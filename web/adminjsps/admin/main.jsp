<%--
  Created by IntelliJ IDEA.
  User: 炸弹人
  Date: 2018/8/25
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>超级管理员后台</title>
        <style type="text/css">
            * {
                font-size: 10pt;
            }

            body {
                text-align: center;
                margin: 0px;
            }

            .table {
                width: 100%;
                height: 100%;
                border: 1px solid #6699cc; /*固定边框,1像素*/
                border-collapse: collapse; /*单线的列表边框*/
            }

            .table td {
                border: 1px solid #6699cc; /*固定边框,1像素*/
            }

            iframe {
                width: 100%;
                height: 100%;
            }
        </style>
    </head>
    <body>
        <table class="table" align="center">
            <tr style=" height: 100px; ">
                <td colspan="2" align="center">
                    <iframe frameborder="0" src="<c:url value='/adminjsps/admin/top.jsp'/>" name="top"></iframe>
                </td>
            </tr>
            <tr>
                <td width="265" style="padding:5px; " align="center" valign="top">
                    <iframe frameborder="0" width="120" src="<c:url value='/adminjsps/admin/left.jsp'/>"
                            name="left"></iframe>
                </td>
                <td>
                    <iframe frameborder="0" src="<c:url value='/adminjsps/admin/body.jsp'/>" name="body"></iframe>
                </td>
            </tr>
        </table>
    </body>
</html>
