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
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat/css/app.css">
</head>

<body>

<header>
    <div class="title">班车线路</div>
</header>

<section class="wrap route-box">
    <div class="slide">
        <ul>
            <%--<li>
                <img src="images/banner_bus.jpg" />
            </li>
            <li>
                <img src="images/banner_bus.jpg" />
            </li>
            <li>
                <img src="images/banner_bus.jpg" />
            </li>--%>
            <c:forEach items="${bannerList}" var="banner">
                <li>
                    <img src="${banner.image.path}" />
                </li>
            </c:forEach>
        </ul>
    </div>
    <form action="" id="formId">
        <div class="search">
            <div class="item from">
                <em>从</em>
                <input type="text" class="ipt" name="" id="from" placeholder="从哪儿出发？" required />
                <i class="clear"></i>
            </div>
            <div class="item to">
                <em>到</em>
                <input type="text" class="ipt" name="" id="to" placeholder="到哪儿去？" required />
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
            <a href="#1" class="place">实时位置</a>
        </div>
        <div class="fav">
            <a href="javascript:;"></a>
        </div>
    </div>
</li>

<%@ include file="../inc/new2/foot.jsp" %>
<script src="${contextPath}/wechat/js/zepto.min.js"></script>
<script src="${contextPath}/wechat/js/app.js"></script>
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

        // 清空输入框文本
        $('.clear').on('click', function() {
            $(this).prev().val('');
        });


        // 收藏&取消收藏
        $('.fav').on('click', function() {
            $(this).toggleClass('faved');
            var isFaved = $(this).hasClass('faved');
            $.ajax({
                url: '',
                data: {faved: isFaved},
                success: function() {
                    // save data into database
                }
            })
        })

        search();
    })

    //查询
    function search(){
        $("#formId").ajaxSubmit({
            url : "${contextPath}/wechat/route/list",
            type : "POST",
            success : function(result) {
                if(result.status == 0) {
                    var list = result.data.list;
                    $(".ui-list ul").empty();
                    for(var i=0; i<list.length;i++){
                        var template = $("#routeTemplate").clone().removeAttr("id");
                        template.find("em:eq(0)").text(list[i].startStation);
                        template.find("em:eq(1)").text(list[i].endStation);
                        template.find("b").text(i+1);
                        console.info(list[i].startStation);
                        template.show();
                        $(".ui-list ul").append(template);
                    }

                }else {
                    alert("查询失败");
                }
            }
        });
    }
</script>
</body>
</html>