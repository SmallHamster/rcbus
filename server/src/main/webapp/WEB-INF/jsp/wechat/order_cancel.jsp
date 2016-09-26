<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>取消订单-江城巴士</title>
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
    <div class="title">取消订单</div>
</header>

<section class="wrap order-box">
    <div class="box-info">
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
                <b>${modelNo} (${CarRental.carType.name})</b>
            </div>
            <div class="item">
                <div class="txt">退订原因</div>
                <div class="cnt">
                    <textarea name="unsubscribe" style="resize:none;" id="unsubscribe" class="mul" cols="30" rows="10"></textarea>
                </div>
            </div>
        </div>
        <input type="hidden" value="${CarRental.id}" id="id">
        <div class="ft"></div>
    </div>

    <div class="box-tips">
        <%-- 两天前 --%>
        <c:if test="${index eq 1}">
            <p>根据<a href="javascript:;" id="rule">退改规则</a>，取消订单您的返回金额为全额<fmt:formatNumber value="${val}" type="currency" pattern=".0"/>元，请点击确认后取消订单。</p>
            <input type="hidden" value="${val}" id="val">
        </c:if>
        <%-- 一天前 --%>
        <c:if test="${index eq 2}">
            <p>根据<a href="javascript:;" id="rule">退改规则</a>，取消订单您将会被扣除全额10%（<fmt:formatNumber value="${val * 0.1}" type="currency" pattern=".0"/>元）预定金，返回金额为<fmt:formatNumber value="${val * 0.9}" type="currency" pattern=".0"/>元，请点击确认后取消订单。</p>
            <input type="hidden" value="<fmt:formatNumber value="${val * 0.9}" type="currency" pattern=".0"/>" id="val">
        </c:if>
        <%-- 5小时前 --%>
        <c:if test="${index eq 3}">
            <p>根据<a href="javascript:;" id="rule">退改规则</a>，取消订单您将会被扣除全额50%（<fmt:formatNumber value="${val * 0.5}" type="currency" pattern=".0"/>元）预定金，返回金额为<fmt:formatNumber value="${val * 0.5}" type="currency" pattern=".0"/>元，请点击确认后取消订单。</p>
            <input type="hidden" value="<fmt:formatNumber value="${val * 0.5}" type="currency" pattern=".0"/>" id="val">
        </c:if>
        <c:if test="${index eq 4}">
            <p>根据<a href="javascript:;" id="rule">退改规则</a>，取消订单您将会被扣除全额的预定金，不返回金额，请点击确认后取消订单。</p>
            <input type="hidden" value="0" id="val">
        </c:if>
    </div>
    <div class="button">
        <button class="ubtn ubtn-red" id="submit">确认取消</button>
    </div>
</section>

<div id="rulebox" class="hide">
    <div class="rule">
        <dl>
            <dt>预订车型</dt>
            <dd>${modelNo} (${CarRental.carType.name})</dd>
            <dt>行程总价</dt>
            <dd><fmt:formatNumber value="${val}" type="currency" pattern=".0"/>元(最终价)</dd>
            <dt>取消订单</dt>
            <dd>
                <p>出发前≥48小时，全额退(<fmt:formatNumber value="${val}" type="currency" pattern=".0"/>元)</p>
                <p>出发前≥24小时，全额退90%(<fmt:formatNumber value="${val * 0.9}" type="currency" pattern=".0"/>元)</p>
                <p>出发前≥5小时，全额退50%(<fmt:formatNumber value="${val * 0.5}" type="currency" pattern=".0"/>元)</p>
                <p>出发前<5小时，不予退款</p>
            </dd>
            <dt>备注详情</dt>
            <dd>
                <p>出发前≥24小时，免费改签</p>
                <p>出发当前，不得改签</p>
            </dd>
        </dl>
    </div>
</div>

<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.dom.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.core.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.scrollview.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.frame.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.scroller.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.frame.ios.js"></script>
<script src="${contextPath}/wechat-html/js/mobiscroll/mobiscroll.i18n.zh.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>


<script type="text/javascript">
    var $time1 = $('#time1');

    var mydate = new Date();
    var maxDay = 15; // 最后期限
    var dateArr = []; // 日期范围
    var hours = ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'];
    var minutes = ['05', 10, 15, 20, 25, 30, 35, 40, 45, 50, 55];
    var time1;


    //求月份最大天数
    function getDaysInMonth(year, month){
        return new Date(year, month + 1, 0).getDate();
    }

    function getDate(mydate) {

        var year = mydate.getFullYear();
        var month = mydate.getMonth();
        var date = mydate.getDate() + 1; // 从明天开始
        var maxMonthDays = getDaysInMonth(year, month);
        var i, arr = [];
        if (maxMonthDays > date + maxDay) {
            for (i = date; i < maxMonthDays - maxDay; i ++) {
                arr.push(year + '-' + (month + 1) + '-' + i);
            }
        } else {
            for (i = date; i <= maxMonthDays; i ++) {
                arr.push(year + '-' + (month + 1) + '-' + i);
            }
            for (i = 1; i < maxDay + date - maxMonthDays; i ++) {
                arr.push(year + '-' + (month + 2) + '-' + i);
            }
        }

        return arr;
    }

    dateArr = getDate(mydate);
    time1 = dateArr[0].replace(/-/g,'');

    mobiscroll.scroller('#time1', {
        theme: 'ios',
        display: 'bottom',
        lang: 'zh',
        headerText: '出发时间',
        focusOnClose: false,
        formatValue: function (data) {
            return data[0] + ' ' + data[1] + ':' + data[2];
        },
        wheels: [
            [
                {
                    circular: false,
                    data: dateArr
                },
                {
                    circular: false,
                    data: hours
                },
                {
                    circular: false,
                    data: minutes
                }
            ]
        ],
        onSet: function(event, inst) {
            time1 = event.valueText.split(' ')[0].replace(/-/g, '');
        }
    });

    mobiscroll.scroller('#time2', {
        theme: 'ios',
        display: 'bottom',
        lang: 'zh',
        headerText: '返回时间',
        focusOnClose: false,
        formatValue: function (data) {
            return data[0] + ' ' + data[1] + ':' + data[2];
        },
        wheels: [
            [
                {
                    circular: false,
                    data: dateArr
                },
                {
                    circular: false,
                    data: hours
                },
                {
                    circular: false,
                    data: minutes
                }
            ]
        ],
        validate: function (event, inst) {
            var i,
                    values = event.values[0],
                    disabledValues = [];

            for (i = 0; i < dateArr.length; i ++) {
                if (time1 && parseInt(dateArr[i].replace(/-/g, ''), 10) < parseInt(time1, 10)) {
                    disabledValues.push(dateArr[i]);
                }
            }

            return {
                disabled: [
                    disabledValues, [], []
                ]
            }
        }
    });

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

    $('#submit').on('click', function() {
        var unsubscribe = $("#unsubscribe").val();
        var id = $("#id").val();
        var val = $("#val").val();
        $.ajax({
            "url": "${contextPath}/wechat/order/cancel/save",
            "data": {
                id:id,
                val:val,
                unsubscribe:unsubscribe
            },
            "dataType": "json",
            "type": "POST",
            success: function (result) {
                if (result.status==0) {
                    layer.open({
                        content: '<i class="ico ico-right2"></i><br /><br />您的订单已退订成功，请等待<br />客服人员与您联系'
                        ,btn: '确定'
                        ,yes: function(index, layero){
                            window.location.href = "${contextPath}/wechat/order/myOrder/index";
                        }
                    });
                }else {
                    layer.open({
                        content: '<i class="ico ico-right2"></i><br /><br />退订失败'
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
