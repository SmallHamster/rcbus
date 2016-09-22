<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>申请改期-江城巴士</title>
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
    <div class="title">申请改期</div>
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
                <div class="txt">出发时间</div>
                <div class="cnt">
                    <input type="text" class="ipt arrow" id="time1" placeholder="" readonly value="<date:date value="${CarRental.startDate}" format="yyyy-MM-dd HH:mm"></date:date>">
                    <span class="error"></span>
                    <i>&gt;</i>
                </div>
            </div>
            <c:if test="${CarRental.rentalWay ne 0 }">
                <div class="item">
                    <div class="txt">返回时间</div>
                    <div class="cnt">
                        <input type="text" class="ipt arrow" id="time2" placeholder="" readonly value="<date:date value="${CarRental.endDate}" format="yyyy-MM-dd HH:mm"></date:date>">
                        <span class="error"></span>
                        <i>&gt;</i>
                    </div>
                </div>
            </c:if>
        </div>
        <input type="hidden" value="${CarRental.id}" id="id">
        <div class="ft"></div>
    </div>

    <div class="box-tips">
        <dl>
            <dt>改期须知：</dt>
            <dd>1.发车前≥24小时，将不会收取任何费用</dd>
            <dd>2.发车当天，不得改签</dd>
            <dd>3.每份订单仅限改签一次</dd>
        </dl>
    </div>

    <div class="button">
        <button class="ubtn ubtn-blue" id="submit">我要改期</button>
    </div>
</section>

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

    $('#submit').on('click', function() {
        var time1 = $("#time1").val();
        var time2 = $("#time2").val();
        var id = $("#id").val();

        $.ajax({
            "url": "${contextPath}/wechat/order/rewrite/save",
            "data": {
                id:id,
                time1:time1,
                time2:time2
            },
            "dataType": "json",
            "type": "POST",
            success: function (result) {
                if (result.status==0) {
                    layer.open({
                        content: '<i class="ico ico-right2"></i><br /><br />改签成功'
                        ,btn: '确定'
                        ,yes: function(index, layero){
                            window.location.href = "${contextPath}/wechat/order/myOrder/index";
                        }
                    });
                }else {
                    layer.open({
                        content: '<i class="ico ico-right2"></i><br /><br />改签失败'
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
