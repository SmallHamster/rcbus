<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>输入线路-江城巴士</title>
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
    <div class="title">输入线路</div>
</header>

<section class="wrap">
    <div class="ui-form2">
        <div class="floor">
            <input type="hidden" id="carTypeId" value="${carRentalVo.carTypeId}">
            <input type="hidden" id="id" value="${carRentalVo.id}">
            <div class="item">
                <div class="txt">出发城市</div>
                <div class="cnt">
                    <input type="text" class="ipt arrow" id="city" placeholder="" readonly value="${carRentalVo.city}" >
                    <span class="error"></span>
                    <i>&gt;</i>
                </div>
            </div>

            <div class="item">
                <div class="txt">起点</div>
                <div class="cnt">
                    <input type="text" class="ipt" id="from" placeholder="" value="${carRentalVo.from}">
                    <span class="error"></span>
                </div>
            </div>

            <div class="item">
                <div class="txt">终点</div>
                <div class="cnt">
                    <input type="text" class="ipt" id="to" placeholder="" value="${carRentalVo.to}">
                    <span class="error"></span>
                </div>
            </div>
        </div>

        <div class="floor">
            <div class="item">
                <div class="txt">包车方式</div>
                <div class="cnt">
                    <input type="hidden" id="stype" value="${carRentalVo.stype}">
                    <label><input type="radio" name="stype" class="rdo" checked value="0">单程</label>
                    <label><input type="radio" name="stype" class="rdo" value="1">往返</label>
                    <label><input type="radio" name="stype" class="rdo" value="2">包天</label>
                </div>
            </div>

            <div class="item">
                <div class="txt">出发时间</div>
                <div class="cnt">
                    <input type="text" class="ipt arrow" id="time1" placeholder="" readonly value="${carRentalVo.time1}">
                    <span class="error"></span>
                    <i>&gt;</i>
                </div>
            </div>

            <div class="item" id="end_hidden" style="display: none">
                <div class="txt">返回时间</div>
                <div class="cnt">
                    <input type="text" class="ipt arrow" id="time2" placeholder="" readonly value="${carRentalVo.time2}">
                    <span class="error"></span>
                    <i>&gt;</i>
                </div>
            </div>

            <div class="item">
                <div class="txt">需要车辆</div>
                <div class="cnt">
                    <input type="text" class="ipt" id="number" placeholder="" value="${carRentalVo.number}">
                    <span class="error"></span>
                </div>
            </div>

            <div class="item">
                <div class="txt">总人数</div>
                <div class="cnt">
                    <input type="text" class="ipt" id="amount" placeholder="" value="${carRentalVo.amount}">
                    <span class="error"></span>
                </div>
            </div>

            <div class="item">
                <div class="txt">发票</div>
                <div class="cnt">
                    <input type="hidden" id="ti" value="${carRentalVo.ticket}"/>
                    <label><input type="checkbox" id="ticket" class="rdo" value="1"></label>
                </div>
            </div>

            <div class="item" id="title_hidden" style="display: none">
                <div class="txt">发票抬头</div>
                <div class="cnt">
                    <input type="text" class="ipt" id="title" placeholder="" value="${carRentalVo.title}">
                    <span class="error"></span>
                </div>
            </div>
        </div>

        <div class="floor">
            <div class="item">
                <div class="txt">用车联系人</div>
                <div class="cnt">
                    <input type="text" class="ipt" id="linkman" placeholder="" value="${carRentalVo.linkm}">
                    <span class="error"></span>
                </div>
            </div>

            <div class="item">
                <div class="txt">联系电话</div>
                <div class="cnt">
                    <input type="text" class="ipt" id="mobile" placeholder="" value="${carRentalVo.mobile}">
                    <span class="error"></span>
                </div>
            </div>
        </div>

        <div class="button">
            <button class="ubtn ubtn-blue" id="submit">确认订单</button>
        </div>

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
<%@ include file="../inc/new2/foot.jsp" %>

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


        var city = $("#city").val();
        var from = $("#from").val();
        var to = $("#to").val();
        var stype = $("input[name='stype']:checked").val();
        var time1 = $("#time1").val();
        var time2 = $("#time2").val();
        var number = $("#number").val();
        var amount = $("#amount").val();
        var ticket = $("#ticket:checked").val();
        var title = $("#title").val();
        var linkman = $("#linkman").val();
        var mobile = $("#mobile").val();
        var carTypeId = $("#carTypeId").val();
        var id = $("#id").val();

        if(null==city || city==""){
            alert("请选择城市");
            event.preventDefault();
            return;
        }
        if(null==from || from==""){
            alert("请填写起点");
            event.preventDefault();
            return;
        }

        if(null==to || to==""){
            alert("请填写终点");
            event.preventDefault();
            return;
        }
        if(null==stype || stype==""){
            alert("请选择包车方式");
            event.preventDefault();
            return;
        }
        if(null==time1 || time1==""){
            alert("请选择出发时间");
            event.preventDefault();
            return;
        }

        if(stype!=0 && (null==time2 || time2=="")){
            alert("请选择回城时间");
            event.preventDefault();
            return;
        }

        if(null==number || number==""){
            alert("请填写需要车辆");
            event.preventDefault();
            return;
        }
        if(null==amount || amount==""){
            alert("请填写总人数");
            event.preventDefault();
            return;
        }
        if(ticket==1 && (null==title || title=="")){
            alert("请填写发票抬头");
            event.preventDefault();
            return;
        }
        $.ajax({
            url : "${contextPath}/wechat/carrental/save",
            data : {
                "city" : city,
                "from" : from,
                "to" : to,
                "stype" : stype,
                "time1" : time1,
                "time2" : time2,
                "number" : number,
                "amount" : amount,
                "ticket" : ticket,
                "title" : title,
                "linkman" : linkman,
                "mobile" : mobile,
                "carTypeId" : carTypeId,
                "id" : id
            },
            type : "POST",
            success : function(result) {
                if(result.status==0){
                    layer.open({
                        content: '<i class="ico ico-right2"></i><br /><br />您的订单已提交成功，请等待<br />客服人员与您联系'
                        ,btn: '确定'
                        ,yes: function(index, layero){
                            window.location.href = "${contextPath}/wechat/order/myOrder/index";
                        }
                    });
                }else {
                    alertMsg("订单提交失败");
                }
            }
        });

