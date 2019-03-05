<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML>
<html>
    <head>
        <title>订单详细</title>

        <style type="text/css">
            * {
                font-size: 11pt;
            }
            li {
                margin: 10px;
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
        <h1>当前订单</h1>

        <table border="1" width="100%" cellspacing="0" background="black">
            <tr>
                <td colspan="6">
                    订单编号：${order.o_id} &nbsp;&nbsp;
                    成交时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.o_orderTime}"/> &nbsp;&nbsp;
                    金额：<font color="red"><b>${order.o_totalPrice}</b></font> &nbsp;&nbsp;
                    订单状态：
                    <c:choose>
                        <c:when test="${order.o_state eq 1}">交易中</c:when>
                        <c:when test="${order.o_state eq 2}">已完成</c:when>
                    </c:choose> &nbsp;&nbsp;
                    买家：${order.o_user.u_name} &nbsp;&nbsp;
                    商家：${order.o_seller.s_storeName} &nbsp;&nbsp;
                    商家电话：${order.o_seller.s_phone}
                </td>
            </tr>

            <c:forEach items="${order.o_orderItemList}" var="orderItem">
                <tr bordercolor="gray" align="center">
                    <td width="15%">
                        <div><img src="<c:url value="${orderItem.oi_goods.g_image}"/>" height="75"/></div>
                    </td>
                    <td>商品名：${orderItem.oi_goods.g_name}</td>
                    <td>单价：${orderItem.oi_singlePrice}元</td>
                    <td>数量：${orderItem.oi_count}</td>
                    <td>小计：${orderItem.oi_subTotal}元</td>
                </tr>
            </c:forEach>

        </table>
    </body>
</html>

