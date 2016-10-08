<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>转赠优惠券-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body>

<header>
    <div class="title">转赠优惠券</div>
</header>

<section class="wrap ui-form">
    <div class="banner">
        <img src="${contextPath}/wechat-html/images/banner_coupons.jpg" width="100%" alt="">
    </div>


    <div class="coupons-list">
        <input type="hidden" id="id" value="${userCoupon.id}">
        <ul>
            <c:if test="${userCoupon.coupon.couponWay eq 1}">
            <li class="c1">
            </c:if>
            <c:if test="${userCoupon.coupon.couponWay eq 2}">
            <li class="c2">
            </c:if>
                <div class="hd">
                    <em>${fn:substring(userCoupon.coupon.name ,0,fn:length(userCoupon.coupon.name)-4)}</em>
                    <c:if test="${userCoupon.coupon.couponWay eq 1}"><sub>折</sub></c:if>
                    <c:if test="${userCoupon.coupon.couponWay eq 2}"><sub>元</sub></c:if>

                </div>
                <div class="bd">
                    <h4>${fn:substring(userCoupon.coupon.name ,fn:length(userCoupon.coupon.name)-3,fn:length(userCoupon.coupon.name))}</h4>
                    <c:if test="${userCoupon.coupon.isLimit eq 0}">
                        <p>无限制</p>
                    </c:if>
                    <c:if test="${userCoupon.coupon.isLimit eq 1}">
                        <p>限${userCoupon.coupon.limitMoney}元以上使用</p>
                    </c:if>
                    <p style="font-size: 10px">有效期<date:date value="${userCoupon.coupon.validDateFrom}" format="yyyy-MM-dd HH:ss" ></date:date> 至<date:date value="${userCoupon.coupon.validDateTo}" format="yyyy-MM-dd HH:ss" ></date:date></p>
                </div>
            </li>
        </ul>

        <div class="result">
            更多精彩活动，尽在江城巴士公众号
        </div>

    </div>

    <div class="button">
        <button class="ubtn ubtn-blue" id="submit">转增给好友</button>
    </div>

</section>


<div id="mobilebox" class="hide">
    <div class="hd">请输入好友电话</div>
    <div class="input">
        <input type="text" class="ipt" />
    </div>
</div>


<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>
<script src="${contextPath}/wechat-html/js/app.js"></script>
<script>
    $(function(){

        var modal = $('#mobilebox').html();

        // 保存
        $('#submit').on('click', function() {
            layer.open({
                content: modal
                ,className: 'popup'
                ,btn: '确定转赠'
                ,yes: function(index) {
                    var val = $('.popup .ipt').val();
                    if (!val) {
                        alertMsg('请输入手机号', 3e3);
                    } else if (!/^1[3-9]\d{9}$/.test(val)) {
                        alertMsg('手机号码格式错误', 3e3);
                    } else {
                        var id = $("#id").val();
                        $.ajax({
                            "url": "${contextPath}/wechat/coupon/givingSave",
                            "data": {
                                id:id,
                                mobile:val
                            },
                            "dataType": "json",
                            "type": "POST",
                            success: function (result) {
                                if (result==1) {
                                    layer.close(index);
                                    window.location.href = "${contextPath}/wechat/coupon/index";
                                    alertMsg('转赠完成', 3e3);
                                }else if (result==2){
                                    alertMsg('该手机号还未注册', 3e3);
                                }else{
                                    alertMsg('转赠失败', 3e3);
                                }
                            }
                        });
                    }

                }
            });
        });
    })
</script>

</body>
</html>