//
//        layer.open({
//            content: '当前座位已满，是否申请增派车辆？'
//            ,btn: ['申请', '取消']
//            ,yes: function(index){
//                layer.close(index);
//            }
//        });

        return false;
    });

    //往返返回时间
    $("input[name='stype']").on("click",function() {
        var stype = $("input[name='stype']:checked").val();
        stypeCk(stype);
    });

    //发票抬头
    $("#ticket").on("click",function() {
        var ticket = $("#ticket:checked").val();
        ticketCk(ticket);

    });

    function stypeCk (data){
        if(data==0){
            $("#end_hidden").css('display','none');
        }else {
            $("#end_hidden").css('display','block');
        }
    }
    function ticketCk (data){
        if(data==1){
            $("#title_hidden").css('display','block');
        }else {
            $("#title_hidden").css('display','none');
        }
    }

    $("#city").on("click",function() {
        var from = $("#from").val();
        var to = $("#to").val();
        var stype = $("input[name='stype']:checked").val();
        var time1 = $("#time1").val();
        var time2 = $("#time2").val();
        var number = $("#number").val();
        var amount = $("#amount").val();
        var ticket = $("#ticket:checked").val();
        var title = $("#title").val();
        var linkman = $("#linkman").val();
        var mobile = $("#mobile").val();
        var carTypeId = $("#carTypeId").val();
        var id = $("#id").val();

        $.ajax({
            url : "${contextPath}/wechat/carrental/citySession",
            data : {
                "from" : from,
                "to" : to,
                "stype" : stype,
                "time1" : time1,
                "time2" : time2,
                "number" : number,
                "amount" : amount,
                "ticket" : ticket,
                "title" : title,
                "linkman" : linkman,
                "mobile" : mobile,
                "carTypeId" : carTypeId,
                "id" : id
            },
            type : "POST",
            success : function(result) {
                if(result.status == 0) {
                    window.location.href = "${contextPath}/wechat/carrental/city"
                }
            }
        });
    });

    $(function(){
        $("input[name='stype']").each(function(){
            var stype = $("#stype").val();
            if(stype == $(this).val()){
                $(this).iCheck("check");
                stypeCk(stype);
            }
        });

        var ti = $("#ti").val();
        var ticket = $("#ticket").val();
        if(ticket==ti){
            $("#ticket").iCheck("check");
            ticketCk(ticket);
        }

    });

</script>
</body>
</html>