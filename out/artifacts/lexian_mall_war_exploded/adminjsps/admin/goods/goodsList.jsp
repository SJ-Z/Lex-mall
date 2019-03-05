<%--
  Created by IntelliJ IDEA.
  User: 炸弹人
  Date: 2018/8/30
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>商品列表</title>
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
        </style>
    </head>
    <body>
        <a href="javascript:history.go(-1);">&lt;&lt;返回</a>
        <h1 style="text-align: center;font-size: 30px;color: #6699cc">
            <c:choose>
                <c:when test="${del eq false}">销售中商品</c:when>
                <c:when test="${del eq true}">已下架商品</c:when>
                <c:otherwise>全部商品</c:otherwise>
            </c:choose>
        </h1>

        <table border="1" width="100%" cellspacing="0">
            <tr bgcolor="#3992d0" style="color: white;">
                <th>商品编号</th>
                <th>商品名称</th>
                <th>商品库存</th>
                <th>商品原价</th>
                <th>促销价格</th>
                <th>商品图片</th>
                <th>商品类别</th>
                <th>商家店名</th>
                <th>商家电话</th>
                <th>商品状态</th>
                <th>收藏人数</th>
                <th>操作</th>
                </td>
            </tr>
            <%--
                要遍历的是PageBean的beanList集合
            --%>
            <c:forEach items="${requestScope.pageBean.beanList}" var="goods">
                <tr align="center">
                    <td>${goods.g_id}</td>
                    <td>${goods.g_name}</td>
                    <td>${goods.g_count}</td>
                    <td>${goods.g_price}</td>
                    <td>
                        <c:choose>
                            <c:when test="${goods.g_discount != null}">${goods.g_discount}</c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td width="10%">
                        <div><img src="<c:url value="${goods.g_image}"/>" height="60"/></div>
                    </td>
                    <td>${goods.g_type.t_name} : ${goods.g_subType.sub_name}</td>
                    <td>${goods.g_seller.s_storeName}</td>
                    <td>${goods.g_seller.s_phone}</td>
                    <td>
                        <c:choose>
                            <c:when test="${goods.g_del eq false}">销售中</c:when>
                            <c:when test="${goods.g_del eq true}">已下架</c:when>
                        </c:choose>
                    </td>
                    <td>${goods.g_likeCount}</td>
                    <td>
                        <c:choose>
                            <c:when test="${goods.g_del eq false}">
                                <a href="<c:url value="/admin/AdminServlet?method=outGoods&g_id=${goods.g_id}"/>"
                                   onclick="return confirm('确认下架该 \'${goods.g_name}\' 商品？')">下架</a>
                            </c:when>
                            <c:when test="${goods.g_del eq true}">-</c:when>
                        </c:choose>
                    </td>
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
