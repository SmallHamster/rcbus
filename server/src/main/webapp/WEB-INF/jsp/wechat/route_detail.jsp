<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>车辆位置-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat/css/app.css">
</head>

<body>

<header>
    <div class="title">车辆位置</div>
</header>


<section class="wrap location-box">
    <div class="slide-wrap">
        <div class="hd">
            <span><img src="${contextPath}/wechat/images/file.png" />车辆信息</span>
        </div>
        <div class="slide">
            <ul>
                <li>
                    <div class="avatar">
                        <img src="${contextPath}/wechat/images/bus_avatar.jpg" />
                        <span>鄂TF0809</span>
                    </div>
                    <div class="detail">
                        <em>司机姓名</em>
                        <span>王华</span>
                        <em>司机电话</em>
                        <span>18812354845</span>
                        <div class="col">
                            <em>品牌</em>
                            <span>东风日产</span>
                        </div>
                        <div class="col">
                            <em>车型</em>
                            <span>东风gpasd11</span>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="avatar">
                        <img src="${contextPath}/wechat/images/bus_avatar.jpg" />
                        <span>鄂TF0809</span>
                    </div>
                    <div class="detail">
                        <em>司机姓名</em>
                        <span>王华</span>
                        <em>司机电话</em>
                        <span>18812354845</span>
                        <div class="col">
                            <em>品牌</em>
                            <span>东风日产</span>
                        </div>
                        <div class="col">
                            <em>车型</em>
                            <span>东风gpasd11</span>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>

    <div class="hd">
        <span><img src="${contextPath}/wechat/images/road.png" />发车线路</span>
        <em>实时线路</em>
    </div>
    <div class="location">
        <ul id="line">
            <li>
                <i class="arrow"></i>
                <em>武丰村</em>
            </li>
            <li>
                <i class="arrow"></i>
                <em>园林路瑞丰路</em>
            </li>
            <li class="current">
                <i class="arrow"></i>
                <em>园林路钢都花园</em>
            </li>
            <li>
                <i class="arrow"></i>
                <em>友谊大道柴林花园</em>
            </li>
        </ul>
    </div>

    <div class="departure">
        <h2><img src="${contextPath}/wechat/images/clock.png" />发车时间</h2>

        <dl>
            <dd class="time1">
            </dd>
        </dl>
    </div>


    <div class="button">
        <button type="button" class="ubtn ubtn-blue" id="submit">预定座位</button>
    </div>
</section>

<li id="busTemplate" style="display: none;">
    <div class="avatar">
        <img src="${contextPath}/wechat/images/bus_avatar.jpg" />
        <span></span>
    </div>
    <div class="detail">
        <em>司机姓名</em>
        <span></span>
        <em>司机电话</em>
        <span></span>
        <div class="col">
            <em>品牌</em>
            <span></span>
        </div>
        <div class="col">
            <em>车型</em>
            <span></span>
        </div>
    </div>
</li>



<script src="${contextPath}/wechat/js/zepto.min.js"></script>
<script src="${contextPath}/wechat/js/app.js"></script>
<script>
    $(function() {
        /*var $line = $('#line'),
                count = $line.find('li').length;
        $('#line').width(56 * count - 10);*/

        initOther();
    })

    //初始化班车，路线，时间
    function initOther(){
        $.post("${contextPath}/wechat/route/other",{'routeId':"${routeId}"},function(res){

            //班车
            var bsList = res.data.object.map.bsList;
            $(".slide ul").empty();
            for(var i=0; i<bsList.length;i++){
                var template = $("#busTemplate").clone().removeAttr("id");
                template.find(".avatar img").attr("src",bsList[i].bus.image.path);
                template.find(".avatar span").text(bsList[i].bus.carNo);
                template.find(".detail span").eq(0).text(bsList[i].bus.driverName);
                template.find(".detail span").eq(1).text(bsList[i].bus.driverPhone);
                template.find(".detail .col").eq(0).find("span").text(bsList[i].bus.brand);
                template.find(".detail .col").eq(1).find("span").text(bsList[i].bus.modelNo);
                template.show();
                $(".slide ul").append(template);
            }

            initSlide();


            //站点
            var stationList = res.data.object.map.stationList;
            $("#line").empty();
            for(var i=0; i<stationList.length;i++){
                $("#line").append('<li><i class="arrow"></i><em>'+stationList[i].stationName+'</em></li>');
            }
            var count = $("#line").find('li').length;
            $('#line').width(56 * count - 10);

            //时间
            var timeList = res.data.object.map.timeList;
            $(".time1").empty();
            for(var i=0; i<timeList.length;i++){
                $(".time1").append('<span style="margin-left: 20px;"><em>'+timeList[i].departTime+'</em></span>');
            }

        });
    }

    //初始化滑动效果，暂时没效果
    function initSlide(){
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
                    console.info($nav.eq(i));
                    console.info("---------firstCallback-----------");
                },
                callback : function(i,sum){
                    $nav.eq(i).addClass('current').siblings().removeClass('current');
                    console.info($nav.eq(i));
                    console.info("---------callback-----------");
                }
            });
        });
    }

</script>

</body>
</html>