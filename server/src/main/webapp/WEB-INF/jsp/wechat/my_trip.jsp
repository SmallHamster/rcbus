<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>我的行程-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body>

<header>
    <div class="title">我的行程</div>
</header>

<section class="wrap">
    <div class="ui-list3">
        <ul>
            <c:forEach items="${routeOrderList}" var="ro" >
                <c:if test="${ro.route.enterprise.type == 0}">
                <li class="c1">
                </c:if>
                <c:if test="${ro.route.enterprise.type == 1}">
                    <li class="c2">
                </c:if>
                    <a onclick="carRoute(${ro.id})">
                        <c:if test="${ro.route.enterprise.type == 0}">
                            <div class="cat">通勤班车</div>
                        </c:if>
                        <c:if test="${ro.route.enterprise.type == 1}">
                            <div class="cat">永旺专线</div>
                        </c:if>

                        <div class="state state6">已结束</div>
                        <div class="inner">
                            <div class="date"><date:date format='yyyy年MM月dd日' value='${ro.order.createDate}'></date:date></div>
                            <div class="fromto">
                                <em>${ro.startStation}</em>
                                <i></i>
                                <em>${ro.endStation}</em>
                            </div>
                            <div class="detail">
                                <c:forEach items="${ro.route.busSends}" var="bs" begin="0" end="0">
                                    <span>${bs.bus.modelNo}</span>
                                    <span>发车时间：${ro.departTime}</span>
                                    <span>车牌：${bs.bus.carNo}</span>
                                    <span>保险单号：${bs.bus.policyNo}</span>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="check">
                            <input type="checkbox" class="rdo2" name="ro_id" value="${ro.id}">
                        </div>
                    </a>
                </li>
            </c:forEach>

            <c:forEach items="${carRentalList}" var="cr" >
                <c:if test="${cr.order.status eq 3 || cr.order.status eq 4}">
                    <li class="c2">
                        <a onclick="carRental(${cr.id},${cr.order.status})">
                            <div class="cat">预定用车</div>
                            <c:if test="${cr.order.status eq 3}"><div class="state state6">已结束</div></c:if>
                            <c:if test="${cr.order.status eq 4}"><div class="state state5">已取消</div></c:if>

                            <div class="inner">
                                <div class="date"><date:date format='yyyy年MM月dd日' value='${cr.startDate}'></date:date></div>
                                <div class="fromto">
                                    <em>${cr.startPoint}</em>
                                    <i></i>
                                    <em>${cr.endPoint}</em>
                                </div>
                                <div class="detail">
                                    <c:forEach items="${cr.busSends}" var="bs" begin="0" end="0">
                                        <span>${bs.bus.modelNo}</span>
                                        <span>发车时间：<date:date format='HH:mm' value='${cr.startDate}'></date:date></span>
                                        <span>车牌：${bs.bus.carNo}</span>
                                        <span>保险单号：${bs.bus.policyNo}</span>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="check">
                                <input type="checkbox" class="rdo2" name="cr_id" value="${cr.id}">
                            </div>
                        </a>
                    </li>
                </c:if>
            </c:forEach>
        </ul>

        <div class="button">
            <button class="ubtn ubtn-blue" onclick="del()">删除</button>
        </div>
    </div>

</section>

<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>
<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script>
    $(function() {
        var sX = 0;    // 手指初始x坐标
        var sY = 0;    // 手指初始y坐标
        var disX = 0;  // 滑动差值
        var disY = 0;  // 滑动差值

        $('.ui-list3').on('touchstart', '.check', function(e) {
            var rdo2 = $(this).find('.rdo2')[0];
            rdo2.checked = !rdo2.checked;
            return false;
        });
        $('.ui-list3').on('touchstart', 'li', function(e){
            sX = e.changedTouches[0].pageX;
            sY = e.changedTouches[0].pageY;
        });
        $('.ui-list3').on('touchmove', 'li', function(e){
            disX = e.changedTouches[0].pageX - sX;
            disY = e.changedTouches[0].pageY - sY;
            if (Math.abs(disY) <= Math.abs(disX)) {
                e.preventDefault();
            }
            if(disX<-40){
                $(this).find(".rdo2").attr("checked","checked");
            }
            if(disX>40){
                $(this).find(".rdo2").removeAttr("checked");
            }
        });
        $('.ui-list3').on('touchend', 'li', function(e){
            if (Math.abs(disY) > 40) {
                return;
            }
            if (disX > 40) {
                $(this).removeClass('on');

            } else if (disX < -40) {
                $(this).addClass('on');

            }
        })
    });


    function carRental(id,status){
        window.location.href = "${contextPath}/wechat/order/myOrder/detail?id=" + id +"&status=" + status;
    }

    function carRoute(id){
        window.location.href = "${contextPath}/wechat/order/myRoute/detail?id=" + id +"&status=" + 1;
    }

    function del(){
        var ro_ids = [];
        var cr_ids = [];

        $("input[name=ro_id]:checked").each(function (){
            var id = $(this).val();
            ro_ids.push(id);
        });

        $("input[name=cr_id]:checked").each(function (){
            var id = $(this).val();
            cr_ids.push(id);
        });

        $.ajax({
            "url": "${contextPath}/wechat/order/del",
            "data": {
                ro_ids:JSON.stringify(ro_ids),
                cr_ids:JSON.stringify(cr_ids)
            },
            "dataType": "json",
            "type": "POST",
            success: function (result) {
                if (result.status==0) {
                    layer.open({
                        content: '<i class="ico ico-right2"></i><br /><br />删除成功'
                        ,btn: '确定'
                        ,yes: function(index, layero){
                            window.location.reload();
                        }
                    });
                }else {
                    layer.open({
                        content: '<i class="ico ico-right2"></i><br /><br />删除失败'
                        ,btn: '确定'
                    });
                }
            }
        });
    }
</script>
</body>
</html>