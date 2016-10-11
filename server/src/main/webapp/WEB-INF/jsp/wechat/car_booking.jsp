<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>用车预定-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">
    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body>

<header>
    <div class="title">用车预定</div>
</header>

<section class="wrap route-box">
    <div class="slide">
        <ul>
            <c:forEach items="${banner}" var="v">
                <li>
                    <a href="${v.outsideChain}"><img src="${v.image.uploadUrl}"/></a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <div class="ui-list2">
        <div class="hd">全部车型</div>
        <div class="bd">
            <ul>
                <c:forEach items="${carType}" var="v">
                    <li>
                        <div class="img">
                            <%--<img src="${contextPath}/wechat-html/uploads/pro1.jpg" alt="">--%>
                            <img src="${v.image.uploadUrl}" alt="">
                        </div>
                        <div class="txt">
                            <em>${v.name}</em>
                            <img src="${contextPath}/wechat-html/images/hot.png" alt="热门">
                            <input type="hidden" id="carTypeId" value="${v.id}">
                            <a onclick="line(this)" class="ubtn ubtn-ghost">预定用车</a>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</section>


<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/app.js"></script>

<script>
    $(function() {
        $('.slide').each(function() {
            var $self = $(this),
                    $nav,
                    length = $self.find('li').length;

            if (length < 2) {
                return;
            }
            var nav = ['<div class="nav">'];
            for (var i = 0 ; i < length; i++) {
                nav.push( i === 0 ? '<i class="current"></i>' : '<i></i>');
            }
            nav.push('</div>');
            $self.append(nav.join(''));
            $nav = $self.find('i');

            $(this).swipeSlide({
                index : 0,
                continuousScroll : true,
                autoSwipe : false,
                lazyLoad : true,
                firstCallback : function(i,sum){
                    $nav.eq(i).addClass('current').siblings().removeClass('current');
                },
                callback : function(i,sum){
                    $nav.eq(i).addClass('current').siblings().removeClass('current');
                }
            });
        })
    });

    function line(data){
        var carTypeId = $(data).parent().find("input").val();
        var params = "";
        if (carTypeId != null && carTypeId != '') {
            params = "?carTypeId=" + carTypeId +"&index=1";
        }
        window.location.href = "${contextPath}/wechat/carrental/add" + params;
    }

</script>
</body>
</html>