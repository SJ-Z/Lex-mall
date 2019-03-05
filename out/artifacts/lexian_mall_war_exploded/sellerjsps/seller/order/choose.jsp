<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>订单显示选择</title>
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/order-browser.css"/>">
        <link rel="stylesheet" href="<c:url value="/common/css/base.css"/>">
    </head>
    <body style="overflow : hidden;">
        <div class="search">
            <a href="<c:url value="/seller/SellerOrderServlet?method=findAll&s_id=${session_seller.s_id}"/>" target="order_body">
                <input type="button" value="查看所有订单" id="all">
            </a>
            <a href="<c:url value="/seller/SellerOrderServlet?method=findByState&s_id=${session_seller.s_id}&state=1"/>" target="order_body">
                <input type="button" value="查看未完成订单" id="finish">
            </a>
            <a href="<c:url value="/seller/SellerOrderServlet?method=findByState&s_id=${session_seller.s_id}&state=2"/>" target="order_body">
                <input type="button" value="查看已完成订单" id="nofinish">
            </a>
            <form action="<c:url value="/seller/SellerOrderServlet?method=findByOrderTime"/>" method="post" target="order_body" id="orderDiv">
                <input type="hidden" name="s_id" value="${session_seller.s_id}"/>
                <input type="date" id="date" name="orderTime"/>
                <input type="submit" id="byOrder" value="按订单时间查询"/>
            </form>
            <br>
            <form action="<c:url value="/seller/SellerOrderServlet"/>" method="post" target="order_body" style="display: inline;margin-top: 10px;">
                <input type="hidden" name="method" value="findByOrderId"/>
                <input type="hidden" name="s_id" value="${session_seller.s_id}"/>
                <input type="text" id="search" name="o_id"><input type="submit" id="searchbtn" value="搜索"/>
            </form>
        </div>
        <script src="<c:url value="/sellerjsps/works/js/order-browser.js"/>"></script>
    </body>
</html>
