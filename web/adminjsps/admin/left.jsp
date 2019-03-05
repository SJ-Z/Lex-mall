<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
    <head>
        <title>菜单</title>
        <script type="text/javascript" src="<c:url value='/menu/mymenu.js'/>"></script>
        <link rel="stylesheet" href="<c:url value='/menu/mymenu.css'/>" type="text/css" media="all">
        <script language="javascript">
            var bar1 = new Q6MenuBar("bar1", "Lex商城管理");

            function load() {
                /*
	            设置配色方案！
                配色方案一共4种：0、1、2、3
                */
                bar1.colorStyle = 0;
                /*
                指定图片目录
                */
                bar1.config.imgDir = "<c:url value='/menu/img/'/>";
                /*
                菜单之间是否相互排斥
                */
                bar1.config.radioButton = false;
                /*
                分类管理：指定要添加的菜单名称（如果这个名称的菜单已经存在，不会重复添加）
                查看分类：指定要添加的菜单项名称
                "value='/admin/AdminTypeServlet?method=findAllType'：指定菜单项时要请求的地址
                body：结果的显示框架页名称
                */
                bar1.add("商家管理", "审核注册申请", "<c:url value="/admin/AdminServlet?method=checkRegistPre"/>", "body");
                bar1.add("商家管理", "停用商家", "<c:url value="/admin/AdminServlet?method=showOpenSeller"/>", "body");

                bar1.add("分类管理", "查看分类", "<c:url value="/admin/AdminTypeServlet?method=findAllType"/>", "body");
                bar1.add("分类管理", "添加一级分类", "<c:url value="/adminjsps/admin/type/addType.jsp"/>", "body");
                bar1.add("分类管理", "添加二级分类", "<c:url value="/admin/AdminTypeServlet?method=addSubTypePre"/>", "body");

                bar1.add("商品管理", "全部商品", "<c:url value="/admin/AdminServlet?method=findAllGoods"/>", "body");
                bar1.add("商品管理", "销售中商品", "<c:url value="/admin/AdminServlet?method=findGoodsByDel&del=0"/>", "body");
                bar1.add("商品管理", "已下架商品", "<c:url value="/admin/AdminServlet?method=findGoodsByDel&del=1"/>", "body");
                bar1.add("商品管理", "搜索商品", "<c:url value="/adminjsps/admin/goods/searchGoods.jsp"/>", "body");

                bar1.add("订单管理", "所有订单", "<c:url value='/admin/AdminServlet?method=findAllOrder'/>", "body");
                bar1.add("订单管理", "交易中订单", "<c:url value='/admin/AdminServlet?method=findOrderByState&o_state=1'/>", "body");
                bar1.add("订单管理", "已完成订单", "<c:url value='/admin/AdminServlet?method=findOrderByState&o_state=2'/>", "body");

                // 获取div元素
                var d = document.getElementById("menu");
                // 把菜单对象转换成字符串，赋给<div>元素做内容
                d.innerHTML = bar1.toString();
            }
        </script>

    </head>

    <body onload="load()" style="margin: 0px;">
        <div id="menu"></div>

    </body>
</html>
