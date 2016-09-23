<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>我的礼券-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body>

<header>
    <div class="title">我的礼券</div>
</header>

<section class="wrap">
    <div class="coupons-list">
        <ul>
            <c:forEach var="cw1" items="${couponWay1}">
                <li class="c1" onclick="giving(${cw1.id})">
                    <div class="hd">
                        <em>${fn:substring(cw1.name ,0,fn:length(cw1.name)-4)}</em>
                        <sub>折</sub>
                    </div>
                    <div class="bd">
                        <h4>${fn:substring(cw1.name ,fn:length(cw1.name)-3,fn:length(cw1.name))}</h4>
                        <c:if test="${cw1.isLimit eq 0}">
                            <p>无限制</p>
                        </c:if>
                        <c:if test="${cw1.isLimit eq 1}">
                            <p>限${cw1.limitMoney}元以上使用</p>
                        </c:if>
                        <p style="font-size: 10px">有效期<date:date value="${cw1.validDateFrom}" format="yyyy-MM-dd HH:ss" ></date:date> 至<date:date value="${cw1.validDateTo}" format="yyyy-MM-dd HH:ss" ></date:date></p>
                    </div>
                </li>
            </c:forEach>

            <c:forEach var="cw2" items="${couponWay2}">
                <li class="c2" onclick="giving(${cw2.id})">
                    <div class="hd">
                        <em>${fn:substring(cw2.name ,0,fn:length(cw2.name)-4)}</em>
                        <sub>折</sub>
                    </div>
                    <div class="bd">
                        <h4>${fn:substring(cw2.name ,fn:length(cw2.name)-3,fn:length(cw2.name))}</h4>
                        <c:if test="${cw2.isLimit eq 0}">
                            <p>无限制</p>
                        </c:if>
                        <c:if test="${cw2.isLimit eq 1}">
                            <p>限${cw2.limitMoney}元以上使用</p>
                        </c:if>
                        <p style="font-size: 10px">有效期<date:date value="${cw2.validDateFrom}" format="yyyy-MM-dd HH:ss" ></date:date> 至<date:date value="${cw2.validDateTo}" format="yyyy-MM-dd HH:ss" ></date:date></p>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>

</section>

<script>


</script>


</body>
</html>