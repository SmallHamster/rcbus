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
    <div class="ticket ticket-state6">
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
        <div class="state state6">已结束</div>
        <div class="button">
            <c:if test="${routeOrder.order.isComment eq 0}">
                <button type="button" class="ubtn ubtn-red" id="comments">评价</button>
            </c:if>
            <c:if test="${routeOrder.order.isComment eq 1}">
                <button type="button" class="ubtn ubtn-gray" disabled>已评价</button>
            </c:if>
        </div>
        <input type="hidden" id="id" value="${routeOrder.id}">
    </div>
</section>
<div id="commentsbox" class="hide">
    <div class="hd">行程评价</div>
    <div class="hd-s">评价行程，即得优惠礼券</div>
    <div class="bd">
        <dl>
            <dt>司机服务</dt>
            <dd>
                <i class="star"><i></i><i></i><i></i><i></i><i></i></i>
                <input type="hidden" name="driverService" class="star">
            </dd>
        </dl>
        <dl>
            <dt>汽车环境</dt>
            <dd>
                <i class="star"><i></i><i></i><i></i><i></i><i></i></i>
                <input type="hidden" name="busEnvironment" class="star">
            </dd>
        </dl>
        <dl>
            <dt>安全驾驶</dt>
            <dd>
                <i class="star"><i></i><i></i><i></i><i></i><i></i></i>
                <input type="hidden" name="safeDriving" class="star">
            </dd>
        </dl>
        <dl>
            <dt>准时到达</dt>
            <dd>
                <i class="star"><i></i><i></i><i></i><i></i><i></i></i>
                <input type="hidden" name="arriveTime" class="star">
            </dd>
        </dl>
    </div>
</div>
<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>
<script>

    $(function() {

        var modal = $('#commentsbox').html();

        $('#comments').on('click', function() {
            layer.open({
                content: modal
                ,className: 'popup'
                ,btn: ['取消', '确定']
                ,yes: function(index) {
                    layer.close(index);
                }
                ,no: function(index){
                    var driverService = $("input[name=driverService]").eq(1).val();
                    var busEnvironment = $("input[name=busEnvironment]").eq(1).val();
                    var safeDriving = $("input[name=safeDriving]").eq(1).val();
                    var arriveTime = $("input[name=arriveTime]").eq(1).val();
                    var id = $("#id").val();

                    $.ajax({
                        "url": "${contextPath}/wechat/order/evaluation",
                        "data": {
                            id:id,
                            driverService : driverService,
                            busEnvironment : busEnvironment,
                            safeDriving : safeDriving,
                            arriveTime : arriveTime,
                            type : 2
                        },
                        "dataType": "json",
                        "type": "POST",
                        success: function (result) {
                            if (result.status==0) {
                                layer.open({
                                    content: '<i class="ico ico-right2"></i><br /><br />评价成功！'
                                    ,btn: '确定'
                                    ,yes: function(index) {
                                        window.location.reload();
                                    }
                                });
                            }else {
                                layer.open({
                                    content: '<i class="ico ico-right2"></i><br /><br />评价失败!'
                                    ,btn: '确定'
                                });
                            }
                        }
                    });

                }
            });

            return false;
        })

        $('body').on('touchstart', '.star i', function(e) {
            var idx = $(this).index();
            $(this).parent().find('i').each(function(i) {
                if (idx >= i) {
                    $(this).addClass('on');
                } else {
                    $(this).removeClass('on');
                }
            }).parent().next().val(1 + $(this).index());

            e.preventDefault();
        });


        $('body').on('touchstart', '.layui-m-layershade', function(e) {
            e.preventDefault();
        })
    })

</script>
</body>
</html>