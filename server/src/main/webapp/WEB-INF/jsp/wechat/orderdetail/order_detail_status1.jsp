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
                <div class="select" id="coupons">
                    <div class="hd">请选择优惠券</div>
                    <div class="bd">

                        <div class="option" data-value="0" data-operator="-">请选择优惠券</div>
                        <c:forEach items="${userCoupon}" var="v">

                            <%-- 折扣 --%>
                            <c:if test="${v.coupon.couponWay eq 1}">
                                <%-- 有限制金额的 --%>
                                <c:if test="${v.coupon.isLimit eq 1 && CarRental.totalAmount >= v.coupon.limitMoney}">
                                    <%-- 最高立减金额不为空 --%>
                                    <c:if test="${v.coupon.discountTopMoney ne null}">
                                        <%-- 减免金额小于最高金额 --%>
                                        <c:if test="${CarRental.totalAmount * (1-v.coupon.discountPercent) <= v.coupon.discountTopMoney}">
                                            <div class="option" data-value="${v.coupon.discountPercent}" data-operator="*">${v.coupon.name}<input type="hidden" name="couponId" value="${v.coupon.id}"/></div>
                                        </c:if>
                                        <%-- 减免金额大于最高金额 --%>
                                        <c:if test="${CarRental.totalAmount * (1-v.coupon.discountPercent) > v.coupon.discountTopMoney}">
                                            <div class="option" data-value="${v.coupon.discountTopMoney}" data-operator="-">${v.coupon.name}(最多减免${v.coupon.discountTopMoney})<input type="hidden" name="couponId" value="${v.coupon.id}"/></div>
                                        </c:if>
                                    </c:if>

                                    <%-- 最高立减金额为空 --%>
                                    <c:if test="${v.coupon.discountTopMoney eq null}">
                                        <div class="option" data-value="${v.coupon.discountPercent}" data-operator="*">${v.coupon.name}<input type="hidden" name="couponId" value="${v.coupon.id}"/></div>
                                    </c:if>
                                </c:if>

                                <%-- 没限制金额的 --%>
                                <c:if test="${v.coupon.isLimit eq 0}">
                                    <%-- 最高立减金额不为空 --%>
                                    <c:if test="${v.coupon.discountTopMoney ne null}">
                                        <%-- 减免金额小于最高金额 --%>
                                        <c:if test="${CarRental.totalAmount * (1-v.coupon.discountPercent) <= v.coupon.discountTopMoney}">
                                            <div class="option" data-value="${v.coupon.discountPercent}" data-operator="*">${v.coupon.name}<input type="hidden" name="couponId" value="${v.coupon.id}"/></div>
                                        </c:if>
                                        <%-- 减免金额大于最高金额 --%>
                                        <c:if test="${CarRental.totalAmount * (1-v.coupon.discountPercent) > v.coupon.discountTopMoney}">
                                            <div class="option" data-value="${v.coupon.discountTopMoney}" data-operator="-">${v.coupon.name}(最多减免${v.coupon.discountTopMoney})<input type="hidden" name="couponId" value="${v.coupon.id}"/></div>
                                        </c:if>
                                    </c:if>

                                    <%-- 最高立减金额为空 --%>
                                    <c:if test="${v.coupon.discountTopMoney eq null}">
                                        <div class="option" data-value="${v.coupon.discountPercent}" data-operator="*">${v.coupon.name}<input type="hidden" name="couponId" value="${v.coupon.id}"/></div>
                                    </c:if>
                                </c:if>
                            </c:if>

                            <%-- 减免金额 --%>
                            <c:if test="${v.coupon.couponWay eq 2}">
                                <%-- 有限制金额的 --%>
                                <c:if test="${v.coupon.isLimit eq 1 && CarRental.totalAmount >= v.coupon.limitMoney}">
                                    <div class="option" data-value="${v.coupon.reduceMoney}" data-operator="-">${v.coupon.name}<input type="hidden" name="couponId" value="${v.coupon.id}"/></div>
                                </c:if>
                                <%-- 没限制金额的 --%>
                                <c:if test="${v.coupon.isLimit eq 0}">
                                    <div class="option" data-value="${v.coupon.reduceMoney}" data-operator="-">${v.coupon.name}<input type="hidden" name="couponId" value="${v.coupon.id}"/></div>
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
        <input type="hidden" id="id" value="${CarRental.id}">

        <div class="ft"></div>
        <div class="state state2"></div>
    </div>

    <div class="button">
        <a onclick="toAgree()">免责申明</a>
        <a onclick="pay()" class="ubtn ubtn-blue">微信支付（<em id="priceTotal2">${CarRental.totalAmount}</em>元）</a>
    </div>
</section>

<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>
<script>

    //服务协议
    function toAgree(){
        location.href = "${contextPath}/wechat/agreement";
    }

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
                couponsInfo = '<span>' + $this.html() + '</span><em>- &yen; ' + (price - total).toFixed(2) + '</em>';

            } else if (operator === '-') {
                total = price - val;
                couponsInfo = '<span>' + $this.html().replace('请', '未') + '</span><em>- &yen; ' + val + '</em>';
            }

            // $('#priceTotal, #priceTotal2').html(total.toFixed(1));
            $('#priceTotal2').html(total > 0.00 ? total.toFixed(2) : 0.00);
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
    });


    function pay (){
        var text = $("#coupons .hd").text();
        var couponId = 0;
        $("#coupons .bd div").each(function(){
            if($(this).text() == text){
                //获取优惠券的ID 使用后改变状态
                couponId = $(this).find("input").val();
            }
        });

        var id = $("#id").val();
        var price = $("#priceTotal2").text();

        // 调用微信浏览器内置功能实现微信支付
        $.ajax({
            method: "POST",
            url: "${contextPath}/wechat/pay/goPay",
            dataType: "html",
            data: {
                rentalId : id,
                price :price
            },
            success: function (result) {
                var obj = eval('(' + result + ')');
                WeixinJSBridge.invoke('getBrandWCPayRequest', {
                    "appId": obj.appId,                  //公众号名称，由商户传入
                    "timeStamp": obj.timeStamp,          //时间戳，自 1970 年以来的秒数
                    "nonceStr": obj.nonceStr,         //随机串
                    "package": obj.package,           //商品包信息
                    "signType": obj.signType,        //微信签名方式:
                    "paySign": obj.paySign           //微信签名
                },function (res) {
                    //支付成功
                    if (res.err_msg == "get_brand_wcpay_request:ok") {

                        $.ajax({
                            "url": "${contextPath}/wechat/order/pay/save",
                            "data": {
                                id:id,
                                price:price,
                                couponId : couponId
                            },
                            "dataType": "json",
                            "type": "POST",
                            success: function (result) {
                                if (result.status==0) {
                                    layer.open({
                                        content: '<i class="ico ico-right2"></i><br /><br />付款完成'
                                        ,btn: '确定'
                                        ,yes: function(index, layero){
                                            window.location.href = "${contextPath}/wechat/order/myOrder/index";
                                        }
                                    });
                                }
                            }
                        });


                    }
                    //支付过程中用户取消
                    if(res.err_msg == "get_brand_wcpay_request：cancel"){

                    }
                    //支付失败
                    if(res.err_msg == "get_brand_wcpay_request：fail"){
                        layer.open({
                            content: '<i class="ico ico-right2"></i><br /><br />支付失败,请联系客服'
                            ,btn: '确定'
                        });
                    }

                });
            }
        });

        return false;
    }
</script>

</body>
</html>