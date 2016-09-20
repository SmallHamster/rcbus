<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>注册-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${contextPath}/wechat-html/favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body class="bg-reg">

<header>
    <div class="title">注册</div>
</header>

<section class="wrap reg-box">
    <form id="formId" action="">
        <div class="form">
            <div class="item">
                <label class="for">手机号</label>
                <input type="text" class="ipt" value="" name="mobile" id="mobile" placeholder="请输入手机号" />
                <span class="error"></span>
            </div>

            <div class="item">
                <label class="for">验证码</label>
                <input type="text" class="ipt" name="code" id="code" placeholder="请输入验证码" maxlength="6" />
                <span class="error"></span>
                <span class="btn-wrap"><button type="button" class="btn-code" id="getcode">获取验证码</button></span>
            </div>

            <div class="item">
                <label><input type="checkbox" checked class="cbx">我已阅读并同意江城巴士的<a href="javascript:;" onclick="toAgree()" class="agreement" target="_blank">服务条款</a></label>
            </div>
        </div>

        <div class="button">
            <button type="button" class="ubtn ubtn-blue" id="submit">下一步</button>
        </div>
    </form>
</section>

<%@ include file="../inc/new2/foot.jsp" %>
<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/app.js"></script>
<script>
    $(function() {
        var $mobile = $('#mobile'),
                $code = $('#code');

        function checkMobile() {
            var val = $mobile.val();
            if (!val) {
                $mobile.next().html('请输入手机号').show();
            } else if (!/^1[3-9]\d{9}$/.test(val)) {
                $mobile.next().html('手机号码格式错误').show();
            } else {
                $mobile.next().html('').hide();
                return true;
            }
            $mobile.focus();
            return false;
        }
        function checkCode() {
            var val = $code.val();
            if (!val) {
                $code.next().html('请输入验证码').show();
            } else {
                $code.next().html('').hide();
                return true;
            }
            $code.focus();
            return false;
        }
        function checkCbx(){
            $cbx = $("input[type=checkbox]");
            if(!$cbx.is(':checked')){
                alert("请勾选同意服务条款");
            }else {
                return true;
            }
            $cbx.focus();
            return false;
        }
        function checkIpt() {
            if (checkMobile() && checkCode() && checkCbx()) {
                return true;
            }
            return false;
        }
        // 下一步
        $('#submit').on('click', function() {
            var flag = checkIpt();
            if(flag){
                $("#formId").ajaxSubmit({
                    url : "${contextPath}/wechat/check/code",
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            location.href = "${contextPath}/wechat/register2?mobile="+$("#mobile").val();
                        }else {
                            alert(result.msg);
                        }
                    }
                });
            }else{
                return flag;
            }
        });

        $('body').on('click', function() {
            $('.error').html('').hide();
        });

        $mobile.on('input', function() {
            $(this).next().html('').hide();
        });

        var $getcode = $("#getcode");

        // 短信验证码
        var sendSMS = {
            timer: 0,
            delay: false, //间隔时间，单位：秒
            text: '秒重新获取',
            send: function(time) {
                var self = this;
                if (self.delay) {
                    return;
                }
                $.ajax({
                    url: '',
                    // dataType: 'json',
                    data: 'phone=' + $mobile.val(),
                    // type: 'post',
                    success: function(data) {
                        data = {
                            status: 'success'
                        }
                        if (data.status === 'success') {
                            self.lock(time);
                            alertMsg(data.message);
                        }else{
                            alertMsg(data.message);
                        }
                    },
                    error: function() {
                        alertMsg('网络连接超时，请您稍后重试!');
                    }
                })
            },
            lock: function(time) {
                var self = this;
                self.delay = true;
                self.timer && clearInterval(self.timer);

                $getcode.text(time + self.text).prop('disabled', true);

                self.timer = setInterval(function() {
                    $getcode.text(--time + self.text).prop('disabled', true);
                    if (time <= 0) {
                        clearInterval(self.timer);
                        $getcode.text("获取验证码").prop('disabled', false);
                        self.delay = false;
                    }
                }, 1e3);
            }
        }

        //获取验证码
        $getcode.on("click", function(e) {
            e.stopPropagation();
            var flag = checkMobile() && sendSMS.send(60);
            if(flag){
                $.post("${contextPath}/wechat/sms/code",{'mobile':$("#mobile").val()},function(res){
                    if(res.status != 0){
                        alert(res.msg);
                    }
                })
            }else{
                return flag;
            }
        })
    });

    function toAgree(){
        location.href = "${contextPath}/wechat/agreement";
    }

</script>

</body>
</html>