<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>江城巴士</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/js/jquery-1.11.0.js"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/weixin/inviteCode/css/style.css"/>
    <script type="text/javascript">
        (function () {
            var deviceWidth = document.documentElement.clientWidth;
            if (deviceWidth > 750) deviceWidth = 750;
            document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';
        })()
    </script>
</head>
<body>
<div class="top-tittle">企业邀请码</div>
<div class="text1">请输入企业邀请码成为企业员工</div>
<input type="text" class="input-style" id="inviteCode" placeholder='请输入邀请码' maxlength="6"/>
<div class="text2">输入成功后，您将成为企业员工，可<br>以享受对应通勤班车了！</div>
<div class="btn-ok">确认</div>
</body>
</html>

<script type="text/javascript">
    function sendInfo() {
        var inviteCode = $('#inviteCode').val();

        if (null == inviteCode || inviteCode == '') {
            alert('请输入邀请码');
        } else {
            $.ajax({
                dataType: 'json',
                url: '${contextPath}/wechat/inviteCode',
                data: {
                    inviteCode: inviteCode
                },
                success: function (data) {
                    if (data == 1) {
                        window.location.href = '${contextPath}/wechat/route/index/0';
                    } else {
                        alert('邀请码错误');
                    }
                }
            });
        }
    }
</script>