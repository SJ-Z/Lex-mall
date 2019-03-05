<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>lexian-mall bill</title>
    <link rel="stylesheet" href="<c:url value="/user/common/css/base.css"/>">
    <link rel="stylesheet" href="<c:url value="/user/common/css/init.css"/>">
    <link rel="stylesheet" href="<c:url value="/user/works/css/u_footer.css"/>">
    <link rel="stylesheet" href="<c:url value="/user/works/css/bill.css"/>">
</head>

<body>
<div class="container">
    <header>
        <h1>订单详情</h1>
    </header>
    <div class="m-body">
        <!-- 当前订单 -->
        <div class="m-func-area">
            <h2>当前订单</h2>
            <ul class="all-bill  new-bill">
                <c:forEach items="${orderListUnfinished}" var="order">
                    <li class="bill-item">
                        <div class="bill-msg clearfix">
                            <p class="bill-code"><span>取货码：</span> ${order.o_code}</p>
                            <p class="bill-addr">商家地址：<span class="bill-addr-val">${order.o_seller.s_addr}</span> </p>
                            <p class="bill-money">订单金额：${order.o_totalPrice}元</p>
                            <p class="bill-time">下单时间：${order.o_orderTime}</p>
                        </div>

                        <c:forEach items="${order.o_orderItemList}" var="orderitem">
                            <div class=" bill-detail-item clearfix">
                                <p class="bill-detail-name">${orderitem.oi_goods.g_name}</p>
                                <p class="bill-detail-count">数量:${orderitem.oi_count}</p><p class="bill-detail-money">价格:￥${orderitem.oi_subTotal}</p>
                            </div>
                        </c:forEach>
                        <a href="<c:url value="/user/OrderServlet?method=confirmOrder&u_id=${u_id}&o_id=${order.o_id}"/>">
                            确认完成
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <!-- 历史订单 -->
        <div class="m-func-area" style="margin-bottom: 2.4rem;">
            <h2>历史订单</h2>
            <ul class="all-bill old-bill">
                <c:forEach items="${orderListFinished}" var="order">
                    <li class="bill-item">
                        <div class="bill-msg clearfix">
                            <p class="bill-code"><span>取货码：</span> ${order.o_code}</p>
                            <p class="bill-addr">商家地址：<span class="bill-addr-val">${order.o_seller.s_addr}</span></p>
                            <p class="bill-money">订单金额：${order.o_totalPrice}元</p>
                            <p class="bill-time">下单时间：${order.o_orderTime}</p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>


    <!-- 底部菜单栏，可以用于主要页面的跳转 -->
    <footer class="flex nav-footer border ">
            <span>
                <img src="<c:url value="/user/res/icon_user/like.svg"/>">
                <h6>收藏</h6>
            </span>
        <span>
                <img src="<c:url value="/user/res/icon_user/msg.svg"/>">
                <h6>消息</h6>
            </span>
        <span>
                <img src="<c:url value="/user/res/icon_user/home.svg"/>">
                <h6>首页</h6>
            </span>
        <span>
                <img src="<c:url value="/user/res/icon_user/bill.svg"/>">
                <h6>订单</h6>
            </span>
        <span>
                <img src="<c:url value="/user/res/icon_user/cart.svg"/>">
                <h6>购物车</h6>
            </span>
    </footer>
</div>

<script src="<c:url value="/user/common/js/myzepto.js"/>">

</script>
<script type="module" src="<c:url value="/user/works/js/bill.js"/>">
</script>
</body>

</html>