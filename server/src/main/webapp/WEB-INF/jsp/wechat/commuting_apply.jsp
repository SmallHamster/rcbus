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
    <link rel="icon" href="${contextPath}/wechat-html/favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
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
                <input type="text" class="ipt" name="userName" value="" notnull="请输入联系人" maxlength="30"/>
            </div>

            <div class="item">
                <label class="for">联系方式</label>
                <input type="text" class="ipt" name="mobile" value=""  notnull="请输入联系方式" maxlength="11"/>
            </div>

            <div class="item">
                <label class="for">乘车人数</label>
                <input type="text" class="ipt" name="num" value="0"  notnull="请输入乘车人数" maxlength="10"/>
            </div>

            <div class="item">
                <label class="for">出发地点</label>
                <input type="text" class="ipt" name="startPoint" value=""  notnull="请输入出发地点" maxlength="100"/>
            </div>

            <div class="item">
                <label class="for">目的地</label>
                <input type="text" class="ipt" name="endPoint" value=""  notnull="请输入目的地" maxlength="100"/>
            </div>

            <div class="item">
                <label class="for">出发时间</label>
                <input type="text" class="ipt" name="startDate" value=""  maxlength="10"/>
            </div>

            <div class="item">
                <label class="for">到达时间</label>
                <input type="text" class="ipt" name="endDate" value=""  maxlength="10"/>
            </div>

            <div class="item">
                <label class="for">返程时间</label>
                <input type="text" class="ipt" name="returnDate" value=""  maxlength="10"/>
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
                    <input type="hidden" name="status" value="1">
                </div>
            </div>

            <div class="item">
                <label class="for">其他备注</label>
                <textarea name="remark" id="" class="ipt ipt-mul" cols="30" rows="10"></textarea>
            </div>
        </div>

        <div class="button">
            <button type="button" class="ubtn ubtn-blue" id="submit" onclick="save()">确认</button>
        </div>
    </form>
</section>


<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<script src="${contextPath}/wechat-html/js/layer/layer.js"></script>
<%@ include file="../inc/new2/foot.jsp" %>
<script type="text/javascript">

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


    function save(){

        var flag = true;
        $("#formId input[notnull]").each(function(){
            if($(this).val() == ''){
                alertMsg($(this).attr("notnull"));
                flag = false;
                return false;
                return ;
            }
        });

        if(flag){

            if(!/^[0-9]*$/.test($("[name=num]").val())){
                alertMsg("请输入数字");
                return ;
            }

            if(!/^1[3-9]\d{9}$/.test($("[name=mobile]").val())){
                alertMsg("请输入正确的手机号");
                return ;
            }

            $("#formId").ajaxSubmit({
                url : "${contextPath}/wechat/commuting/save",
                type : "POST",
                success : function(result) {

                    if(result.status == 0) {
                        /*alertMsg("申请成功");
                        $("#formId input,#formId textarea").val("");*/
                        location.href = "${contextPath}/wechat/user/index";
                    }else {
                        alertMsg("申请失败");
                    }
                }
            });
        }
    }


</script>

</body>
</html>