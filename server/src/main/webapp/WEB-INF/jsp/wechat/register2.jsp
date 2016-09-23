<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>设置密码-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${contextPath}/wechat-html/favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body class="bg-reg">

<header>
    <div class="title">设置密码</div>
</header>

<section class="wrap reg-box">
    <div class="form form-extal">

        <form id="formId">
            <input type="hidden" name="mobile" value="${mobile}">
            <input type="hidden" name="type" value="${type}">
            <div class="item">
                <input type="password" class="ipt" value="" name="password" id="newpwd" placeholder="请输入新密码" />
                <span class="error"></span>
            </div>

            <div class="item">
                <input type="password" class="ipt" name="" id="reppwd" placeholder="请输入确认密码" />
                <span class="error"></span>
            </div>

            <div class="button">
                <button type="button" class="ubtn ubtn-blue" id="submit">确定</button>
            </div>
        </form>

    </div>
</section>


<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<%@ include file="../inc/new2/foot.jsp" %>
<script>
    $(function() {
        var $newpwd = $('#newpwd'),
                $reppwd = $('#reppwd');
        function checkNewpwd() {
            var val = $newpwd.val();
            if (!val) {
                $newpwd.next().html('请输入新密码').show();
            } else {
                $newpwd.next().html('').hide();
                return true;
            }
            $newpwd.focus();
            return false;
        }
        function checkReppwd() {
            var val = $reppwd.val();
            if (!val) {
                $reppwd.next().html('请输入确认密码').show();
            } else if (val !== $newpwd.val()) {
                $reppwd.next().html('两次密码不一致').show();
            } else {
                $reppwd.next().html('').hide();
                return true;
            }
            $reppwd.focus();
            return false;
        }
        function checkIpt() {
            if (checkNewpwd() && checkReppwd()) {
                return true;
            }
            return false;
        }
        // 保存
        $('#submit').on('click', function() {
            var flag = checkIpt();
            if(flag){
                $("#formId").ajaxSubmit({
                    url : "${contextPath}/wechat/register",
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            location.href = "${contextPath}/wechat/index";
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


    });

</script>

</body>
</html>