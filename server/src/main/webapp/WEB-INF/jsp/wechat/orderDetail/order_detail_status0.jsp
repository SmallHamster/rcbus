<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>订单详情-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat/css/app.css">
</head>

<body>

<header>
    <div class="title">订单详情</div>
</header>


<section class="wrap order-box">
    <div class="box-info">
        <div class="hd">
            <em>审核中</em>
            <span>审核完成后，客服将尽快与您取得联系</span>
        </div>
        <div class="fromto">
            <em>${CarRental.startPoint}</em>
            <i></i>
            <em>${CarRental.endPoint}</em>
        </div>
        <dl>
            <dt>出行信息：</dt>
            <dd>
                <span>联系人</span>
                <em>${CarRental.order.userName}</em>
            </dd>
            <dd>
                <span>联系方式</span>
                <em>${CarRental.order.mobile}</em>
            </dd>
            <dd>
                <span>出行类型</span>
                <em><c:if test="${CarRental.rentalWay eq 0}">单程</c:if><c:if test="${CarRental.rentalWay eq 1}">往返</c:if><c:if test="${CarRental.rentalWay eq 2}">全天</c:if></em>
            </dd>
            <dd>
                <span>发车时间</span>
                <em><date:date value="${CarRental.startDate}" format="yyyy-MM-dd hh:mm"></date:date></em>
            </dd>
            <dd>
                <span>返程时间</span>
                <em><date:date value="${CarRental.endDate}" format="yyyy-MM-dd hh:mm"></date:date></em>
            </dd>
            <dd>
                <span>乘车人数</span>
                <em>${CarRental.totalNumber}</em>
            </dd>
            <dd>
                <span>需要车辆</span>
                <em>${CarRental.busNum}</em>
            </dd>
            <dd>
                <span>需要发票</span>
                <c:if test="${CarRental.isInvoice eq 1}"><em>是（${CarRental.invoice}）</em></c:if>
                <c:if test="${CarRental.isInvoice eq 0}">否</c:if>
            </dd>
        </dl>
        <dl>
            <dt>订单信息：</dt>
            <dd>
                <span>订单编号</span>
                <em>${CarRental.order.orderNo}</em>
            </dd>
            <dd>
                <span>提交时间</span>
                <em><date:date value="${CarRental.createDate}" format="yyyy-MM-dd hh:mm"></date:date></em>
            </dd>
        </dl>

        <div class="ft"></div>
        <div class="state state3"></div>
    </div>

    <div class="button">
        <a href="${contextPath}/wechat/oldFile/disclaimer.html">免责申明</a>
        <a href="#" class="ubtn ubtn-ghost">修改信息</a>
        <button type="button" class="ubtn ubtn-red" id="cancel">取消申请</button>
    </div>
</section>

<script src="${contextPath}/wechat/js/zepto.min.js"></script>
<script src="${contextPath}/wechat/js/layer/layer.js"></script>
<script>
    $(function() {
        $('#cancel').on('click', function() {
            layer.open({
                content: '您的订单正在审核中，您<br />确定要取消吗？'
                ,btn: ['取消', '确定']
                ,yes: function(index) {
                    layer.close(index);
                }
                ,no: function(index){
                    layer.open({
                        content: '<i class="ico ico-right2"></i><br /><br />取消成功！'
                        ,btn: '确定'
                    });
                }
            });

            return false;
        })


        $('body').on('touchstart', '.layui-m-layershade', function(e) {
            e.preventDefault();
        })
    })

</script>

</body>
</html>