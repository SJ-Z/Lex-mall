<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>修改二级分类</title>

<style type="text/css">
    a:link {
        font-size: 16px;
        color: #00a1ff;
        text-decoration: none;
    }
    a:visited {
        font-size: 16px;
        color: #00a1ff;
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
    <h2>修改二级分类</h2>
    <form action="<c:url value="/admin/AdminTypeServlet"/>" method="post">
    	<input type="hidden" name="method" value="modifySubType" />
    	<input type="hidden" name="sub_id" value="${subType.sub_id}" />
    	分类名称：<input type="text" name="sub_name" value="${subType.sub_name}"/>
    	<input type="submit" value="修改分类"/>
    </form>
  </body>
</html>
