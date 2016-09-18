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
            <li>
                <img src="images/banner_bus.jpg" />
            </li>
            <li>
                <img src="images/banner_bus.jpg" />
            </li>
            <li>
                <img src="images/banner_bus.jpg" />
            </li>
        </ul>
    </div>
    <form action="">
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
                <button type="submit" class="ubtn ubtn-blue" id="submit">查询班车</button>
            </div>
        </div>
    </form>

    <div class="ui-list">
        <div class="hd">全部线路</div>
        <div class="bd">
            <ul>
                <li class="c1">
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
                <li class="c2">
                    <div class="inner">
                        <div class="fromto">
                            <em>光谷广场</em>
                            <i></i>
                            <em>武昌火车站</em>
                            <b>2</b>
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
                <li class="c3">
                    <div class="inner">
                        <div class="fromto">
                            <em>光谷广场</em>
                            <i></i>
                            <em>武昌火车站</em>
                            <b>3</b>
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
                <li class="c4">
                    <div class="inner">
                        <div class="fromto">
                            <em>光谷广场</em>
                            <i></i>
                            <em>武昌火车站</em>
                            <b>4</b>
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
                <li class="c5">
                    <div class="inner">
                        <div class="fromto">
                            <em>光谷广场</em>
                            <i></i>
                            <em>武昌火车站</em>
                            <b>5</b>
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
                <li class="c6">
                    <div class="inner">
                        <div class="fromto">
                            <em>光谷广场</em>
                            <i></i>
                            <em>武昌火车站</em>
                            <b>6</b>
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
                <li class="c7">
                    <div class="inner">
                        <div class="fromto">
                            <em>光谷广场</em>
                            <i></i>
                            <em>武昌火车站</em>
                            <b>7</b>
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
            </ul>
        </div>
    </div>
</section>

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
    })
</script>
</body>
</html>