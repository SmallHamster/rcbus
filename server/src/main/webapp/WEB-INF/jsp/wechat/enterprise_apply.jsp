<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>企业报名入口-江城巴士</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${contextPath}/wechat-html/favicon.ico">

    <link rel="stylesheet" href="${contextPath}/wechat-html/css/app.css">
</head>

<body>

<header>
    <div class="title">班车申请</div>
</header>


<section class="wrap ui-form">
    <h1 class="sub-title">请留下您的联系方式，我们将尽快与您取得联系</h1>
    <div class="banner">
        <img src="${contextPath}/wechat-html/images/banner_apply.jpg" width="100%" />
    </div>

    <form id="formId">
        <div class="form">
            <div class="item">
                <label class="for">联系人</label>
                <input type="text" class="ipt" value="" name="username" maxlength="20" notnull="请输入联系人"/>
            </div>

            <div class="item">
                <label class="for">联系方式</label>
                <input type="text" class="ipt" value="" name="mobile"  maxlength="11" notnull="请输入联系方式"/>
            </div>

            <div class="item">
                <label class="for">企业名称</label>
                <input type="text" class="ipt" value="" name="enterpriseName"  maxlength="30" notnull="请输入企业名称" />
            </div>

            <div class="item">
                <label class="for">企业地址</label>
                <input type="text" class="ipt" value="" name="enterpriseAddress"  maxlength="500"  notnull="请输入企业地址"/>
            </div>

            <div class="item">
                <label class="for">其他备注</label>
                <textarea name="" id="" class="ipt ipt-mul" cols="30" rows="10" name="remark" ></textarea>
            </div>
        </div>

        <div class="button">
            <button type="button" class="ubtn ubtn-blue" id="submit" onclick="$enApply.fn.save()">确认</button>
        </div>
    </form>
</section>

<script src="${contextPath}/wechat-html/js/zepto.min.js"></script>
<%@ include file="../inc/new2/foot.jsp" %>
<script>
    $enApply = {
        v: {
            list: []
        },
        fn: {
            //保存
            save : function() {
                var flag = true;

                $("input[notnull]").each(function(){
                    if($(this).val() == ''){
                        alertMsg($(this).attr("notnull"));
                        flag = false;
                        return false;
                    }
                });

                if(flag){

                    var regPhone = /^1([3578]\d|4[57])\d{8}$/;
                    if(!regPhone.test( $("[name=mobile]").val() )){
                        alertMsg("请输入正确的手机号");
                        flag = false;
                    }

                    $("#formId").ajaxSubmit({
                        url : "${contextPath}/wechat/enterprise/apply/save",
                        type : "POST",
                        success : function(result) {
                            if(result.status == 0) {
                                alertMsg("申请成功");
                                $("#formId input,#formId textarea").val("");
                            }else {
                                alertMsg("申请失败");
                            }
                        }
                    });
                }
            }
        }
    }
</script>

</body>
</html>