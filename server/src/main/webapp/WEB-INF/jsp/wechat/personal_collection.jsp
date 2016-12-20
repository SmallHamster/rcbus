<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>我的收藏-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body>

<header>
    <div class="title">我的收藏</div>
</header>


<section class="wrap">
    <div class="ui-list">
        <div class="bd">
            <ul>

            </ul>
        </div>
        <div class="button hide">
            <button class="ubtn ubtn-blue" id="submit">取消收藏</button>
        </div>
    </div>

</section>

<!-- 路线模板 -->
<li class="c1" id="routeTemplate" style="display: none;">
    <div class="inner">
        <div class="fromto">
            <em>光谷广场</em>
            <i></i>
            <em>武昌火车站</em>
            <b>1</b>
        </div>
        <div class="detail">
            <span>东风风行cm7</span>
            <span>即将出发：9:30</span>
            <span>车牌：鄂TF0809</span>
            <span>预定人数：48</span>
        </div>
        <div class="bus">野芷湖西路保利心语</div>
        <div class="fav faved"></div>
    </div>
    <div class="check">
        <input type="checkbox" value="iid_1" class="rdo2">
    </div>
</li>


<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>
<script src="${contextPath}/wechat-html/js/global.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=pcExWaLfoopv7vZ5hO1B8ej8"></script>
<script>
    $(function() {

        initList();
        init();

    })

    var userLat;
    var userLng;

    function init(){
        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function(r){
            if(this.getStatus() == BMAP_STATUS_SUCCESS){
                userLat = r.point.lat;
                userLng = r.point.lng;
            }
            else {
                console.info('failed'+this.getStatus());
            }
            initList();
        },{enableHighAccuracy: true});
    }

    function initList(){
        var data = {
            'userLat':userLat,
            'userLng':userLng,
        };
        $.post("${contextPath}/wechat/route/collect/list",data,function(res){
            if(res.status == 0){
                var list = res.data.object.routeList;
                $(".ui-list ul").empty();
                for(var i=0; i<list.length; i++){
                    var template = $("#routeTemplate").clone().removeAttr("id");
                    template.find("em").eq(0).text(list[i].startStation);
                    template.find("em").eq(1).text(list[i].endStation);
                    template.find("b").text(list[i].lineName==null?"":list[i].lineName);
                    template.find(".fromto").attr('onclick','toDetail('+list[i].id+')');
                    template.find(".fav").attr("val",list[i].id);
                    template.find("[type=checkbox]").val(list[i].id);
                    if(list[i].bus != null){
                        template.find(".detail").find("span").eq(0).text((list[i].bus.modelNo==null)?'':(list[i].bus.modelNo));
                        template.find(".detail").find("span").eq(2).text("车牌号："+list[i].bus.carNo);
                        template.find(".bus").text(list[i].bus.stationName==null?'':list[i].bus.stationName);//当前站点名称
                    }
                    var times = list[i].tempTimes;
                    template.find(".detail").find("span").eq(1).text("即将出发："+(times==null||times.length==0?"无":times[0].departTime));
                    template.find(".detail").find("span").eq(3).text("预定人数："+(list[i].orderNum==null?0:list[i].orderNum));
                    template.show();
                    $(".ui-list ul").append(template);
                }

                initFav();
            }
        });
    }

    function initFav(){
        // 收藏&取消收藏
        $('.fav').on('click', function() {
            $(this).toggleClass('faved');
            var isFaved = 0;
            if($(this).hasClass('faved')){
                isFaved = 1;
            }else{
                isFaved = 0;
            }
            $.ajax({
                url: "${contextPath}/wechat/route/collect/oper",
                data: {'isCollect': isFaved,'routeId': $(this).attr("val")},
                success: function(res) {
                    if(res.status == 0){
                        window.location.reload();
                    }else{
                        alertMsg("操作失败");
                        $(this).toggleClass('faved');
                    }
                }
            })
        })
    }

    function initTouch(){
        var sX = 0;    // 手指初始x坐标
        var sY = 0;    // 手指初始y坐标
        var disX = 0;  // 滑动差值
        var disY = 0;  // 滑动差值

        function showButton() {
            var length = $('.ui-list').find('.rdo2:checked').length;
            if (length === 0) {
                $('.button').addClass('hide');
            } else{
                $('.button').removeClass('hide');
            }
        }

        $('.ui-list').on('touchstart', '.check', function(e) {
            var rdo2 = $(this).find('.rdo2')[0];
            rdo2.checked = !rdo2.checked;
            showButton();
            return false;
        })
        $('.ui-list').on('touchstart', 'li', function(e){
            sX = e.changedTouches[0].pageX;
            sY = e.changedTouches[0].pageY;
        })
        $('.ui-list').on('touchmove', 'li', function(e){
            disX = e.changedTouches[0].pageX - sX;
            disY = e.changedTouches[0].pageY - sY;
            if (Math.abs(disY) <= Math.abs(disX)) {
                e.preventDefault();
            }
        })
        $('.ui-list').on('touchend', 'li', function(e){
            if (Math.abs(disY) > 60) {
                return;
            }
            if (disX > 30) {
                $(this).removeClass('on');

            } else if (disX < -30) {
                $(this).addClass('on');

            }
            e.preventDefault();
        })

        $('#submit').on('click', function() {
            var iids = [];
            $('.rdo2:checked').each(function() {
                iids.push(this.value);
            });
            $.ajax({
                url: '${contextPath}/wechat/route/collect/multiDel',
                data: {routeIds: iids.join(',')}, // eg: iid=1,2,3,4,5,6
                success: function(res) {
                    if(res.status == 0){
                        alertMsg("取消成功");
                    }else{
                        alertMsg("取消失败");
                    }
                }
            })
        })
    }

    function toDetail(id){
        location.href = "${contextPath}/wechat/route/detail?routeId="+id;
    }
</script>
</body>
</html>