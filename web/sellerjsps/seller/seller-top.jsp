<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="<c:url value="/common/css/base.css"/>">
    <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/top.css"/>">
</head>
<body>
    
    <img src="<c:url value="/sellerjsps/res/img/main-banner.png"/>" alt="">
    <p style="margin-right: 20px;margin-top: 10px;">欢迎您 | <label id="username">${session_seller.s_name}</label></p>
    <br/><br/>
    <div style="float: right; margin-right: 20px">
        <a id="exit" href="<c:url value="/seller/SellerServlet?method=quit"/>" target="_top">[退出]</a>
    </div>

</body>
</html>