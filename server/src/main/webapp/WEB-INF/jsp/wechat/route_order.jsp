<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>预定座位-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${contextPath}/wechat-html/favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body>

<header>
    <div class="title">班车线路</div>
</header>


<section class="wrap seat-box">
    <div class="ui-hd">
        <i class="ico ico-clock"></i>发车时间
    </div>
    <div class="ui-form">
        <form id="formId">
            <div class="form">
                <div class="item">

                </div>
            </div>
            <div class="button">
                <button type="button" class="ubtn ubtn-blue" id="submit">预定座位</button>
            </div>
        </form>
    </div>
</section>



<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>
<%@ include file="../inc/new2/foot.jsp" %>
<script>
    $(function() {
        $('#submit').on('click', function() {

            alertConfirm('确定要预定此班次吗？',function(){
                submitOrder();
            });
            return false;
        })

        //初始化时间列表
        initTimes();

    })

    function initTimes(){
        $.post("${contextPath}/wechat/route/times",{'routeId':"${routeId}"},function(res){
            var timeList = res.data.object.timeList;
            $(".item").empty();
            for(var i=0; i<timeList.length;i++){
                $(".item").append('<label><input type="radio" name="time" class="rdo">'+timeList[i].departTime+'</label>');
            }

            $("input[name=time]").eq(0).attr("checked",true);
        });
    }

    //提交订单
    function submitOrder(){
        var departTime = $("input[name=time]:checked").parent("label").text();
        if(departTime == ''){
            alertMsg("请选择发车时间");
            return ;
        }
        $.post("${contextPath}/wechat/route/saveOrder",{'routeId':"${routeId}",'departTime':departTime},function(res){
            if(res.status == 0){
                alertMsg("预定成功",function(){
                    var routeOrderId = res.data.routeOrder.id;
                    location.href = "${contextPath}/wechat/order/myRoute/detail?status=0&id="+routeOrderId;
                });
            }else{
                alertMsg("预定失败");
            }
        });
    }

</script>

</body>
</html>