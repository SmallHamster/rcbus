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


<section class="wrap order-box">
    <div class="box-info">
        <div class="hd">
            <em>待付款</em>
            <span>如有问题可与客服联系</span>
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
        <dl>
            <dt>报价详情：</dt>
            <dd class="extra">
                <div class="price">
                    <em id="priceTotal">&yen; ${CarRental.totalAmount}</em>
                    <input type="hidden" value="${CarRental.totalAmount}" id="price">
                </div>
                <div class="select" id="coupons">
                    <div class="hd">请选择优惠券</div>
                    <div class="bd">

                        <div class="option" data-value="0" data-operator="-">请选择优惠券</div>
                        <c:forEach items="${coupon}" var="v">

                            <%-- 折扣 --%>
                            <c:if test="${v.couponWay eq 1}">
                                <%-- 有限制金额的 --%>
                                <c:if test="${v.isLimit eq 1 && CarRental.totalAmount >= v.limitMoney}">
                                    <%-- 最高立减金额不为空 --%>
                                    <c:if test="${v.discountTopMoney ne null}">
                                        <%-- 减免金额小于最高金额 --%>
                                        <c:if test="${CarRental.totalAmount * (1-v.discountPercent) <= v.discountTopMoney}">
                                            <div class="option" data-value="${v.discountPercent}" data-operator="*">${v.name}<input type="hidden" name="couponId" value="${v.id}"/></div>
                                        </c:if>
                                        <%-- 减免金额大于最高金额 --%>
                                        <c:if test="${CarRental.totalAmount * (1-v.discountPercent) > v.discountTopMoney}">
                                            <div class="option" data-value="${v.discountTopMoney}" data-operator="-">${v.name}(最多减免${v.discountTopMoney})<input type="hidden" name="couponId" value="${v.id}"/></div>
                                        </c:if>
                                    </c:if>

                                    <%-- 最高立减金额为空 --%>
                                    <c:if test="${v.discountTopMoney eq null}">
                                        <div class="option" data-value="${v.discountPercent}" data-operator="*">${v.name}<input type="hidden" name="couponId" value="${v.id}"/></div>
                                    </c:if>
                                </c:if>

                                <%-- 没限制金额的 --%>
                                <c:if test="${v.isLimit eq 0}">
                                    <%-- 最高立减金额不为空 --%>
                                    <c:if test="${v.discountTopMoney ne null}">
                                        <%-- 减免金额小于最高金额 --%>
                                        <c:if test="${CarRental.totalAmount * (1-v.discountPercent) <= v.discountTopMoney}">
                                            <div class="option" data-value="${v.discountPercent}" data-operator="*">${v.name}<input type="hidden" name="couponId" value="${v.id}"/></div>
                                        </c:if>
                                        <%-- 减免金额大于最高金额 --%>
                                        <c:if test="${CarRental.totalAmount * (1-v.discountPercent) > v.discountTopMoney}">
                                            <div class="option" data-value="${v.discountTopMoney}" data-operator="-">${v.name}(最多减免${v.discountTopMoney})<input type="hidden" name="couponId" value="${v.id}"/></div>
                                        </c:if>
                                    </c:if>

                                    <%-- 最高立减金额为空 --%>
                                    <c:if test="${v.discountTopMoney eq null}">
                                        <div class="option" data-value="${v.discountPercent}" data-operator="*">${v.name}<input type="hidden" name="couponId" value="${v.id}"/></div>
                                    </c:if>
                                </c:if>
                            </c:if>

                            <%-- 减免金额 --%>
                            <c:if test="${v.couponWay eq 2}">
                                <%-- 有限制金额的 --%>
                                <c:if test="${v.isLimit eq 1 && CarRental.totalAmount >= v.limitMoney}">
                                    <div class="option" data-value="${v.reduceMoney}" data-operator="-">${v.name}<input type="hidden" name="couponId" value="${v.id}"/></div>
                                </c:if>
                                <%-- 没限制金额的 --%>
                                <c:if test="${v.isLimit eq 0}">
                                    <div class="option" data-value="${v.reduceMoney}" data-operator="-">${v.name}<input type="hidden" name="couponId" value="${v.id}"/></div>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <div class="cost">
                    <c:forEach var="v" items="${carRentalOffer}">
                        <div class="item">
                            <span>${v.name}</span>
                            <em>&yen; ${v.amount}</em>
                        </div>
                    </c:forEach>
                    <div class="item blue" id="couponsUsed">
                        <span>未选择优惠券</span>
                        <em>&yen; 0</em>
                    </div>
                </div>
            </dd>
        </dl>

        <div class="ft"></div>
        <div class="state state2"></div>
    </div>

    <div class="button">
        <a href="${contextPath}/wechat-html/oldFile/disclaimer.html">免责申明</a>
        <a onclick="pay()" class="ubtn ubtn-blue">微信支付（<em id="priceTotal2">${CarRental.totalAmount}</em>元）</a>
    </div>
</section>

<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>
<script>
    $(function() {
        var price = parseFloat($('#price').val()); // 价格

        $('#coupons').on('click', function(e) {
            $(this).addClass('select-on');
            e.stopPropagation();
        });

        $('#coupons').on('click', '.option', function(e) {
            var val = parseFloat($(this).data('value')),
                    operator = $(this).data('operator'),
                    total = 0,
                    couponsInfo = '',
                    $this = $(this);

            if (operator === '*') {
                total = price * val;
                couponsInfo = '<span>' + $this.html() + '</span><em>- &yen; ' + (price - total).toFixed(1) + '</em>';

            } else if (operator === '-') {
                total = price - val;
                couponsInfo = '<span>' + $this.html().replace('请', '未') + '</span><em>- &yen; ' + val + '</em>';
            }

            // $('#priceTotal, #priceTotal2').html(total.toFixed(1));
            $('#priceTotal2').html(total.toFixed(1));
            $('#couponsUsed').html(couponsInfo);
            $('#coupons').find('.hd').html($this.html());

            $(this).addClass('hover');
            setTimeout(function() {
                $this.removeClass('hover');
                $('#coupons').removeClass('select-on');
            }, 100);

            e.stopPropagation();

        });

        $('body').on('click', function() {
            $('#coupons').removeClass('select-on');
        })
    })


    function pay (){
        var text = $("#coupons .hd").text();
        $("#coupons .bd div").each(function(){
            if($(this).text() == text){
                //获取优惠券的ID 使用后改变状态
                console.log($(this).find("input").val())
            }
        });
    }
</script>

</body>
</html>