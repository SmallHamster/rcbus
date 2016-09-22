<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../inc/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>通勤班车-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
    <style>
        body{background:#f2f3f4;}
    </style>
</head>

<body>

<header>
    <div class="title">班车线路</div>
</header>


<section class="wrap ticket-box">
    <div class="notice">*上车请主动出示车票</div>
    <div class="ticket">
        <div class="fromto">
            <em>${routeOrder.startStation}</em>
            <img src="${contextPath}/wechat-html/images/arrow_right_white.png" />
            <em>${routeOrder.endStation}</em>
        </div>
        <c:forEach items="${routeOrder.route.busSends}" var="bs" begin="0" end="0">
            <div class="number">${bs.bus.carNo}</div>
        </c:forEach>
        <div class="space">
            <img src="${contextPath}/wechat-html/images/line_mid.png" alt="">
        </div>
        <div class="time"><date:date format='yyyy年MM月dd日' value='${routeOrder.order.createDate}'></date:date></div>
        <p>发车时间：${routeOrder.departTime}</p>
        <c:forEach items="${routeOrder.route.busSends}" var="bs" begin="0" end="0">
            <p>${bs.bus.modelNo}</p>
        </c:forEach>
        <p>单位名称：${routeOrder.order.userInfo.enterprise.name}</p>
        <div class="state state1">未出行</div>
    </div>
</section>



<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>

</body>
</html>