<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>订单管理</title>
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/manager-order.css"/>">
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/manager-main.css"/>">
    </head>
    <body style="overflow-x: hidden;border-top: 1px dotted #6699cc;">
        <a href="javascript:history.go(-1);" style="float: left;">&lt;&lt;返回</a><br/>
        <c:choose>
            <c:when test="${msg != null}">
                <label style="text-align: center; display: block; margin-right: 80px">
                    <img src="<c:url value="/sellerjsps/res/img/404page2.svg"/>" style="width: 250px;">
                </label>
                <p style="font-size: 20px; color: #6699cc; width: 95%; text-align: center">
                    出错了。。没有找到该订单的信息</p>
            </c:when>
            <c:when test="${order != null}">
                <table border="1" class="col-9 order" style="float: none;margin: auto">
                    <tr class="tab">
                        <th class="code">订单编号</th>
                        <th class="time">成交时间</th>
                        <th class="amount">金额</th>
                        <th class="customer">买家</th>
                        <th class="status">订单状况</th>
                        <th class="operation">操作</th>
                    </tr>
                    <tr class="tab">
                        <td class="code">${order.o_id}</td>
                        <td class="time">${order.o_orderTime}</td>
                        <td class="amount">${order.o_totalPrice}</td>
                        <td class="customer">${order.o_user.u_name}</td>
                        <td class="status">
                            <c:choose>
                                <c:when test="${order.o_state eq 1}">交易中</c:when>
                                <c:when test="${order.o_state eq 2}">已完成</c:when>
                            </c:choose>
                        </td>
                        <td class="operation">
                            <a href="<c:url value='/seller/SellerOrderServlet?method=viewDetail&o_id=${order.o_id}'/>">查看详情</a>
                        </td>
                    </tr>
            </c:when>
            <c:when test="${pageBean != null}">
                    <table border="1" class="col-9 order" style="float: none;">
                        <tr class="tab">
                            <th class="code">订单编号</th>
                            <th class="time">成交时间</th>
                            <th class="amount">金额</th>
                            <th class="customer">买家</th>
                            <th class="status">订单状况</th>
                            <th class="operation">操作</th>
                        </tr>

                        <%--
                            要遍历的是PageBean的beanList集合
                        --%>
                        <c:forEach items="${requestScope.pageBean.beanList}" var="order">
                            <tr id="brief">
                                <td>${order.o_id}</td>
                                <td>${order.o_orderTime}</td>
                                <td>${order.o_totalPrice}</td>
                                <td>${order.o_user.u_name}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${order.o_state eq 1}">交易中</c:when>
                                        <c:when test="${order.o_state eq 2}">已完成</c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="<c:url value='/seller/SellerOrderServlet?method=viewDetail&o_id=${order.o_id}'/>">查看详情</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <br/>

                    <%--
                        给出分页相关的链接
                    --%>
                    <div style="width: 90%; text-align: center">
                        第${pageBean.pageCode}页/共${pageBean.totalPage}页
                        <a href="${pageBean.url}&pageCode=1">首页</a>
                        <c:choose>
                            <c:when test="${pageBean.pageCode > 1}">
                                <a href="${pageBean.url}&pageCode=${pageBean.pageCode-1}">上一页</a>
                            </c:when>
                            <c:otherwise>
                                <label>上一页</label>
                            </c:otherwise>
                        </c:choose>

                            <%--计算begin、end --%>
                        <c:choose>
                            <%--如果总页数不足10页，那么把所有的页数都显示出来 --%>
                            <c:when test="${pageBean.totalPage <= 10 }">
                                <c:set var="begin" value="1"/>
                                <c:set var="end" value="${pageBean.totalPage}"/>
                            </c:when>
                            <c:otherwise>
                                <%-- 当总页数>10时，通过公式计算出begin和end --%>
                                <c:set var="begin" value="${pageBean.pageCode-5}"/>
                                <c:set var="end" value="${pageBean.pageCode+4}"/>
                                <%-- 头溢出 --%>
                                <c:if test="${begin < 1}">
                                    <c:set var="begin" value="1"/>
                                    <c:set var="end" value="10"/>
                                </c:if>
                                <%-- 尾溢出 --%>
                                <c:if test="${end > pageBean.totalPage}">
                                    <c:set var="end" value="${pageBean.totalPage}"/>
                                    <c:set var="begin" value="${pageBean.totalPage - 9}"/>
                                </c:if>
                            </c:otherwise>
                        </c:choose>

                            <%-- 循环遍历页码列表 --%>
                        <c:forEach var="i" begin="${begin}" end="${end}">
                            <c:choose>
                                <c:when test="${i eq pageBean.pageCode}">
                                    <span style="display:inline-block; width: 30px">[${i}]</span>
                                </c:when>
                                <c:otherwise>
                        <span style="display:inline-block; width: 30px">
                            <a href="${pageBean.url}&pageCode=${i}">
                                [${i}]</a></span>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:choose>
                            <c:when test="${pageBean.pageCode < pageBean.totalPage}">
                                <a href="${pageBean.url}&pageCode=${pageBean.pageCode+1}">下一页</a>
                            </c:when>
                            <c:otherwise>
                                <label>下一页</label>
                            </c:otherwise>
                        </c:choose>
                        <a href="${pageBean.url}&pageCode=${pageBean.totalPage}">尾页</a>
                    </div>
            </c:when>
        </c:choose>
    </body>
</html>