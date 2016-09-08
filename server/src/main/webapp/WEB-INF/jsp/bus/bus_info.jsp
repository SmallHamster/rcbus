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
    <title>车辆信息</title>
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
                            班车信息
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >车型：</label>
                                    <div class="col-sm-6">
                                        ${bus.modelNo}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >品牌：</label>
                                    <div class="col-sm-6">
                                        ${bus.brand}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >座位数：</label>
                                    <div class="col-sm-6">
                                        ${bus.seatNum}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >车牌号：</label>
                                    <div class="col-sm-6">
                                        ${bus.carNo}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >保险单号：</label>
                                    <div class="col-sm-6">
                                        ${bus.policyNo}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >司机姓名：</label>
                                    <div class="col-sm-6">
                                        ${bus.driverName}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >司机电话：</label>
                                    <div class="col-sm-6">
                                        ${bus.driverPhone}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >司机身份证号：</label>
                                    <div class="col-sm-6">
                                        ${bus.driverIDCard}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >司机性别：</label>
                                    <div class="col-sm-6">
                                        ${bus.driverSex}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >用车类型：</label>
                                    <div class="col-sm-6">
                                        ${bus.carType.name}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >图片：</label>
                                    <div class="col-sm-6">
                                        <img src="${bus.imgUrl}" style="width: 100px; height: 100px;">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-6">
                                        <button type="button" onclick="$bus.fn.back()" class="btn btn-primary">返回</button>
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

    $bus = {
        v: {
            list: [],
            chart: null,
            dTable: null,
            imageSize: 0
        },
        fn: {
            back: function(){
                window.location.href = "${contextPath}/admin/bus/index";
            }
        }
    }

</script>
</body>
</html>
