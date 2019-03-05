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
        <title>商品</title>
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

            #a_out:hover {
                font-size: 16px;
                color: white;
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <a href="javascript:history.go(-1);">&lt;&lt;返回</a>
        <h1 style="color: mediumslateblue">查询结果</h1>

        <table border="1" width="100%" cellspacing="0" background="black">
            <tr>
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
                            <a id="a_out" href="<c:url value="/admin/AdminServlet?method=outGoods&g_id=${goods.g_id}"/>"
                               onclick="return confirm('确认下架该 \'${goods.g_name}\' 商品？')">下架</a>
                        </c:when>
                        <c:when test="${goods.g_del eq true}">-</c:when>
                    </c:choose>
                </td>
            </tr>
        </table>
        <br/>
    </body>
</html>
