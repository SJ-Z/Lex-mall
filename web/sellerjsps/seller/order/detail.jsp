<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>订单详情</title>
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/manager-order.css"/>">
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/manager-main.css"/>">
    </head>
    <body style="overflow-x: hidden">
        <a href="javascript:history.go(-1);" style="float: left;">&lt;&lt;返回</a><br/>
        <div style="width: 95%">
            <table class="details" border="0">
                <tr>
                    <td colspan="5">
                        订单编号：${order.o_id} &nbsp;&nbsp;
                        成交时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.o_orderTime}"/> &nbsp;&nbsp;
                        金额：<b>${order.o_totalPrice}</b> &nbsp;&nbsp;
                        订单状态：
                        <c:choose>
                            <c:when test="${order.o_state eq 1}">交易中</c:when>
                            <c:when test="${order.o_state eq 2}">已完成</c:when>
                        </c:choose> &nbsp;&nbsp;
                        买家：${order.o_user.u_name} &nbsp;&nbsp;
                    </td>
                </tr>
                <tr>
                    <th class="sequence">商品图片</th>
                    <th class="name ">商品名</th>
                    <th class="unit">单价</th>
                    <th class="number">数量</th>
                    <th class="subtotal">小计</th>
                </tr>
                <c:forEach items="${order.o_orderItemList}" var="orderItem">
                    <tr>
                        <td width="15%">
                            <div><img src="<c:url value="${orderItem.oi_goods.g_image}"/>" height="75"/></div>
                        </td>
                        <td>${orderItem.oi_goods.g_name}</td>
                        <td>${orderItem.oi_singlePrice}元</td>
                        <td>${orderItem.oi_count}</td>
                        <td>${orderItem.oi_subTotal}元</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
