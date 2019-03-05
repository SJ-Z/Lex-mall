<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
    <head>
        <title>二级分类列表</title>

        <style type="text/css">
            table {
                font-family: 宋体;
                font-size: 14pt;
                border-color: rgb(78, 78, 78);
                width: 60%;
                text-align: center;
            }

            #th {
                background: #3992d0;
            }
            a:link {
                font-size: 16px;
                color: #00a1ff;
                text-decoration: none;
            }
            a:visited {
                font-size: 16px;
                color: #00a3ff;
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
        <h2 style="text-align: center;">二级分类列表</h2>
        <h3 style="width: 100%; text-align: center; font-size: 16px; color: firebrick">${msg}</h3>
        <table align="center" border="1" cellpadding="0" cellspacing="0">
            <tr id="th" bordercolor="rgb(78,78,78)">
                <th>二级分类名称</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${subTypeList}" var="subType">
                <tr bordercolor="rgb(78,78,78)">
                    <td>${subType.sub_name}</td>
                    <td>
                        <a href="<c:url value="/admin/AdminTypeServlet?method=modifySubTypePre&sub_id=${subType.sub_id}"/>">修改</a> |
                        <a href="<c:url value="/admin/AdminTypeServlet?method=deleteSubType&sub_id=${subType.sub_id}"/>"
                           onclick="return confirm('您真要删除${subType.sub_name}分类吗？')">删除</a>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </body>
</html>
