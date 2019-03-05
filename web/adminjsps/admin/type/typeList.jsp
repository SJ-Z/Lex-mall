<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
    <head>
        <title>一级分类列表</title>

        <style type="text/css">
            table {
                font-family: 宋体;
                font-size: 14pt;
                width: 70%;
                text-align: center;
            }
            a:link {
                font-size: 16px;
                color: #00aeff;
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
        </style>
    </head>

    <body>
        <a href="javascript:history.go(-1);">&lt;&lt;返回</a>
        <h2 style="text-align: center;">一级分类列表</h2>
        <h3 style="width: 100%; text-align: center; font-size: 16px; color: firebrick">${msg}</h3>
        <table align="center" border="1" cellpadding="0" cellspacing="0">
            <tr id="th">
                <th>一级分类名称</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${typeList}" var="type">
                <tr>
                    <td>${type.t_name}</td>
                    <td>
                        <a href="<c:url value="/admin/AdminTypeServlet?method=findAllSubType&t_id=${type.t_id}"/>">查看所有二级分类</a> |
                        <a href="<c:url value="/admin/AdminTypeServlet?method=modifyTypePre&t_id=${type.t_id}"/>">修改</a> |
                        <a href="<c:url value="/admin/AdminTypeServlet?method=deleteType&t_id=${type.t_id}"/>"
                           onclick="return confirm('您真要删除${type.t_name}分类吗？')">删除</a>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </body>
</html>
