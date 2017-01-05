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

    <c:if test="${type == 0}">
    <div class="button">
        <button type="button" class="ubtn ubtn-blue" onclick="toOrder()">预定座位</button>
    </div>
    </c:if>
</section>


<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=pcExWaLfoopv7vZ5hO1B8ej8"></script>
<script>

    var stationArr = [];
    var locArr = [];

    $.post("${contextPath}/wechat/route/findBus",{'routeId':"${routeId}"},function(res){
        if(res.status == 0){

            //站点路线
            var stationList = res.data.object.map.stationList;
            for(var i=0; i<stationList.length; i++){
                var station = {
                    'name':stationList[i].stationName,
                    'lat': stationList[i].lat,
                    'lng': stationList[i].lng
                };
                stationArr.push(station);
            }

            //车辆位置
            var busList = res.data.object.map.busList;
            for(var i=0; i<busList.length;i++){
                var loc = {
                    'lat':busList[i].curLat,
                    'lng':busList[i].curLng,
                    'carNo':busList[i].carNo,
                    'dn':busList[i].driverName==null?'':busList[i].driverName,
                    'dp':busList[i].driverPhone==null?'':busList[i].driverPhone
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

        //路线站点连线显示
        if(stationArr != null && stationArr != undefined && stationArr.length >= 2){
            var p1 = new BMap.Point(stationArr[0].lng,stationArr[0].lat);
            var p2 = new BMap.Point(stationArr[stationArr.length-1].lng,stationArr[stationArr.length-1].lat);

            /*var lab1 = new BMap.Label(stationArr[0].name,{position:p1});
            map.addOverlay(lab1);

            var lab2 = new BMap.Label(stationArr[stationArr.length-1].name,{position:p2});
            map.addOverlay(lab2);*/

            var waypArr = [];
            for(var i=1 ; i<stationArr.length-1;i++){
                var wayp = new BMap.Point(stationArr[i].lng,stationArr[i].lat);
                /*var lab = new BMap.Label(stationArr[i].name,{position:wayp});
                map.addOverlay(lab);*/
                waypArr.push(wayp);
            }

            var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
            driving.search(p1, p2,{waypoints:waypArr});//waypoints表示途经点
        }

        //给该路线的多个车创建标注
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

</script>

</body>
</html>