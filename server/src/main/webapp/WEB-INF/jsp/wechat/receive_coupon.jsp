<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>领取优惠券-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body>

<header>
    <div class="title">领取优惠券</div>
</header>

<section class="wrap ui-form">
    <div class="banner">
        <img src="${contextPath}/wechat-html/images/banner_coupons.jpg" width="100%" alt="">
    </div>

    <div class="tips">
        您的好友向您分享了 “江城巴士” ，填写<br>手机号免费领取优惠券
    </div>
    <form action="">
        <div class="form">
            <div class="item">
                <input type="text" class="ipt ipt-tc" name="mobile" id="mobile" placeholder="请输入您的手机号">
            </div>
        </div>
        <input type="hidden" id="rentalId" value="${rentalId}">
        <div class="button">
            <button type='button' class="ubtn ubtn-blue" id="submit" >确认领取</button>
        </div>
    </form>

</section>


<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/app.js"></script>
<script>
    $(function(){
        var $mobile = $('#mobile');

        function checkMobile() {
            var val = $mobile.val();
            if (!val) {
                alertMsg('请输入手机号', 3e3);
            } else if (!/^1[3-9]\d{9}$/.test(val)) {
                alertMsg('手机号码格式错误', 3e3);
            } else {
                var mobile = $("#mobile").val();
                var rentalId = $("#rentalId").val();
                $.ajax({
                    "url": "${contextPath}/wechat/coupon/receiveSave",
                    "data": {
                        mobile:mobile,
                        rentalId:rentalId
                    },
                    "dataType": "json",
                    "type": "POST",
                    success: function (result) {
                        if (result==1) {
                            alertMsg('领取完成', 3e3);
                        }else if(result==2){
                            alertMsg('超出领取上限', 3e3);
                        }else if(result==3){
                            alertMsg('您已经领取过了', 3e3);
                        }else {
                            alertMsg('领取失败', 3e3);
                        }
                    }
                });
                return true;
            }
            $mobile.focus();
            return false;
        }


        // 保存
        $('#submit').on('click', function() {
            return checkMobile();
        });
    })
</script>

</body>
</html>