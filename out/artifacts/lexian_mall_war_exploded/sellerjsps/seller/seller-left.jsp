<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>左侧导航栏</title>
	<link rel="stylesheet" href="<c:url value="/common/css/base.css"/>">
    <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/left.css"/>">
	<link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/manager-main.css"/>">
</head>

<body>
    <div class="sidebar clearf">
		<div class="sidebar clearf">
			<ul>
				<li class="tab1">
					<span style="text-align: center">商家管理</span>
					<ul class="tab2">
						<li><img src="<c:url value="/sellerjsps/res/img/position.png"/>" ><a href="<c:url value="/seller/SellerServlet?method=showInfo&s_id=${session_seller.s_id}" />" target="show">商&nbsp;家&nbsp;信&nbsp;息</a>
						</li>
						<li><img src="<c:url value="/sellerjsps/res/img/position.png"/>" ><a href="<c:url value="/seller/SellerServlet?method=modifyPre&s_id=${session_seller.s_id}"/>" target="show">商家信息修改</a>
						</li>
					</ul>
				</li>

				<li class="tab1">
					<span style="text-align: center">商品管理</span>
					<ul class="tab2">
						<li><img src="<c:url value="/sellerjsps/res/img/position.png"/>" >
							<a href="<c:url value="/seller/SellerGoodsServlet?method=findAll&s_id=${session_seller.s_id}"/>" target="show">商品信息浏览</a>
						</li>

						<li><img src="<c:url value="/sellerjsps/res/img/position.png"/>"><a href="<c:url value="/seller/SellerGoodsServlet?method=addGoodsPre"/>" target="show">添&nbsp;加&nbsp;商&nbsp;品</a>
						</li>
						<li><img src="<c:url value="/sellerjsps/res/img/position.png"/>"><a href="<c:url value="/seller/SellerGoodsServlet?method=searchGoodsPre"/>" target="show">搜&nbsp;索&nbsp;商&nbsp;品</a>
						</li>
					</ul>
				</li>
				<li  class="tab1">
					<span style="text-align: center">订单管理</span>
					<ul class="tab2">
						<li><img src="<c:url value="/sellerjsps/res/img/position.png"/>"><a href="<c:url value="seller-order.jsp"/>" target="show">浏&nbsp;览&nbsp;订&nbsp;单</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	<script src="<c:url value="/sellerjsps/works/js/left.js"/>"></script>
</body>
</html>
