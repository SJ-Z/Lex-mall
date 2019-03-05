<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <title>商家</title>
        <link rel="shortcut icon" href="<c:url value="/sellerjsps/res/img/favicon.ico"/>">
        <link rel="stylesheet" href="<c:url value="/sellerjsps/works/css/open.css"/>">

    </head>
        <body>
        <table cellspacing="0" cellpadding="0" border="0">
            <tr id="tr1">
                <td colspan="2" id="top">
                    <iframe class="top" src="<c:url value="seller-top.jsp"/>" frameborder="0" scrolling="no"></iframe>
                </td>
            </tr>
            <tr id="tr2">
                <td id="left">
                    <iframe src="<c:url value="seller-left.jsp"/>" frameborder="0" scrolling="no"></iframe>
                </td>
                <td id="right">
                    <iframe src="<c:url value="information-browser.jsp"/>" frameborder="0" name="show"></iframe>
                </td>
            </tr>
        </table>
        </body>


</html>
