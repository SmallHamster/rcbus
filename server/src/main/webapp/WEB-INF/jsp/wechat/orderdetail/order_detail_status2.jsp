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
                <c:if test="${CarRental.isRewrite ne 1 && (CarRental.startDate - (60*60*24*1000)) >= toDayDate}">
                    <a onclick="rewrite(${CarRental.id},1)" class="ubtn ubtn-ghost" >我要改期</a>
                </c:if>
                <a onclick="rewrite(${CarRental.id},2)" class="ubtn ubtn-blue">我要退订</a>
                <p><a href="javascript:;" id="rule" class="blue">退改规则</a></p>
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
            <c:if test="${CarRental.endDate ne null}">
                <dd>
                    <span>返程时间</span>
                    <em><date:date value="${CarRental.endDate}" format="yyyy-MM-dd HH:mm"></date:date></em>
                </dd>
            </c:if>
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
                    <em id="priceTotal">&yen; ${CarRental.income}</em>
                    <input type="hidden" value="${CarRental.income}" id="price">
                </div>
                <div class="cost">
                    <c:forEach var="v" items="${carRentalOffer}">
                        <div class="item">
                            <span>${v.name}</span>
                            <em>&yen; ${v.amount}</em>
                        </div>
                    </c:forEach>
                    <div class="item blue" id="couponsUsed">
                        <span>${myCoupon.coupon.name}</span>
                        <c:if test="${myCoupon.coupon.couponWay eq 1}">
                            <%-- 最高立减金额不为空 --%>
                            <c:if test="${myCoupon.coupon.discountTopMoney ne null}">
                                <%-- 减免金额小于最高金额 --%>
                                <c:if test="${CarRental.totalAmount * (1-myCoupon.coupon.discountPercent) <= myCoupon.coupon.discountTopMoney}">
                                    <em>- &yen; ${CarRental.totalAmount * (1-myCoupon.coupon.discountPercent)}</em>
                                    <input type="hidden" value="${CarRental.totalAmount * (1-myCoupon.coupon.discountPercent)}" id="discount">
                                </c:if>
                                <%-- 减免金额大于最高金额 --%>
                                <c:if test="${CarRental.totalAmount * (1-myCoupon.coupon.discountPercent) > myCoupon.coupon.discountTopMoney}">
                                    <em>- &yen; ${myCoupon.coupon.discountTopMoney}</em>
                                    <input type="hidden" value=" ${myCoupon.coupon.discountTopMoney}" id="discount">
                                </c:if>
                            </c:if>

                            <%-- 最高立减金额为空 --%>
                            <c:if test="${myCoupon.coupon.discountTopMoney eq null}">
                                <em>- &yen; ${myCoupon.coupon.discountPercent}</em>
                                <input type="hidden" value="${myCoupon.coupon.discountPercent}" id="discount">
                            </c:if>

                        </c:if>

                        <c:if test="${myCoupon.coupon.couponWay eq 2}">
                            <em>- &yen; ${myCoupon.coupon.reduceMoney}</em>
                            <input type="hidden" value="${myCoupon.coupon.reduceMoney}" id="discount">
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
        <a onclick="toAgree()">免责申明</a>
        <a id="submit" class="ubtn ubtn-blue">确认完成</a>
    </div>
</section>

<div id="rulebox" class="hide">
    <div class="rule">
        <dl>
            <dt>预订车型</dt>
            <dd>${modelNo} (${CarRental.carType.name})</dd>
            <dt>行程总价</dt>
            <dd><fmt:formatNumber value="${CarRental.income}" type="currency" pattern="#0.00"/>元(最终价)</dd>
            <dt>取消订单</dt>
            <dd>
                <p>起点发车时间前≥48小时，全额退(<fmt:formatNumber value="${CarRental.income}" type="currency" pattern="#0.00"/>元)</p>
                <p>起点发车时间前≥24小时，全额退90%(<fmt:formatNumber value="${CarRental.income * 0.90}" type="currency" pattern="#0.00"/>元)</p>
                <p>起点发车时间前≥5小时，全额退50%(<fmt:formatNumber value="${CarRental.income * 0.50}" type="currency" pattern="#0.00"/>元)</p>
                <p>起点发车时间前<5小时，不予退款</p>
            </dd>
            <dt>备注详情</dt>
            <dd>
                <p>1.江城巴士平台将提供一次改期服务</p>
                <p>2.车辆发车前24小时,可免费改期一次</p>
            </dd>
        </dl>
    </div>
