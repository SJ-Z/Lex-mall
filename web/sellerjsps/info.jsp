<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>info.jsp</title>
        <link rel="shortcut icon" href="<c:url value="/sellerjsps/res/img/favicon.ico"/>">
        <style>
            html {
                width: 100%;
                margin: 0;
            }

            body {
                width: 100%;
                margin: 0;
                background-image: url(<c:url value="/sellerjsps/res/img/3.jpg"/>);
                background-size: cover;
            }

            p {
                display: block;
                width: 100%;
                height: 100px;
                text-align: center;
                background-color: #ffffff;
                margin: 0;
                margin-top: 10%;
                font-size: 50px;
                color: firebrick;
                padding-top: 40px;
            }
        </style>
    </head>
    <body>
        <p>${msg }</p>
    </body>
</html>
