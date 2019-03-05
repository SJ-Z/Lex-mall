<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>添加商品</title>
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/goods-add.css"/>">
        <link rel="stylesheet" href="<c:url value="/common/css/base.css"/>">
        <script type="text/javascript" src="<c:url value="/sellerjsps/works/jquery-3.3.1.min.js"/>"></script>
    </head>
    <body>
        <a href="javascript:history.go(-1);" style="float: left; margin-left: 20px">&lt;&lt;返回</a><br/>
        <p style="font-size: 20px; color: firebrick; width: 100%; text-align: center; padding-right: 100px">${msg }</p>
        <div class="add">
            <form action="<c:url value="/seller/SellerAddGoodsServlet"/>" method="post" enctype="multipart/form-data">
                <div class="photo-div" id="add-photo-input">
                    <input type="hidden" name="s_id" value="${session_seller.s_id}"/>
                    <c:set var="select_t_id" value="${goods.g_type.t_id}" scope="request"/>
                    <div class="add-photo-input">
                        <label for="add-photo">
                            <img src="" alt="" id="pic">
                        </label>
                        <input type="file" id="add-photo" name="g_image"/>
                    </div>
                </div>
                <div class="add-div">
                    <label for="add-name">名&nbsp;&nbsp;称：</label>
                    <input id="add-name" name="g_name" value="${goods.g_name}" type="text">
                </div>
                <div class="add-div">
                    <label>种&nbsp;&nbsp;类：</label>
                    <select name="t_id" id="type">
                        <option>===请选择一级分类===</option>
                        <c:forEach items="${typeList}" var="type">
                            <option value="${type.t_id}"
                                    <c:if test="${type.t_id==select_t_id}">selected</c:if>>${type.t_name}</option>
                        </c:forEach>
                    </select>
                    <select name="sub_id" id="subtype">
                        <option>===请选择二级分类===</option>
                    </select>
                </div>
                <div class="add-div">
                    <label for="add-price">价&nbsp;&nbsp;格：</label>
                    <c:choose>
                        <c:when test="${goods.g_price eq null || goods.g_price eq 0.0}">
                            <input id="add-price" name="g_price" type="text">
                        </c:when>
                        <c:otherwise>
                            <input id="add-price" name="g_price" value="${goods.g_price}" type="text">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="add-div">
                    <label for="add-sales">促&nbsp;&nbsp;销：</label>
                    <c:choose>
                        <c:when test="${goods.g_discount eq null || goods.g_discount eq 0.0}">
                            <input id="add-sales" name="g_discount" type="text">
                        </c:when>
                        <c:otherwise>
                            <input id="add-sales" name="g_discount" value="${goods.g_discount}" type="text">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="add-div">
                    <label for="add-store">货&nbsp;&nbsp;存：</label>
                    <c:choose>
                        <c:when test="${goods.g_count eq null || goods.g_count eq 0}">
                            <input id="add-store" name="g_count" type="text">
                        </c:when>
                        <c:otherwise>
                            <input id="add-store" name="g_count" value="${goods.g_count}" type="text">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="add-description">
                    <label for="add-description">描&nbsp;&nbsp;述：</label>
                    <textarea id="add-description" name="g_desc" cols="25" rows="3" maxlength="200"
                             onchange="this.value=this.value.substring(0, 200)"
                             onkeydown="this.value=this.value.substring(0, 200)"
                             onkeyup="this.value=this.value.substring(0, 200)">${goods.g_desc}</textarea>
                </div>
                <div class="add-bottom">
                    <input id="add-add" type="submit" value="添加"style="border: none" />
                    <button id="add-reset" type="reset">重置</button>
                </div>
            </form>
        </div>
        <script type="text/javascript">
            $(function () {
                $("#add-photo").on("change", function () {
                    var objUrl = getObjectURL(this.files[0]); //获取图片的路径，该路径不是图片在本地的路径
                    if (objUrl) {
                        $("#pic").attr("src", objUrl); //将图片路径存入src中，显示出图片
                        document.getElementById('add-photo').style.backgroundImage.url = '';
                    }
                });
            });

            //建立一個可存取到該file的url
            function getObjectURL(file) {
                var url = null;
                if (window.createObjectURL != undefined) { // basic
                    url = window.createObjectURL(file);
                } else if (window.URL != undefined) { // mozilla(firefox)
                    url = window.URL.createObjectURL(file);
                } else if (window.webkitURL != undefined) { // webkit or chrome
                    url = window.webkitURL.createObjectURL(file);
                }
                return url;
            }

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
    </body>
</html>
