<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>个人通勤-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <link rel="stylesheet" href="wechat/css/app.css">
</head>

<body>

<header>
    <div class="title">个人通勤</div>
</header>


<section class="wrap ui-form">
    <h1 class="sub-title">请留下您的联系方式，我们将尽快与您取得联系</h1>
    <form id="formId">
        <div class="form">
            <div class="item">
                <label class="for">联系人</label>
                <input type="text" class="ipt" value="张三" />
            </div>

            <div class="item">
                <label class="for">联系方式</label>
                <input type="text" class="ipt" value="18074156959" />
            </div>

            <div class="item">
                <label class="for">乘车人数</label>
                <input type="text" class="ipt" value="11" />
            </div>

            <div class="item">
                <label class="for">出发地点</label>
                <input type="text" class="ipt" value="武汉市洪山区" />
            </div>

            <div class="item">
                <label class="for">目的地</label>
                <input type="text" class="ipt" value="武汉市洪山区" />
            </div>

            <div class="item">
                <label class="for">出发时间</label>
                <input type="text" class="ipt" value="8:00" />
            </div>

            <div class="item">
                <label class="for">到达时间</label>
                <input type="text" class="ipt" value="9:00" />
            </div>

            <div class="item">
                <label class="for">返程时间</label>
                <input type="text" class="ipt" value="18:00" />
            </div>

            <div class="item">
                <label class="for">周期</label>
                <div class="select">
                    <div class="hd">工作日</div>
                    <div class="bd">
                        <div class="option" data-value="1">工作日</div>
                        <div class="option" data-value="2">周末</div>
                        <div class="option" data-value="3">每天</div>
                    </div>
                    <input type="hidden" name="period" value="1">
                </div>
            </div>

            <div class="item">
                <label class="for">其他备注</label>
                <textarea name="" id="" class="ipt ipt-mul" cols="30" rows="10">其他备注其他备注其他备注其他备注其他备注其他备注</textarea>
            </div>
        </div>

        <div class="button">
            <button type="button" class="ubtn ubtn-blue" id="submit">确认</button>
        </div>
    </form>
</section>


<%@ include file="../inc/new2/foot.jsp" %>
<script src="wechat/js/zepto.min.js"></script>
<script src="wechat/js/layer/layer.js"></script>
<script>
    $(function() {
        var price = parseFloat($('#price').val()); // 价格
        var $select = $('.select');

        $select.on('click', function(e) {
            $(this).addClass('select-on');
            e.stopPropagation();
        })
        $select.on('click', '.option', function(e) {
            var val = $(this).data('value'),
                    $this = $(this);

            $select.find('.hd').html($this.html());
            $(this).addClass('hover').parent().next().val(val);
            setTimeout(function() {
                $this.removeClass('hover');
                $select.removeClass('select-on');
            }, 100);

            e.stopPropagation();

        })
        $('body').on('click', function() {
            $select.removeClass('select-on');
        })



    })


</script>

</body>
</html>