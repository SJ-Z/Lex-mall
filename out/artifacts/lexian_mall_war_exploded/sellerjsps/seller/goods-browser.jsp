<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>浏览商品信息</title>
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/manager-main.css"/>">
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/goods-browser.css"/>">
        <link rel="stylesheet" href="<c:url value="/common/css/base.css"/>">
    </head>
    <body>
        <a href="javascript:history.go(-1);" style="float: left; margin-left: 20px">&lt;&lt;返回</a><br/>
        <c:choose>
            <c:when test="${requestScope.pageBean.beanList eq null}">
                <br/><br/>
                <label style="text-align: center; display: block">
                    <img src="<c:url value="/sellerjsps/res/img/404page2.svg"/>" style="width: 250px; margin-right: 100px">
                </label>
                <p style="font-size: 20px; color: #6699cc; width: 95%; text-align: center">
                    走丢了。。未搜索到该商品的信息</p>
            </c:when>
            <c:otherwise>
                <div>
                    <c:forEach items="${requestScope.pageBean.beanList}" var="goods">
                        <div class="goods clearf" style="float: left;">
                            <form>
                                <div class="top fl" style="font-size: 10px; padding-top: 5px">
                                    <label style="width: 10%">ID:</label><label id="goods-id">${goods.g_id}</label>
                                </div>
                                <div class="goods-photo fl">
                                    <img src="<c:url value="${goods.g_image}"/>" alt="" id=photo width="80px"
                                         height="80px" style="margin-top: 25px;">
                                </div>
                                <div class="goods-name fl">
                                    <label for="name">名称：</label><input id="name" value="${goods.g_name}" type="text"
                                                                        disabled="disabled">
                                </div>
                                <div class="goods-category fl">
                                    <label for="category">种类：</label><input id="category"
                                                                            value="${goods.g_type.t_name} ：${goods.g_subType.sub_name}"
                                                                            type="text" disabled="disabled">
                                </div>
                                <div class="goods-price fl">
                                    <label for="price">价格：</label><input id="price" value="${goods.g_price}" type="text"
                                                                         disabled="disabled">
                                </div>
                                <div class="goods-sales fl">
                                    <label for="sales">促销：</label>
                                    <c:choose>
                                        <c:when test="${goods.g_discount eq 0.0}">
                                            <input id="sales" value="-" type="text" disabled="disabled">
                                        </c:when>
                                        <c:otherwise>
                                            <input id="sales" value="${goods.g_discount}" type="text"
                                                   disabled="disabled">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="goods-store fl">
                                    <label for="store">货存：</label><input id="store" value="${goods.g_count}" type="text"
                                                                         disabled="disabled">
                                </div>
                                <div class="goods-description fl">
                                    <label for="description">描述：</label><textarea id="description" cols="20" rows="2"
                                                                                  disabled="disabled"
                                                                                  maxlength="200"
                                                                                  onchange="this.value=this.value.substring(0, 200)"
                                                                                  onkeydown="this.value=this.value.substring(0, 200)"
                                                                                  onkeyup="this.value=this.value.substring(0, 200)"
                                >${goods.g_desc}</textarea>
                                </div>
                                <div class="bottom fl">
                                    <a href="<c:url value="/seller/SellerGoodsServlet?method=outGoods&g_id=${goods.g_id}&s_id=${session_seller.s_id}"/>"
                                       onclick="return confirm('确认下架该 \'${goods.g_name}\' 商品？')">
                                        <img src="<c:url value="/sellerjsps/res/img/delete.png"/>" alt="删除" id="delete"
                                             class="fr"></a>
                                    <a href="<c:url value="/seller/SellerGoodsServlet?method=modifyGoodsPre&g_id=${goods.g_id}"/>">
                                        <img src="<c:url value="/sellerjsps/res/img/edit.png"/>" alt="编辑" id="edit"
                                             class="fr"></a>
                                </div>
                            </form>
                        </div>
                    </c:forEach>
                </div>

                <%--
                    给出分页相关的链接
                --%>
                <div style="width: 90%; text-align: center; clear: both">
                    <br/>
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
            </c:otherwise>
        </c:choose>
    <br/><br/>
    </body>
</html>
