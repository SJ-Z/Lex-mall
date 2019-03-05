<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML>
<html>
    <head>
        <title>订单列表</title>

        <style type="text/css">
            * {
                font-size: 11pt;
            }

            li {
                margin: 10px;
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
            #a_detail:hover {
                font-size: 16px;
                color: slateblue;
                text-decoration: none;
            }
        </style>
    </head>

    <body >
        <a href="javascript:history.go(-1);">&lt;&lt;返回</a>
        <h1 style="color: #6699cc;text-align: center;font-size: 30px">
            <c:choose>
                <c:when test="${state eq '0'}">所有订单</c:when>
                <c:when test="${state eq '1'}">交易中订单</c:when>
                <c:when test="${state eq '2'}">已完成订单</c:when>
            </c:choose></h1>

        <table border="1" width="100%" cellspacing="0">
            <tr>
                <th>订单编号</th>
                <th>下单时间</th>
                <th>金额</th>
                <th>订单状态</th>
                <th>取货码</th>
                <th>用户</th>
                <th>商家</th>
                <th>商家电话</th>
                <th>查看详情</th>
            </tr>
            <%--
                要遍历的是PageBean的beanList集合
            --%>
            <c:forEach items="${requestScope.pageBean.beanList}" var="order">
                <tr align="center">
                    <td>${order.o_id}</td>
                    <td>${order.o_orderTime}</td>
                    <td>${order.o_totalPrice}</td>
                    <td>
                        <c:choose>
                            <c:when test="${order.o_state eq 1}">交易中</c:when>
                            <c:when test="${order.o_state eq 2}">已完成</c:when>
                        </c:choose>
                    </td>
                    <td>${order.o_code}</td>
                    <td>${order.o_user.u_name}</td>
                    <td>${order.o_seller.s_storeName}</td>
                    <td>${order.o_seller.s_phone}</td>
                    <td><a id="a_detail" href="<c:url value='/admin/AdminServlet?method=orderDetail&o_id=${order.o_id}'/>">查看详情</a></td>
                </tr>
            </c:forEach>
        </table>
        <br/>

        <%--
            给出分页相关的链接
        --%>
        <div style="width: 100%; text-align: center">
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
                    <c:set var="begin" value="1" />
                    <c:set var="end" value="${pageBean.totalPage}" />
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
    </body>
</html>
