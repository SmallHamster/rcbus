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
                <input type="text" class="ipt" id="time" name="time" value="" placeholder="" readonly />
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
    var $time1 = $('#time');

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

    mobiscroll.scroller('#time', {
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



    function save(){
        var content = $("#content");
        var time = $("#time");
        $("#car_travel").ajaxSubmit({
            url : "${contextPath}/wechat/cartravel/save",
            data : {
                content : content,
                time : time
            },
            type : "POST",
            success : function(result) {
                if(result.status == 0) {
                    alertMsg("预定成功",function(){
                        window.location.href = "${contextPath}/wechat/wechat/index";
                    });
                }
                else {
                    alertMsg("预定失败");
                }
            }
        })

    }


</script>

</body>
</html>