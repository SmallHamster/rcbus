<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>实时地图-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${contextPath}/wechat-html/favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
    <style>
        html,body{height:100%;}
    </style>
</head>

<body>

<header>
    <div class="title">班车线路</div>
</header>


<section class="map-box">
    <div class="map-wrap" id="map"></div>

    <div class="button">
        <button type="button" class="ubtn ubtn-blue" onclick="toOrder()">预定座位</button>
    </div>
</section>


<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=pcExWaLfoopv7vZ5hO1B8ej8"></script>
<script>

    var locArr = [];

    $.post("${contextPath}/wechat/route/findBus",{'routeId':"${routeId}"},function(res){
        if(res.status == 0){
            var bsList = res.data.object.bsList;
            for(var i=0; i<bsList.length;i++){
                var loc = {
                    'lat':bsList[i].bus.curLat,
                    'lng':bsList[i].bus.curLng,
                    'carNo':bsList[i].bus.carNo,
                    'dn':bsList[i].bus.driverName==null?'':bsList[i].bus.driverName,
                    'dp':bsList[i].bus.driverPhone==null?'':bsList[i].bus.driverPhone
                };
                locArr.push(loc);
            }
            initMap();
        }
    });

    function initMap(){
        // 百度地图API功能
        map = new BMap.Map("map");
        map.centerAndZoom(new BMap.Point(locArr[0].lng,locArr[0].lat), 12);

        var opts = {
            width : 100,     // 信息窗口宽度
            height: 40,     // 信息窗口高度
            title : "信息窗口" , // 信息窗口标题
            enableMessage:true//设置允许信息窗发送短息
        };
        for(var i=0;i<locArr.length;i++){
            var marker = new BMap.Marker(new BMap.Point(locArr[i].lng,locArr[i].lat));  // 创建标注
            var content = "司机姓名："+locArr[i].dn+", 司机电话："+locArr[i].dp;
            map.addOverlay(marker);               // 将标注添加到地图中
            addClickHandler(content,marker,locArr[i].carNo);
        }
        function addClickHandler(content,marker,title){
            marker.addEventListener("click",function(e){
                openInfo(content,e,title)}
            );
        }
        function openInfo(content,e,title){
            var p = e.target;
            var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
            opts.title = title;
            var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象
            map.openInfoWindow(infoWindow,point); //开启信息窗口
        }

    }

    //跳转至订单页
    function toOrder(){
        location.href = "${contextPath}/wechat/route/toOrder?routeId=${routeId}";
    }


    // 百度地图API功能
    /*var map = new BMap.Map("map");

    var point = new BMap.Point(114.410231,30.481782);

    // 创建标注
    var marker = new BMap.Marker(point);

    // 将标注添加到地图中
    map.addOverlay(marker);

    marker.setAnimation(BMAP_ANIMATION_BOUNCE); // BMAP_ANIMATION_DROP
    // marker.enableDragging(); // 标注可拖拽

    map.centerAndZoom(point, 18); // 定位到中心点

    // 启用滚轮放大缩小
    map.enableScrollWheelZoom();
    map.enableContinuousZoom();

    // 地图平移缩放控件，默认位于地图左上方，它包含控制地图的平移和缩放的功能。
    map.addControl(new BMap.NavigationControl());

    // 比例尺控件，默认位于地图左下方，显示地图的比例关系。
    map.addControl(new BMap.ScaleControl());*/

</script>

</body>
</html>