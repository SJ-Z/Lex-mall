<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>添加一级分类</title>
        <script type="text/javascript" src="<c:url value="/adminjsps/css/jquery-3.3.1.min.js"/>"></script>
        <style type="text/css">
            a:link {
                font-size: 16px;
                color: #00a1ff;
                text-decoration: none;
            }

            a:visited {
                font-size: 16px;
                color: #00a1ff;
                text-decoration: none;
            }

            a:hover {
                font-size: 16px;
                color: slateblue;
                text-decoration: none;
            }
            input{
                width: 150px;
                height: 30px;
                outline: none;
                background-color: #ffffff;
                border: 1px solid gray;
                margin: 10px;
                border-radius: 3px;
            }
            *{
                font-size: 20px;
            }
            img{
                width: 64px;
                height: 64px;
                vertical-align: top;
            }
            #button{
                background-color: #6699cc;
                color: #fff;
                border: none;
                margin-left: 100px;
            }
            #button:hover{
                background-color: rgb(106,193,233);
            }

        </style>
    </head>

    <body>
        <a href="javascript:history.go(-1);">&lt;&lt;返回</a>
        <h2>添加一级分类</h2>
        <p style="font-size: 16px; color: firebrick">${msg }</p>
        <form action="<c:url value="/admin/AdminAddTypeServlet"/>" method="post" enctype="multipart/form-data">
            一级分类名称：<input type="text" name="t_name" value="${t_name}"/><br/>
            一级分类图片：<input type="file" name="t_image" id="photo">
            <img src="<c:url value="/adminjsps/img/addphoto.png"/>" id="pic"><br>
            <input type="submit" value="添加一级分类" id="button"/>
        </form>
    </body>
    <script type="text/javascript">
        $(function() {
            $("#photo").on("change",function(){
                var objUrl = getObjectURL(this.files[0]) ; //获取图片的路径，该路径不是图片在本地的路径
                if (objUrl) {
                    $("#pic").attr("src", objUrl) ; //将图片路径存入src中，显示出图片
                }
            });
        });
        //建立一個可存取到該file的url
        function getObjectURL(file) {
            var url = null ;
            if (window.createObjectURL!=undefined) { // basic
                url = window.createObjectURL(file) ;
            } else if (window.URL!=undefined) { // mozilla(firefox)
                url = window.URL.createObjectURL(file) ;
            } else if (window.webkitURL!=undefined) { // webkit or chrome
                url = window.webkitURL.createObjectURL(file) ;
            }
            return url ;
        }
    </script>
</html>
