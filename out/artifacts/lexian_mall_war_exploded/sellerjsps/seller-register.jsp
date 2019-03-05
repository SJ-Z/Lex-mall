<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>商家注册</title>
        <link rel="stylesheet" href="<c:url value="/common/css/base.css"/>">
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/manager-register.css"/>" type="text/css">
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/manager-main.css"/>">
        <link rel="shortcut icon" href="<c:url value="/sellerjsps/res/img/favicon.ico"/>">
    </head>
    <body>
        <img src="<c:url value="/sellerjsps/res/img/banner1.png"/>" class="fl register-logo">
        <img src="<c:url value="/sellerjsps/res/img/register-slogen.png"/>" class="fl register-slogen">
        <div class="register fr">
            <div class="top">
                <label id="register-c">注册&nbsp;</label><label id="register-e">&nbsp;Register</label>
            </div>
            <form id="form" action="<c:url value="/SellerBeforeLoginServlet"/>" method="post">
                <input type="hidden" name="method" value="regist"/>
                <span id="s_name_msg">${errors.s_name}</span>
                <div class="managerdiv" id="manager-username">
                    <label for="s_name" class="col-3">用户名:</label>
                    <input  type="text" id="s_name" name="s_name" value="${form.s_name}" >
                </div>

                <span id="s_pwd_msg">${errors.s_pwd}</span>
                <div class="managerdiv" id="manager-password">
                    <label for="s_pwd" class="col-3">密&nbsp;&nbsp;码:</label>
                    <input  type="password" id="s_pwd" name="s_pwd" autocomplete="off" >
                </div>
                <span id="_pwd_msg">${errors._pwd}</span>
                <div class="managerdiv" id="manager-password2">
                    <label for="_pwd" class="col-3">重复密码:</label>
                    <input  type="password" id="_pwd" name="_pwd" autocomplete="off">
                </div>

                <span id="s_email_msg">${errors.s_email}</span>
                <div class="managerdiv" id="manager-email">
                    <label for="s_email" class="col-3">邮&nbsp;&nbsp;箱:</label>
                    <input type="email" id="s_email" name="s_email" value="${form.s_email}" autocomplete="off">
                </div>
                <span id="s_storeName_msg">${errors.s_storeName}</span>
                <div class="managerdiv">
                    <label>店名：</label>
                    <input type="text" name="s_storeName" value="${form.s_storeName}">
                </div>
                <span id="s_phone_msg">${errors.s_phone}</span>
                <div class="managerdiv">
                    <label>电话：</label>
                    <input type="tel" name="s_phone" value="${form.s_phone}">
                </div>
                <span id="s_addr_msg">${errors.s_addr}</span>
                <div class="managerdiv">
                    <label>地址：</label>
                    <input id="addr" type="text" name="addr">
                    <input id="s_addr" type="hidden" name="s_addr">
                </div>
                <div class="manager-vertification">
                    <input type="text" id="vertification" name="s_verifyCode" autocomplete="off" size="6px">
                    &nbsp;&nbsp;
                    <a href="javascript:_change()">
                        <img id="img_verifyCode" src="<c:url value="/VerifyCodeServlet"  />" alt="验证码"  border="1"/>
                    </a>
                </div>
                <p style="font-size: 15px; color: firebrick">${msg }</p>
                <div class="manager-submit">
                    <button id="submit" class="fl">确认</button>
                    <button id="reset" type="reset" class="fr">重置</button>
                </div>
            </form>
            <div class="bottom fr">
                <p>已有账号？<a href="<c:url value="/sellerjsps/index.jsp"/>">去登录</a></p>
            </div>
        </div>

        <script src="./works/jquery-3.3.1.min.js"></script>
        <script type="text/javascript">
            function _change() {
                var ele = document.getElementById("img_verifyCode");
                ele.src = "<c:url value="/VerifyCodeServlet" />?xxx=" + new Date().getTime();
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

            /** 要求元素的id和name值相同 */
            function confirm(id, url) {
                var element = document.getElementById(id);
                element.onchange = function () {
                    var xmlHttp = createXMLHttpRequest();
                    xmlHttp.open("POST", url, true);
                    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    xmlHttp.send(id + "=" + element.value);
                    xmlHttp.onreadystatechange = function () {
                        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
                            //获取服务器的响应结果
                            var msg = xmlHttp.responseText;
                            //获取span元素
                            var span = document.getElementById(id + "_msg");
                            span.innerHTML = msg;
                        }
                    }
                };
            }

            /** 验证两次密码是否一致 */
            function confirmPassword() {
                var password1 = document.getElementById("s_pwd");
                var password2 = document.getElementById("_pwd");
                password2.onchange = function () {
                    var password2_msg = document.getElementById("_pwd_msg");
                    password2_msg.innerHTML = "";
                    if (password1.value != password2.value) {
                        password2_msg.innerHTML = "两次密码输入不一致！";
                    } else {
                        password2_msg.innerHTML = "输入正确！";
                    }
                }
            }

            window.onload = function () {
                confirm("s_name", "<c:url value='/ConfirmUsernameServlet'/>");
                confirm("s_pwd", "<c:url value='/ConfirmPasswordServlet'/>");
                confirm("s_email", "<c:url value='/ConfirmEmailServlet'/>");
                confirmPassword();

                document.getElementById('addr').onblur = ()=>{
                    if(document.getElementById('addr').value){
                        $.ajax({
                            type: "GET",
                            url: "https://restapi.amap.com/v3/geocode/geo",
                            data: {
                                key: "648c34239775a77fe9637615026d9f9b",
                                address: document.getElementById('addr').value
                            },
                            success: function (data, status, xhr) {
                                document.getElementById('s_addr').value =  data.geocodes[0].location;
                            },
                            error: function (xhr, errorType, error) {
                                alert("定位失败，高德背锅")
                                console.error(error);
                            }
                        });
                    }else{
                        document.getElementById('s_addr').value = ''
                    }

                }

            }
        </script>
    </body>
</html>