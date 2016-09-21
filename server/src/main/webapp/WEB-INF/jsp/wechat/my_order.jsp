<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>我的订单-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body>

<header>
    <div class="title">我的订单</div>
</header>


<section class="wrap">
    <div class="ui-list3">
        <ul>
            <c:forEach items="${CarRentalList}" var="cr" >
                <c:if test="${cr.order.status ne 3 && cr.order.status ne 4}">
                    <li class="c2">
                        <a onclick="carRental(${cr.id},${cr.order.status})">
                            <div class="cat">预定用车</div>
                            <c:if test="${cr.order.status eq 0}"><div class="state state3">待审核</div></c:if>
                            <c:if test="${cr.order.status eq 1}"><div class="state state2">待付款</div></c:if>
                            <c:if test="${cr.order.status eq 2}"><div class="state state4">进行中</div></c:if>

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
                                <input type="checkbox" class="rdo2" value="${cr.id}">
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
            if($(this).find(".state4").length==1){
                return;
            }
            disX = e.changedTouches[0].pageX - sX;
            disY = e.changedTouches[0].pageY - sY;
            if (Math.abs(disY) <= Math.abs(disX)) {
                e.preventDefault();
            }
            if(disX<0){
                $(this).find(".rdo2").attr("checked","checked");
            }
            if(disX>0){
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
        window.location.href = "${contextPath}/wechat/order/detail?id=" + id +"&status=" + status;
    }

    function del(){
        var ids = [];

        $(".rdo2:checked").each(function (){
            var id = $(this).val();
            ids.push(id);
        });

        $.ajax({
            "url": "${contextPath}/wechat/order/del",
            "data": {
                ids:JSON.stringify(ids)
            },
            "dataType": "json",
            "type": "POST",
            success: function (result) {
                if (result.status==0) {
                    layer.open({
                        content: '<i class="ico ico-right2"></i><br /><br />删除成功'
                        ,btn: '确定'
                        ,yes: function(index, layero){
                            window.location.href = "${contextPath}/wechat/order/myOrder/index";
                        }
                    });
                }else {
                    layer.open({
                        content: '<i class="ico ico-right2"></i><br /><br />删除失败'
                        ,btn: '确定'
                    });;
                }
            }
        });
    }

</script>
</body>
</html>