<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">
    <title>编辑礼券</title>
    <%@ include file="../inc/new2/css.jsp" %>
</head>

<body class="sticky-header">

<section>
    <%@ include file="../inc/new2/menu.jsp" %>
    <!-- main content start-->
    <div class="main-content">
        <%@ include file="../inc/new2/header.jsp" %>
        <!--body wrapper start-->
        <section class="wrapper">
            <!-- page start-->

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            编辑礼券
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <input name="id" type="hidden" value="${coupon.id}">
                                <input name="gainWay" type="hidden" value="${coupon.gainWay}">

                                <div class="form-group">
                                    <label class="col-sm-1 control-label" ><span style="color: red;">* </span>礼券名称：</label>
                                    <div class="col-sm-3">
                                        <input type="text" name="name" value="${coupon.name}" class="form-control" required maxlength="20"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label">获得方式：</label>
                                    <div class="col-sm-3">
                                        <c:if test="${coupon.gainWay == 1}">好友分享</c:if>
                                        <c:if test="${coupon.gainWay == 2}">订单完成后</c:if>
                                        <c:if test="${coupon.gainWay == 3}">注册后</c:if>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label">优惠方式：</label>
                                    <div class="col-sm-1">
                                        <input type="radio" name="couponWay" value="1" checked="checked">
                                        折扣
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="text" name="discountPercent" value="${coupon.discountPercent}" class="form-control" placeholder="输入折扣" number-2="true"/>
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="text" name="discountTopMoney" value="${coupon.discountTopMoney}" class="form-control" placeholder="最高立减金额" number-2="true"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-1">
                                        <input type="radio" name="couponWay" value="2" checked="">
                                        减免金额
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="text" name="reduceMoney" value="${coupon.reduceMoney}" class="form-control" placeholder="输入减免金额" number-2="true"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label">消费要求：</label>
                                    <div class="col-sm-1">
                                        <input type="radio" name="isLimit" value="0" checked="checked">
                                        否
                                        <input type="radio" name="isLimit" value="1">
                                        是
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="text" name="limitMoney" value="${coupon.limitMoney}" class="form-control" placeholder="请输入使用要求消费金额" style="display: none;" number-2="true"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >有效期从：</label>
                                    <div class="col-sm-1">
                                        <input type="text" name="validDateFrom1" class="form-control input-append date form_datetime" style="width: 180px;" readonly maxlength="20" value="">
                                    </div>
                                    <label class="col-sm-1 control-label" >至：</label>
                                    <div class="col-sm-2">
                                        <input type="text" name="validDateTo1" class="form-control input-append date form_datetime" style="width: 180px;" readonly maxlength="20" value="">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-6">
                                        <button type="button" onclick="$coupon.fn.save()" class="btn btn-primary">保存</button>
                                        <button type="button" onclick="$coupon.fn.back()" class="btn btn-primary">返回</button>
                                    </div>
                                </div>

                            </form>
                        </div>
                    </section>
                </div>
            </div>
        </section>
    </div>
    <!-- main content end-->
</section>
<%@ include file="../inc/new2/foot.jsp" %>
<script>
    $coupon = {
        v: {
            list: [],
            chart: null,
            dTable: null,
            imageSize: 0
        },
        fn: {
            init: function () {

                //初始化时间控件的值
                if("${coupon.validDateFrom}" != null && "${coupon.validDateFrom}" != 0){
                    var date1 = new Date(parseInt("${coupon.validDateFrom}"));
                    $("[name=validDateFrom1]").val(date1.format('yyyy-MM-dd hh:mm'));
                }

                if("${coupon.validDateFrom}" != null && "${coupon.validDateTo}" != 0){
                    var date2 = new Date(parseInt("${coupon.validDateTo}"));
                    $("[name=validDateTo1]").val(date2.format('yyyy-MM-dd hh:mm'));
                }

                //是否有消费要求
                $("input[name=isLimit]").click(function(){
                    if($(this).val() == 1){
                        $("[name=limitMoney]").show();
                    }else {
                        $("[name=limitMoney]").hide();
                    }
                });

                //初始化优惠方式
                $("input[name=couponWay][value="+"${coupon.couponWay}"+"]").click();

                //初始化消费要求
                $("input[name=isLimit][value="+"${coupon.isLimit}"+"]").click();

                //时间控件初始化
                $('.form_datetime').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 2,
                    forceParse: 0,
                    showMeridian: false,
//                    minView: "month",

                    format: 'yyyy-mm-dd hh:ii'
                });
            },
            back : function(){
                window.location.href = "${contextPath}/admin/coupon/index";
            },
            //保存
            save : function() {
                if(!$("#formId").valid()) return;

                //优惠方式
                var couponWay = $("[name=couponWay]:checked").val();
                if(couponWay == 1){
                    if($("[name=discountPercent]").val() == '' || $("[name=discountPercent]").val() == 0){
                        $leoman.alertMsg("折扣金额不能为空");
                        return ;
                    }
                }else if(couponWay == 2){
                    if($("[name=reduceMoney]").val() == ''){
                        $leoman.alertMsg("减免金额不能为空");
                        return ;
                    }
                }

                //消费要求
                var isLimit = $("[name=isLimit]:checked").val();
                if(isLimit == 1 && $("[name=limitMoney]").val() == ''){
                    $leoman.alertMsg("请输入使用要求消费金额");
                    return ;
                }

                if($("[name=validDateFrom1]").val() != '' && $("[name=validDateTo1]").val() != ''){
                    if($("[name=validDateFrom1]").val() > $("[name=validDateTo1]").val()){
                        $leoman.alertMsg("起始时间不能大于结束时间");
                        return ;
                    }
                }

                $("#formId").ajaxSubmit({
                    url : "${contextPath}/admin/coupon/save",
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            window.location.href = "${contextPath}/admin/coupon/index";
                        }
                        else {
                            $leoman.alertMsg('操作失败');
                        }
                    }
                })
            }
        }
    }
    $(function () {
        $coupon.fn.init();
    })
</script>
</body>
</html>
