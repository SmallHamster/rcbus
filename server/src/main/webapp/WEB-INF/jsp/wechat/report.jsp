<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>意见反馈-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
    <style>
        .ui-form .item{margin-bottom:40px;}
        .button{margin-top:140px;}
    </style>
</head>

<body>

<header>
    <div class="title">意见反馈</div>
</header>


<section class="wrap ui-form">

    <form id="report">
        <div class="form">
            <div class="item">
                <input type="text" class="ipt ipt-row" id="mobile" name="mobile" value="" placeholder="联系方式（不填为注册默认手机号）" />
                <span class="error"></span>
            </div>

            <div class="item">
                <textarea name="" class="ipt ipt-mul ipt-row" id="msg" cols="30" rows="10" placeholder="要跟我说些什么"></textarea>
                <span class="error"></span>
            </div>
        </div>

        <div class="button">
            <button type="button" class="ubtn ubtn-blue" id="submit" >提交</button>
        </div>
    </form>
</section>

<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>
<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script>
    $(function() {
        var $mobile = $('#mobile'),
                $msg = $('#msg');

        function checkMobile() {
            var val = $mobile.val();
            if (val && !/^1[3-9]\d{9}$/.test(val)) {
                $mobile.next().html('手机号码格式错误').show();
            } else {
                $mobile.next().html('').hide();
                return true;
            }
            $mobile.focus();
            return false;
        }
        function checkMessage() {
            var val = $msg.val()
            if (!val) {
                $msg.next().html('什么都没说呀~').show();
            } else {
                $msg.next().html('').hide();
                return true;
            }
            $msg.focus();
            return false;
        }
        function checkIpt() {
            if (checkMobile() && checkMessage()) {
                var msg = $("#msg").val();
                $.ajax({
                    "url": "${contextPath}/wechat/report/save",
                    "data": {
                        msg:msg
                    },
                    "dataType": "json",
                    "type": "POST",
                    success: function (result) {
                        if (result.status==0) {
                            layer.open({
                                content: '<i class="ico ico-right2"></i><br /><br />反馈成功'
                                ,btn: '确定'
                                ,yes: function(index, layero){
                                    window.location.href = "${contextPath}/wechat/user/index";
                                }
                            });
                        }else {
                            layer.open({
                                content: '<i class="ico ico-right2"></i><br /><br />反馈失败'
                                ,btn: '确定'
                            });
                        }
                    }
                });
                return true;
            }
            return false;
        }
        // 保存
        $('#submit').on('click', function() {
            return checkIpt();
        });

        $('body').on('click', function() {
            $('.error').html('').hide();
        });

    });

</script>

</body>
</html>