<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>租车旅游报名入口-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
    <link rel="stylesheet" href="${contextPath}/wechat-html/css/mobiscroll.animation.css" />
    <link rel="stylesheet" href="${contextPath}/wechat-html/css/mobiscroll.frame.css" />
    <link rel="stylesheet" href="${contextPath}/wechat-html/css/mobiscroll.frame.ios.css" />
</head>

<body>

<header>
    <div class="title">租车旅游</div>
</header>


<section class="wrap ui-form">
    <h1 class="title">${travelName}活动报名</h1>
    <div class="sub-title">请留下您的联系方式，我们将尽快与您取得联系</div>

    <form id="car_travel">
        <div class="form">
            <input type="hidden" id="travelName" name="travelName" value="${travelName}">
            <div class="item">
                <label class="for">联系人</label>
                <input type="text" class="ipt" id="userName" name="userName" value="" />
            </div>

            <div class="item">
                <label class="for">联系方式</label>
                <input type="text" class="ipt" id="mobile" name="mobile" value="" />
            </div>

            <div class="item">
                <label class="for">报名人数</label>
                <input type="text" class="ipt" id="num" name="num" value="" />
            </div>

            <div class="item">
                <label class="for">出行时间</label>
                <input type="text" class="ipt" id="travelTime" name="travelTime" value="" />
            </div>

            <div class="item">
                <label class="for">其他备注</label>
                <textarea name="" id="content" class="ipt ipt-mul" cols="30" rows="10"></textarea>
            </div>
        </div>

        <div class="button">
            <button type="button" class="ubtn ubtn-blue" id="submit" onclick="save()">确认</button>
        </div>
    </form>
</section>

<%@ include file="../inc/new2/foot.jsp" %>
<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/app.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.dom.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.core.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.scrollview.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.frame.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.scroller.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.frame.ios.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.i18n.zh.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>
<script>

    function save(){
        var content = $("#content");
        $("#car_travel").ajaxSubmit({
            url : "${contextPath}/wechat/cartravel/save",
            data : {
                content : content
            },
            type : "POST",
            success : function(result) {
                if(result.status == 0) {
                    window.location.href = "${contextPath}/admin/user/index";
                }
                else {
                    alert("操作失败");
                }
            }
        })

    }


</script>

</body>
</html>