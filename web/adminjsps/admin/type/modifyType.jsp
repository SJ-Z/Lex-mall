<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
    <head>
        <title>修改一级分类</title>
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
            table{
                margin: auto;
                margin-top: 6%;
            }
        </style>
    </head>

    <body>
        <a href="javascript:history.go(-1);">&lt;&lt;返回</a><br/><br/>
        <p style="font-size: 16px; color: firebrick">${msg }</p>
        <form action="<c:url value="/admin/AdminModifyTypeServlet"/>" method="post" enctype="multipart/form-data" style="text-align: center">
            <input type="hidden" name="method" value="modifyType"/>
            <input type="hidden" name="t_id" value="${type.t_id}"/>
            <table border="1" width="30%" cellspacing="0" background="black">
                <tr>
                    <th colspan="2"><h2 align="center">修改一级分类</h2></th>
                </tr>
                <tr>
                    <td>分类名称</td>
                    <td><input type="text" name="t_name" value="${type.t_name}"/></td>
                </tr>
                <tr>
                    <td>分类图片</td>
                    <td>
                        <div><img src="<c:url value="${type.t_image}"/>" height="60" id="pic"/></div>
                    </td>
                </tr>
                <tr>
                    <td>修改图片</td>
                    <td><input type="file" name="t_image" id="modifypic"/></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="修改分类"/></td>
                </tr>
            </table>
        </form>

        <script type="text/javascript">
            $(function() {
                $("#modifypic").click(); //隐藏了input:file样式后，点击头像就可以本地上传
                $("#modifypic").on("change",function(){
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
    </body>
</html>
