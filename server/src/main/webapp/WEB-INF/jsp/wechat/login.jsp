<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>登录-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <%--<link rel="icon" href="${contextPath}/wechat-html/favicon.ico">--%>

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body class="bg-login">


<section class="wrap login-box">
    <div class="avatar">
        <img src="${contextPath}/wechat-html/images/avatar.png" alt="">
    </div>

    <form id="formId" action="">
        <div class="form">
            <div class="item">
                <img src="${contextPath}/wechat-html/images/a/user.png" />
                <input type="text" class="ipt" name="username" id="username" />
                <span class="error"></span>
            </div>

            <div class="item">
                <img src="${contextPath}/wechat-html/images/a/lock.png" />
                <input type="password" class="ipt" name="password" id="pwd" />
                <span class="error"></span>
            </div>

            <div class="item cf">
                <a href="javascript:;" class="fr">忘记密码？</a>
                <a href="javascript:;" onclick="toRegister1()">注册</a>
            </div>

        </div>

        <div class="button">
            <button type="button" class="ubtn ubtn-white" id="submit">登录</button>
        </div>
    </form>

</section>

<%@ include file="../inc/new2/foot.jsp" %>
<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script>
    $(function() {
        var $username = $('#username'),
                $pwd = $('#pwd');

        function checkUsername() {
            var val = $username.val();
            if (!val) {
                $username.next().html('请输入会员名').show();
            } else {
                $username.next().html('').hide();
                return true;
            }
            $username.focus();
            return false;
        }
        function checkPwd() {
            var val = $pwd.val();
            if (!val) {
                $pwd.next().html('请输入密码').show();
            } else {
                $pwd.next().html('').hide();
                return true;
            }
            $pwd.focus();
            return false;
        }
        function checkIpt() {
            if (checkUsername() && checkPwd()) {
                return true;
            }
            return false;
        }
        // 保存
        $('#submit').on('click', function() {
            var flag = checkIpt();
            if(flag){
                $("#formId").ajaxSubmit({
                    url : "${contextPath}/wechat/login/check",
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            location.href = "${contextPath}/wechat/index";
                        }else {
                            alert(result.msg);
                        }
                    }
                });
            }
        });

        $('body').on('click', function() {
            $('.error').html('').hide();
        })
    });

    function toRegister1(){
        location.href = "${contextPath}/wechat/register1";
    }

</script>

</body>
</html>