</div>

<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    /*
     * 注意：
     * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
     * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
     * 3. 常见问题及完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
     *
     * 开发中遇到问题详见文档“附录5-常见错误及解决办法”解决，如仍未能解决可通过以下渠道反馈：
     * 邮箱地址：weixin-open@qq.com
     * 邮件主题：【微信JS-SDK反馈】具体问题
     * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
     */

    wx.config({
        debug: false,
        appId: 'wxd7e6d638dcb6bfbf',
        timestamp: $('#timestamp').val(),
        nonceStr: $('#noncestr').val(),
        signature: $('#signature').val(),
        jsApiList: [
            'checkJsApi',
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo',
            'onMenuShareQZone',
            'hideMenuItems',
            'showMenuItems',
            'hideAllNonBaseMenuItem',
            'showAllNonBaseMenuItem',
            'translateVoice',
            'startRecord',
            'stopRecord',
            'onRecordEnd',
            'playVoice',
            'pauseVoice',
            'stopVoice',
            'uploadVoice',
            'downloadVoice',
            'chooseImage',
            'previewImage',
            'uploadImage',
            'downloadImage',
            'getNetworkType',
            'openLocation',
            'getLocation',
            'hideOptionMenu',
            'showOptionMenu',
            'closeWindow',
            'scanQRCode',
            'chooseWXPay',
            'openProductSpecificView',
            'addCard',
            'chooseCard',
            'openCard'
        ]
    });
    wx.ready(function () {
        //获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
        wx.onMenuShareTimeline({
            title: '江城巴士-优惠券', // 分享标题
            link: 'http://www.whjcbs.com/leoman_rcbus/wechat/coupon/receive?rentalId=' + $('#id').val(), // 分享链接
            imgUrl: '', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        //获取“分享给朋友”按钮点击状态及自定义分享内容接口
        wx.onMenuShareAppMessage({
            title: "江城巴士-优惠券", // 分享标题
            desc: "来输入手机号领取优惠券吧~", // 分享描述
            link: 'http://www.whjcbs.com/leoman_rcbus/wechat/coupon/receive?rentalId=' + $('#id').val(), // 分享链接
            imgUrl: "", // 分享图标
            type: 'link', // 分享类型,music、video或link，不填默认为link
            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        //获取“分享到QQ”按钮点击状态及自定义分享内容接口
        wx.onMenuShareQQ({
            title: '江城巴士-优惠券', // 分享标题
            desc: '来输入手机号领取优惠券吧~', // 分享描述
            link: 'http://www.whjcbs.com/leoman_rcbus/wechat/coupon/receive?rentalId=' + $('#id').val(), // 分享链接
            imgUrl: '', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        //获取“分享到腾讯微博”按钮点击状态及自定义分享内容接口
        wx.onMenuShareWeibo({
            title: '江城巴士-优惠券', // 分享标题
            desc: '来输入手机号领取优惠券吧~', // 分享描述
            link: 'http://www.whjcbs.com/leoman_rcbus/wechat/coupon/receive?rentalId=' + $('#id').val(), // 分享链接
            imgUrl: '', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        //获取“分享到QQ空间”按钮点击状态及自定义分享内容接口
        wx.onMenuShareQZone({
            title: '江城巴士-优惠券', // 分享标题
            desc: '来输入手机号领取优惠券吧~', // 分享描述
            link: 'http://www.whjcbs.com/leoman_rcbus/wechat/coupon/receive?rentalId=' + $('#id').val(), // 分享链接
            imgUrl: '', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
    });

</script>
<script>

    //服务协议
    function toAgree(){
        location.href = "${contextPath}/wechat/agreement";
    }


    // 退改规则
    var modal = $('#rulebox').html();
    $('#rule').on('click', function() {
        layer.open({
            content: modal
            ,className: 'popup'
            ,btn: '确定'
        });
        return false;
    });

    var price = $("#price").val();

    function rewrite(id,type){
        window.location.href = "${contextPath}/wechat/order/rewrite?id="+id + "&type="+type + "&val=" + price;
    }

    $('#submit').on('click', function() {
        var id = $("#id").val();
        $.ajax({
            "url": "${contextPath}/wechat/order/complete/save",
            "data": {
                id : id
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