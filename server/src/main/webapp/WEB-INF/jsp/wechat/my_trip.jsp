<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>我的行程-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body>

<header>
    <div class="title">我的行程</div>
</header>


<section class="wrap">
    <div class="ui-list3">
        <ul>
            <li class="c1">
                <div class="cat">通勤班车</div>
                <div class="state state6">已结束</div>

                <div class="inner">
                    <div class="date">11月11日</div>
                    <div class="fromto">
                        <em>光谷广场</em>
                        <i></i>
                        <em>武昌火车站</em>
                    </div>
                    <div class="detail">
                        <span>东风风行cm7</span>
                        <span>发车时间：9:30</span>
                        <span>车牌：鄂TF0809</span>
                        <span>保险单号：zf55909</span>
                    </div>
                </div>
                <div class="check">
                    <input type="checkbox" class="rdo2">
                </div>
            </li>
            <li class="c2">
                <div class="cat">预定用车</div>
                <div class="state state5">已取消</div>

                <div class="inner">
                    <div class="date">11月11日</div>
                    <div class="fromto">
                        <em>光谷广场</em>
                        <i></i>
                        <em>武昌火车站</em>
                    </div>
                    <div class="detail">
                        <span>东风风行cm7</span>
                        <span>发车时间：9:30</span>
                        <span>车牌：鄂TF0809</span>
                        <span>保险单号：zf55909</span>
                    </div>
                </div>

                <div class="check">
                    <input type="checkbox" class="rdo2">
                </div>
            </li>
            <li class="c1">
                <div class="cat">通勤班车</div>
                <div class="state state6">已结束</div>

                <div class="inner">
                    <div class="date">11月11日</div>
                    <div class="fromto">
                        <em>光谷广场</em>
                        <i></i>
                        <em>武昌火车站</em>
                    </div>
                    <div class="detail">
                        <span>东风风行cm7</span>
                        <span>发车时间：9:30</span>
                        <span>车牌：鄂TF0809</span>
                        <span>保险单号：zf55909</span>
                    </div>
                </div>
                <div class="check">
                    <input type="checkbox" class="rdo2">
                </div>
            </li>
            <li class="c1">
                <div class="cat">通勤班车</div>
                <div class="state state6">已结束</div>

                <div class="inner">
                    <div class="date">11月11日</div>
                    <div class="fromto">
                        <em>光谷广场</em>
                        <i></i>
                        <em>武昌火车站</em>
                    </div>
                    <div class="detail">
                        <span>东风风行cm7</span>
                        <span>发车时间：9:30</span>
                        <span>车牌：鄂TF0809</span>
                        <span>保险单号：zf55909</span>
                    </div>
                </div>
                <div class="check">
                    <input type="checkbox" class="rdo2">
                </div>
            </li>
            <li class="c2">
                <div class="cat">预定用车</div>
                <div class="state state5">已取消</div>

                <div class="inner">
                    <div class="date">11月11日</div>
                    <div class="fromto">
                        <em>光谷广场</em>
                        <i></i>
                        <em>武昌火车站</em>
                    </div>
                    <div class="detail">
                        <span>东风风行cm7</span>
                        <span>发车时间：9:30</span>
                        <span>车牌：鄂TF0809</span>
                        <span>保险单号：zf55909</span>
                    </div>
                </div>

                <div class="check">
                    <input type="checkbox" class="rdo2">
                </div>
            </li>
        </ul>

        <div class="button">
            <button class="ubtn ubtn-blue">删除</button>
        </div>
    </div>

</section>


<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script>
    $(function() {
        var sX = 0;    // 手指初始x坐标
        var sY = 0;    // 手指初始y坐标
        var disX = 0;  // 滑动差值
        var disY = 0;  // 滑动差值

        $('.ui-list3').on('touchstart', '.check', function(e) {
            var rdo2 = $(this).find('.rdo2')[0];
            rdo2.checked = !rdo2.checked;
            return false;
        })
        $('.ui-list3').on('touchstart', 'li', function(e){
            sX = e.changedTouches[0].pageX;
            sY = e.changedTouches[0].pageY;
        })
        $('.ui-list3').on('touchmove', 'li', function(e){
            disX = e.changedTouches[0].pageX - sX;
            disY = e.changedTouches[0].pageY - sY;
            if (Math.abs(disY) <= Math.abs(disX)) {
                e.preventDefault();
            }
        })
        $('.ui-list3').on('touchend', 'li', function(e){
            if (Math.abs(disY) > 40) {
                return;
            }
            if (disX > 40) {
                $(this).removeClass('on');

            } else if (disX < -40) {
                $(this).addClass('on');

            }
        })
    })
</script>
</body>
</html>