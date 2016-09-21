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

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body>

<header>
    <div class="title">订单详情</div>
</header>

<div class="share-guide"></div>

<section class="wrap order-box">
    <div class="box-info">
        <div class="hd">
            <em>进行中</em>
            <span>如有问题可与客服联系</span>
        </div>
        <div class="detail">
            <div class="fromto">
                <em>${CarRental.startPoint}</em>
                <i class="goback"></i>
                <em>${CarRental.endPoint}</em>
            </div>
            <div class="date">
                <em>出发时间</em>
                <em>返回时间</em>
            </div>
            <div class="time">
                <em><date:date value="${CarRental.startDate}" format="yyyy-MM-dd HH:mm"></date:date></em>
                <em><date:date value="${CarRental.endDate}" format="yyyy-MM-dd HH:mm"></date:date></em>
            </div>
            <div class="bus">
                <b>${modelNo} ${CarRental.carType.name}</b>
                <i>|</i>
                <c:forEach items="${busSend}" var="v">
                    <span>${v.bus.carNo}</span>
                    <i>|</i>
                </c:forEach>
            </div>
            <div class="button">
                <c:if test="${CarRental.isRewrite ne 1 && CarRental.startDate >= toDayDate}">
                    <a onclick="rewrite(${CarRental.id},1)" class="ubtn ubtn-ghost" >我要改期</a>
                </c:if>

                <a onclick="rewrite(${CarRental.id},2)" class="ubtn ubtn-blue">我要退订</a>
                <p><a href="#" class="blue">退改规则</a></p>
                <span>（出行前如有车辆变更会有系统提示）</span>
            </div>
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
                <em><date:date value="${CarRental.startDate}" format="yyyy-MM-dd HH:mm"></date:date></em>
            </dd>
            <dd>
                <span>返程时间</span>
                <em><date:date value="${CarRental.endDate}" format="yyyy-MM-dd HH:mm"></date:date></em>
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
                <em><date:date value="${CarRental.createDate}" format="yyyy-MM-dd HH:mm"></date:date></em>
            </dd>
        </dl>
        <dl>
            <dt>报价详情：</dt>
            <dd class="extra">
                <div class="price">
                    <em id="priceTotal">&yen; ${CarRental.totalAmount}</em>
                    <input type="hidden" value="${CarRental.totalAmount}" id="price">
                </div>
                <div class="cost">
                    <c:forEach var="v" items="${carRentalOffer}">
                        <div class="item">
                            <span>${v.name}</span>
                            <em>&yen; ${v.amount}</em>
                        </div>
                    </c:forEach>
                    <div class="item blue" id="couponsUsed">
                            <span>${myCoupon.name}</span>
                        <c:if test="${myCoupon.couponWay eq 1}">
                            <c:if test="${myCoupon.isLimit eq 1 && CarRental.totalAmount >= myCoupon.limitMoney}">
                                <%-- 最高立减金额不为空 --%>
                                <c:if test="${myCoupon.discountTopMoney ne null}">
                                    <%-- 减免金额小于最高金额 --%>
                                    <c:if test="${CarRental.totalAmount * (1-myCoupon.discountPercent) <= myCoupon.discountTopMoney}">
                                        <em>- &yen; ${CarRental.totalAmount * (1-myCoupon.discountPercent)}</em>
                                        <input type="hidden" value="${CarRental.totalAmount * (1-myCoupon.discountPercent)}" id="discount">
                                    </c:if>
                                    <%-- 减免金额大于最高金额 --%>
                                    <c:if test="${CarRental.totalAmount * (1-myCoupon.discountPercent) > myCoupon.discountTopMoney}">
                                        <em>- &yen; ${myCoupon.discountTopMoney}</em>
                                        <input type="hidden" value=" ${myCoupon.discountTopMoney}" id="discount">
                                    </c:if>
                                </c:if>
                                <%-- 最高立减金额为空 --%>
                                <c:if test="${myCoupon.discountTopMoney eq null}">
                                    <em>- &yen; ${myCoupon.discountPercent}</em>
                                    <input type="hidden" value="${myCoupon.discountPercent}" id="discount">
                                </c:if>
                            </c:if>
                        </c:if>

                        <c:if test="${myCoupon.couponWay eq 2}">
                            <em>- &yen; ${myCoupon.reduceMoney}</em>
                            <input type="hidden" value="${myCoupon.reduceMoney}" id="discount">
                        </c:if>
                    </div>
                </div>
            </dd>
        </dl>
        <input type="hidden" id="id" value="${CarRental.id}">
        <div class="ft"></div>
        <div class="state state4"></div>
    </div>

    <div class="button">
        <a href="${contextPath}/wechat-html/oldFile/disclaimer.html">免责申明</a>
        <a id="submit" class="ubtn ubtn-blue">确认完成</a>
    </div>
</section>

<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>


<script>
    var price = $("#price").val(),
        discount = $("#discount").val(),
        val = price - discount;

    $(function() {
        $("#priceTotal").html("&yen; "+val);
    });

    function rewrite(id,type){
        window.location.href = "${contextPath}/wechat/order/rewrite?id="+id + "&type="+type + "&val=" + val;
    }

    $('#submit').on('click', function() {
        var id = $("#id").val();
        $.ajax({
            "url": "${contextPath}/wechat/order/complete/save",
            "data": {
                id:id,
                val : val
            },
            "dataType": "json",
            "type": "POST",
            success: function (result) {
                if (result.status==0) {
                    layer.open({
                        content: '<i class="ico ico-right2"></i><br /><br />确认订单已完成'
                        ,btn: '确定'
                        ,yes: function(index, layero){
                            window.location.href = "${contextPath}/wechat/order/myOrder/index";
                        }
                    });
                }else {
                    layer.open({
                        content: '<i class="ico ico-right2"></i><br /><br />确认失败'
                        ,btn: '确定'
                    });
                }
            }
        });
        return false;
    })


</script>
</body>
</html>