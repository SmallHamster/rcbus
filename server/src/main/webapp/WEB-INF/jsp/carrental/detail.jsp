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
    <title>Form Layouts</title>
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
                            出发信息
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <input id="id" name="id" type="hidden" value="">
                                <div class="form-group">
                                    <label for="city" class="col-sm-1 control-label">出发城市</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="city" name="city" value="${carRental.city.name}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="rentalWay" class="col-sm-1 control-label">包车方式</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="rentalWay" name="rentalWay" value="${carRental.rentalWay}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="startPoint" class="col-sm-1 control-label">起点</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="startPoint" name="startPoint" value="${carRental.startPoint}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="endPoint" class="col-sm-1 control-label">终点</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="endPoint" name="endPoint" value="${carRental.endPoint}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="startDate" class="col-sm-1 control-label">出发时间</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="startDate" name="startDate" value="<date:date format='yyyy-MM-dd HH:mm:ss' value='${carRental.startDate}'></date:date>" class="form-control" disabled/>
                                    </div>
                                </div>
                                <c:if test="${carRental.rentalWay eq 2}">
                                    <div class="form-group">
                                        <label for="endDate" class="col-sm-1 control-label">回城时间</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="endDate" name="endDate" value="<date:date format='yyyy-MM-dd HH:mm:ss' value='${carRental.endDate}'></date:date>" class="form-control" disabled/>
                                        </div>
                                    </div>
                                </c:if>
                                <div class="form-group">
                                    <label for="carType" class="col-sm-1 control-label">车型</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="carType" name="carType" value="${carRental.carType.name}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="totalNumber" class="col-sm-1 control-label">总人数</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="totalNumber" name="totalNumber" value="${carRental.totalNumber}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="busNum" class="col-sm-1 control-label">车辆数</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="busNum" name="busNum" value="${carRental.busNum}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="invoice" class="col-sm-1 control-label">发票</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="invoice" name="invoice" value="<c:if test="${carRental.isInvoice eq 0}">无</c:if><c:if test="${carRental.isInvoice eq 1}">有(${carRental.invoice})</c:if>" class="form-control" disabled/>
                                    </div>
                                </div>
                                <header class="panel-heading">
                                    客人信息
                                </header>
                                <div class="form-group" style="margin-top: 15px">
                                    <label for="userName" class="col-sm-1 control-label">用车联系人</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="userName" name="userName" value="${carRental.order.userName}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-1 control-label">联系电话</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="mobile" name="mobile" value="${carRental.order.mobile}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <%--</div>--%>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-6">
                                        <button type="button" class="btn btn-primary" onclick="history.go(-1);">返回</button>
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
    $admin = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {
                $("#formId").validate();
            },

            save : function() {
                if(!$("#formId").valid()) return;
                $("#formId").ajaxSubmit({
                    url : "${contextPath}/admin/user/save",
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            window.location.href = "${contextPath}/admin/user/index";
                        }
                        else {
                            alert("操作失败");
                        }
                    }
                });
            }
        }
    }
    $(function () {
        $admin.fn.init();
    })
</script>
</body>
</html>
