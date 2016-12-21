<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>班车线路-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${contextPath}/wechat-html/favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body>

<header>
    <div class="title">班车线路</div>
</header>

<section class="wrap route-box">
    <div class="slide">
        <ul>
            <c:forEach items="${bannerList}" var="banner">
                <li>
                    <c:if test="${banner.isOc == 0}">
                        <img src="${banner.image.path}" />
                    </c:if>
                    <c:if test="${banner.isOc == 1}">
                        <a href="http://${banner.outsideChain}" ><img src="${banner.image.path}"/></a>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </div>
    <form action="" id="formId">
        <input type="hidden" name="type" value="${type}">
        <input type="hidden" id="userLat" name="userLat" value="">
        <input type="hidden" id="userLng" name="userLng" value="">
        <div class="search">
            <div class="item from">
                <em>从</em>
                <input type="text" class="ipt" name="startStation" id="from" placeholder="从哪儿出发？" required />
                <i class="clear"></i>
            </div>
            <div class="item to">
                <em>到</em>
                <input type="text" class="ipt" name="endStation" id="to" placeholder="到哪儿去？" required />
                <i class="clear"></i>
            </div>
            <div class="button">
                <button type="button" class="ubtn ubtn-blue" id="submit"onclick="search()">查询班车</button>
            </div>
        </div>
    </form>

    <div class="ui-list">
        <div class="hd">全部线路</div>
        <div class="bd">
            <ul>
            </ul>
        </div>
    </div>
</section>

<li id="routeTemplate" class="c1" style="display: none;">
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
        <div class="op">
            <a href="javascript:;" class="place">实时位置</a>
        </div>
        <div class="fav">
            <a href="javascript:;"></a>
        </div>
    </div>
</li>

<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/app.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>
<script src="${contextPath}/wechat-html/js/global.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=pcExWaLfoopv7vZ5hO1B8ej8"></script>
<script>

    $(function() {
        $('.slide').each(function() {
            var $self = $(this),
                    $nav,
                    length = $self.find('li').length;

            if (length < 2) {
                return;
            }
            var nav = ['<div class="nav">'];
            for (var i = 0 ; i < length; i++) {
                nav.push( i === 0 ? '<i class="current"></i>' : '<i></i>');
            }
            nav.push('</div>');
            $self.append(nav.join(''));
            $nav = $self.find('i');

            $(this).swipeSlide({
                index : 0,
                continuousScroll : true,
                autoSwipe : false,
                lazyLoad : true,
                firstCallback : function(i,sum){
                    $nav.eq(i).addClass('current').siblings().removeClass('current');
                },
                callback : function(i,sum){
                    $nav.eq(i).addClass('current').siblings().removeClass('current');

                }
            });
        })

        //初始化查询
        search();
        init();

        // 清空输入框文本
        $('.clear').on('click', function() {
            $(this).prev().val('');
            search();
        });

    });

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
                    if(res.status != 0){
                        alertMsg("操作失败");
                        $(this).toggleClass('faved');
                    }
                }
            })
        })
    }

    function init(){
        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function(r){
            if(this.getStatus() == BMAP_STATUS_SUCCESS){
                $("#userLat").val(r.point.lat);
                $("#userLng").val(r.point.lng);
                search();
            }
            else {
                console.info('failed'+this.getStatus());
            }
            search();
        },{enableHighAccuracy: true});
    }

    //查询
    function search(){
        $.post("${contextPath}/wechat/route/list",$("#formId").serialize(),function(result){
            if(result.status == 0) {
                var list = result.data.list;
                $(".ui-list ul").empty();
                if(list.length > 0){
                    $(".ui-list .hd").eq(0).text("全部路线");
                    for(var i=0; i<list.length;i++){
                        var template = $("#routeTemplate").clone().removeAttr("id");
                        var clazz = getClassByIndex(i+1);
                        if(i > 0 && list[i].lineName != null && (list[i].lineName == list[i-1].lineName)){
                            clazz = getClassByIndex(i);
                        }
                        template.attr("class", clazz);
                        template.find("em").eq(0).text(list[i].startStation);
                        template.find("em").eq(1).text(list[i].endStation);
                        template.find("b").text(list[i].lineName==null?"":list[i].lineName);
                        template.find(".fromto").attr('onclick','toDetail('+list[i].id+')');//路线名称跳转至详情页
                        template.find(".detail").attr('onclick','toDetail('+list[i].id+')');//路线详情区跳转至详情页
                        template.find(".fav").attr("val",list[i].id);
                        if(list[i].isCollect == 1){
                            template.find(".fav").addClass("faved");//给已收藏的路线添加已收藏的样式
                        }
                        var times = list[i].tempTimes;
                        template.find(".detail").find("span").eq(1).text("即将出发："+(times==null||times.length==0?"无":times[0].departTime));
                        template.find(".detail").find("span").eq(3).text("预定人数："+(list[i].orderNum==null?0:list[i].orderNum));
                        if(list[i].bus != null){
                            template.find(".detail").find("span").eq(0).text((list[i].bus.modelNo==null)?'':(list[i].bus.modelNo));
                            template.find(".detail").find("span").eq(2).text("车牌号："+list[i].bus.carNo);
                            template.find(".bus").text(list[i].bus.stationName==null?'':list[i].bus.stationName);//当前站点名称
                        }
                        template.find(".op a").attr('onclick','toPosition('+list[i].id+')');
                        template.show();
                        $(".ui-list ul").append(template);
                    }
                    initFav();
                }else{
                    $(".ui-list .hd").eq(0).text("您搜索的路线暂不存在，如以后开通该路线我们会及时通知您哦~");
                }

            }else {
                alertMsg("查询失败");
            }
        });
    }

    function toDetail(id){
        location.href = "${contextPath}/wechat/route/detail?routeId="+id;
    }

    function toPosition(id){
//        location.href = "http://221.234.42.20:89/Interface/findPosition.action?carNum=鄂ALB229";
        location.href = "${contextPath}/wechat/route/toPosition?routeId="+id;
    }

    /*function alertMsg(msg,func){
        if(func == '' || func == undefined){
            layer.open({
                content: msg
                ,btn: '确定'
            });
        }else{
            layer.open({
                content: msg
                ,btn: ['确定']
                ,yes: function(index){
                    layer.close(index);
                    func();
                }
            });
        }
    }*/

    function getClassByIndex(index){
        var clazz = "c" + ((index%7)==0?7:(index%7));
        return clazz;
    }

</script>
</body>
</html>