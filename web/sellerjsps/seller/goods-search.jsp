<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>搜索商品</title>
    <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/goods-search.css"/>">
    <link rel="stylesheet" href="<c:url value="/common/css/base.css"/>">

    <script>
        //创建异步对象
        function createXMLHttpRequest() {
            try {
                return new XMLHttpRequest(); //支持大多数浏览器
            } catch (e) {
                try {
                    return new ActiveXObject("Msxml2.XMLHTTP"); //IE6.0
                } catch (e) {
                    try {
                        return new ActiveXObject("Microsoft.XMLHTTP"); //IE5.5及更早版本
                    } catch (e) {
                        alert("您用的浏览器是啥？");
                        throw e;
                    }
                }
            }
        }

        window.onload = function () {
            /**
             * 给<select id="type">添加onchange监听
             */
            document.getElementById("type").onchange = function () {
                //异步请求服务器，得到选择的省下所有市
                var xmlHttp = createXMLHttpRequest();
                xmlHttp.open("POST", "<c:url value="/LoadSubTypeServlet"/>", true);
                //设置请求头
                xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                //发送
                xmlHttp.send("t_id=" + this.value); //商家选择的一级分类
                xmlHttp.onreadystatechange = function () {
                    if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
                        /**
                         * 0.先要清空原来的<option>元素
                         * 1.得到服务器发送过来的所有二级分类
                         * 2.使用每个二级分类生成<option>元素添加到<select id="subtype">中
                         */
                            //清空<select id="subtype">中的选项
                        var subtypeSelect = document.getElementById("subtype");
                        //获取select中的所有子元素
                        var subtypeOptionArray = subtypeSelect.getElementsByTagName("option");
                        while (subtypeOptionArray.length > 1) { //这里只控制循环的次数
                            subtypeSelect.removeChild((subtypeOptionArray[1])); //只删除1下标，不会删除0
                        }

                        //得到服务器发送过来的所有二级分类
                        var subtypeArray = eval("(" + xmlHttp.responseText + ")");
                        for (var i = 0; i < subtypeArray.length; i++) {
                            var subtype = subtypeArray[i]; //得到每个city对象
                            var optionEle = document.createElement("option"); //创建option元素
                            optionEle.value = subtype.sub_id; //给<option>的实际值赋为sub_id
                            var textNode = document.createTextNode(subtype.sub_name);//给<option>的显示值赋为name
                            optionEle.appendChild(textNode);

                            //把option元素添加到select元素中
                            document.getElementById("subtype").appendChild(optionEle);
                        }
                    }
                };
            }
        };
    </script>
</head>
<body>
    <a href="javascript:history.go(-1);" style="float: left; margin-left: 20px">&lt;&lt;返回</a><br/>
    <p style="font-size: 20px; color: firebrick; width: 100%; text-align: center; padding-right: 100px">${msg }</p>

    <form action="<c:url value="/seller/SellerGoodsServlet"/>" method="post">
        <input type="hidden" name="method" value="searchGoodsByName"/>
        <input type="hidden" name="s_id" value="${session_seller.s_id}"/>
        <div class="search">
            <input type="text" id="search" name="keyword">
            <button id="searchbtn">搜索</button>
        </div>
    </form>
    <form action="<c:url value="/seller/SellerGoodsServlet"/>" method="post">
        <input type="hidden" name="method" value="searchGoodsByType"/>
        <input type="hidden" name="s_id" value="${session_seller.s_id}"/>
        <div class="selection">
            <select name="t_id" id="type">
                <option>===请选择一级分类===</option>
                <c:forEach items="${typeList}" var="type">
                    <option value="${type.t_id}">${type.t_name}</option>
                </c:forEach>
            </select>
            <select name="sub_id" id="subtype">
                <option>===请选择二级分类===</option>
            </select>
            <button id="selectionbtn">按种类搜索</button>
        </div>
    </form>
</body>
</html>