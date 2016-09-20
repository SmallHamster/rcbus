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
                <em><date:date value="${CarRental.startDate}" format="yyyy-MM-dd hh:mm"></date:date></em>
                <em><date:date value="${CarRental.endDate}" format="yyyy-MM-dd hh:mm"></date:date></em>
            </div>
            <div class="bus">
                <b>宝骏730（豪华商务七座）</b>
                <span>鄂TF0809</span>
                <i>|</i>
                <span>鄂TF0809</span>
                <i>|</i>
                <span>鄂TF0809</span>
                <i>|</i>
                <span>鄂TF0809</span>
            </div>
            <div class="button">
                <a href="${contextPath}/wechat-html/oldFile/order_modify_date.html" class="ubtn ubtn-ghost">我要改期</a>
                <a href="${contextPath}/wechat-html/oldFile/order_cancel.html" class="ubtn ubtn-blue">我要退订</a>
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
                            <span>${coupon.name}</span>
                        <c:if test="${coupon.couponWay eq 1}">
                            <c:if test="${coupon.isLimit eq 1 && CarRental.totalAmount >= coupon.limitMoney}">
                                <%-- 最高立减金额不为空 --%>
                                <c:if test="${coupon.discountTopMoney ne null}">
                                    <%-- 减免金额小于最高金额 --%>
                                    <c:if test="${CarRental.totalAmount * (1-coupon.discountPercent) <= coupon.discountTopMoney}">
                                        <em>- &yen; ${CarRental.totalAmount * (1-coupon.discountPercent)}</em>
                                    </c:if>
                                    <%-- 减免金额大于最高金额 --%>
                                    <c:if test="${CarRental.totalAmount * (1-coupon.discountPercent) > coupon.discountTopMoney}">
                                        <em>- &yen; ${coupon.discountTopMoney}</em>
                                    </c:if>
                                </c:if>
                                <%-- 最高立减金额为空 --%>
                                <c:if test="${coupon.discountTopMoney eq null}">
                                    <em>- &yen; ${coupon.discountPercent}</em>
                                </c:if>
                            </c:if>
                        </c:if>

                        <c:if test="${coupon.couponWay eq 2}">
                            <em>- &yen; ${coupon.reduceMoney}</em>
                        </c:if>
                    </div>
                </div>
            </dd>
        </dl>

        <div class="ft"></div>
        <div class="state state4"></div>
    </div>

    <div class="button">
        <a href="${contextPath}/wechat-html/oldFile/disclaimer.html">免责申明</a>
        <a href="#" class="ubtn ubtn-blue">确认完成</a>
    </div>
</section>

<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>


<script>
    $(function() {

    })

</script>
</body>
</html>