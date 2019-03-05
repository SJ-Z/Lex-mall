<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <style type="text/css">
            *{
                font-size:10pt;
            }
            html{
                width: 100%;
                height: 100%;
                margin: 0;
            }
            body{
                height: 100%;
                width: 100%;
                text-align:center;
                margin: 0;
            }
            .table{
                width:98%;
                height:100%;
            }
            iframe {
                width: 100%;
                height: 100%;
                overflow: hidden;
            }
        </style>
    </head>
    <body>
        <table class="table" align="center" border="0">
            <tr style="height: 100px; ">
                <td align="center" width="100%" height="100px">
                    <iframe style="padding-top: 10px;" frameborder="0" src="<c:url value='/sellerjsps/seller/order/choose.jsp'/>" name="order_top" scrolling="no"></iframe>
                </td>
            </tr>
            <tr>
                <td>
                    <iframe frameborder="0" src="<c:url value='/sellerjsps/seller/order/list.jsp'/>" name="order_body"></iframe>
                </td>
            </tr>
        </table>
        <script src="<c:url value="/sellerjsps/works/js/order-browser.js"/>"></script>
    </body>
</html>