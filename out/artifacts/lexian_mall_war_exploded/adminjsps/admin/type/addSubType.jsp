<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
    <head>
        <title>添加二级分类</title>

        <style type="text/css">
            body{
                font-size: 20px;
            }

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
            #button{
                outline: none;
                background-color: #6699cc;
                border: none;
                border-radius: 3px;
                color: #ffffff;
                height: 30px;
                font-size: 20px;
                width: 150px;
                margin-left:30px;
            }
            #button:hover{
                background-color: rgb(106,193,233);
            }
            *{
                vertical-align: middle;
            }
        </style>

    </head>

    <body>
        <a href="javascript:history.go(-1);">&lt;&lt;返回</a>
        <h2>添加二级分类</h2>
        <p style="font-size: 16px; color: firebrick">${msg }</p>
        <form action="<c:url value="/admin/AdminTypeServlet"/>" method="post">
            <input type="hidden" name="method" value="addSubType"/>
            <c:set var="select_id" value="${form.sub_type.t_id}" scope="request"/>
            所属一级分类：
            <select name="sub_type" id="sub_type" style="width: 150px; height: 30px;">
                <option>===选择一级分类===</option>
                <c:forEach items="${typeList}" var="type">
                    <option value="${type.t_id}"
                            <c:if test="${type.t_id==select_id}">selected</c:if>>${type.t_name}</option>
                </c:forEach>
            </select>
            二级分类名称：<input style="width: 150px; height: 25px;" type="text" name="sub_name"
                          value="${form.sub_name}"/>
            <input type="submit" value="添加二级分类" id="button"/>
        </form>
    </body>
</html>
