<%--
  Created by IntelliJ IDEA.
  User: 炸弹人
  Date: 2018/8/29
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>审核商家注册申请</title>

        <style type="text/css">
            table {
                font-family: 宋体;
                font-size: 14pt;
                width: 70%;
                text-align: center;
            }

            a:link {
                font-size: 16px;
                color: #6699cc;
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
        <h2 style="text-align: center;">商家注册申请列表</h2>
        <table align="center" border="1" cellpadding="0" cellspacing="0">
            <tr id="th" bordercolor="rgb(78,78,78)">
                <th colspan="4">商家信息</th>
                <th>操作</th>
            </tr>
            <tr>
                <th>门店名</th>
                <th>门店地址</th>
                <th>商家email</th>
                <th>商家电话</th>
                <th>-</th>
            </tr>
            <c:forEach items="${sellerList}" var="seller">
                <tr bordercolor="rgb(78,78,78)">
                    <td>${seller.s_storeName}</td>
                    <td id="address">${seller.s_addr}</td>
                    <td>${seller.s_email}</td>
                    <td>${seller.s_phone}</td>
                    <td>
                        <a href="<c:url value="/admin/AdminServlet?method=checkRegist&s_id=${seller.s_id}&s_state=1"/>"
                           onclick="return confirm('确认通过${seller.s_storeName}的注册申请吗？')">通过审核</a> |
                        <a href="<c:url value="/admin/AdminServlet?method=checkRegist&s_id=${seller.s_id}&s_state=2"/>"
                           onclick="return confirm('确认拒绝${seller.s_storeName}的注册申请吗？')">拒绝申请</a>
                    </td>
                </tr>
            </c:forEach>

        </table>


        <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
        <script>
            window.onload = ()=>{
                // 页面加载好，先根据数据库中的经纬度，发送请求，获得地址
                $.ajax({
                    type: "GET",
                    url: "https://restapi.amap.com/v3/geocode/regeo",
                    data: {
                        key: "648c34239775a77fe9637615026d9f9b",
                        location: document.getElementById('address').innerHTML,
                        radius: 500
                    }
                })
                .then((data)=>{
                    document.getElementById('address').innerHTML = data.regeocode.formatted_address
                })
                .fail(function (xhr, errorType, error) {
                    alert("定位失败，开启GPS和WLAN试试？")
                    console.error(error);
                })
            }

        </script>
    </body>
</html>
