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
                            订单处理
                        </header>
                        <div class="panel-body">
                            <input type="hidden" id="rwType" value="${carRental.rentalWay}">
                            <input type="hidden" id="isInvoice" value="${carRental.isInvoice}">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <input id="id" name="id" type="hidden" value="">
                                <header class="panel-heading">
                                    客人信息
                                </header>
                                <div class="form-group" style="margin-top: 15px">
                                    <label for="userName" class="col-sm-2 control-label">用车联系人</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="userName" name="userName" value="${carRental.order.userName}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-2 control-label">联系电话</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="mobile" name="mobile" value="${carRental.order.mobile}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <header class="panel-heading">
                                    出发信息
                                </header>
                                <div class="form-group" style="margin-top: 15px">
                                    <label for="city" class="col-sm-2 control-label">出发城市</label>
                                    <div class="col-sm-6">
                                        <select class="selectpicker show-tick form-control" data-live-search="true" id="city" name="city" required>
                                            <c:forEach items="${city}" var="v">
                                                <option value="${v.id}">${v.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">包车方式</label>
                                    <div class="col-sm-9 icheck " id="rw">
                                        <div class="square-red single-row">
                                            <div class="radio ">
                                                <input tabindex="3" type="radio"  name="rwType" value="0">
                                                <label>单程 </label>
                                            </div>
                                        </div>
                                        <div class="square-yellow  single-row">
                                            <div class="radio ">
                                                <input tabindex="3" type="radio"  name="rwType" value="1">
                                                <label>返程 </label>
                                            </div>
                                        </div>
                                        <div class="square-blue  single-row">
                                            <div class="radio ">
                                                <input tabindex="3" type="radio"  name="rwType" value="2">
                                                <label>往返 </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="startPoint" class="col-sm-2 control-label">起点</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="startPoint" name="startPoint" value="${carRental.startPoint}" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group" >
                                    <label for="endPoint" class="col-sm-2 control-label">终点</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="endPoint" name="endPoint" value="${carRental.endPoint}" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="startDate" class="col-sm-2 control-label">出发时间</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="startDate" name="startDate" value="<date:date format='yyyy-MM-dd HH:mm:ss' value='${carRental.startDate}'></date:date>" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group" id="endDate_hidden" style="display: none">
                                    <label for="endDate" class="col-sm-2 control-label">回城时间</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="endDate" name="endDate" value="<date:date format='yyyy-MM-dd HH:mm:ss' value='${carRental.endDate}'></date:date>" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="carType" class="col-sm-2 control-label">车型</label>
                                    <div class="col-sm-6">
                                        <select id="carType" name="carType" class="form-control input-sm" required >
                                            <c:forEach items="${carType}" var="v">
                                                <option value="${v.id}">${v.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="totalNumber" class="col-sm-2 control-label">总人数</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="totalNumber" name="totalNumber" value="${carRental.totalNumber}" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="busNum" class="col-sm-2 control-label">车辆数</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="busNum" name="busNum" value="${carRental.busNum}" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">发票</label>
                                    <div class="col-sm-9 icheck " id="inv">
                                        <div class="square-red single-row">
                                            <div class="radio ">
                                                <input tabindex="3" type="radio"  name="isInvoice" value="0">
                                                <label>无 </label>
                                            </div>
                                        </div>
                                        <div class="square-yellow  single-row">
                                            <div class="radio ">
                                                <input tabindex="3" type="radio"  name="isInvoice" value="1">
                                                <label>有 </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group" id="invoice_hidden" style="display: none">
                                    <label for="endDate" class="col-sm-2 control-label">发票抬头</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="invoice" name="invoice" value="${carRental.invoice}" class="form-control" required/>
                                    </div>
                                </div>

                                <header class="panel-heading">
                                    报价信息
                                </header>
                                <div style="margin-top: 15px"></div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-2 control-label">收费名称</label>
                                    <div class="col-sm-3">
                                        <input type="text" id="offter_name" name="offter_name" value="" class="form-control" />
                                    </div>
                                    <button type="button" onclick="$admin.fn.addOffter()" class="btn btn-primary"><i class='fa fa-plus-circle'></i></button>
                                </div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-2 control-label">金额</label>
                                    <div class="col-sm-3">
                                        <input type="text" id="offter_amount" name="offter_amount" value="" class="form-control" />
                                    </div>
                                </div>
                                <div id="offterDiv" >

                                </div>
                                <header class="panel-heading">
                                    车辆派遣
                                </header>
                                <div style="margin-top: 15px"></div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-6">
                                        <div class="form-group col-sm-2">
                                            <input type="text" id="a" name="mobile" class="form-control" placeholder="手机号">
                                        </div>
                                        <button id="c_search" class="btn btn-info" style="float: right">搜索</button>
                                        <div class="adv-table">
                                            <table class="display table table-bordered table-striped" id="dataTables" width="100%">
                                                <thead>
                                                <tr>
                                                    <th>车牌号</th>
                                                    <th>司机姓名</th>
                                                    <th>车型</th>
                                                    <th>用车类型</th>
                                                    <th>操作</th>
                                                </tr>
                                                </thead>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <%--</div>--%>
                                <div class="form-group" style="margin-top: 15px">
                                    <label class="col-sm-2 control-label"></label>
                                    <div class="col-sm-6">
                                        <button type="button" onclick="$admin.fn.save()" class="btn btn-primary">保存</button>
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
                //下拉框
                $('#city').selectpicker();

                $("#rw .iCheck-helper").click(function(){
                    $admin.fn.endDateChk($("input[name='rwType']:checked").val());
                });

                $("#inv .iCheck-helper").click(function(){
                    $admin.fn.invoiceChk($("input[name='isInvoice']:checked").val());
                });

                $admin.fn.endDateChk($("#rwType").val());


                $admin.fn.invoiceChk($("#invoice").val());
            },
            dataTableInit: function () {
                $route.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort": false,
                    "ajax": {
                        "url": "${contextPath}/admin/bus/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "carNo"},
                        {"data": "driverName"},
                        {"data": "modelNo"},
                        {"data": "carType.name"},
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {

                                var dispatch = "<button title='派遣' class='btn btn-primary btn-circle edit' onclick=\"$route.fn.dispatch(\'" + data + "\')\">" +
                                        "<i class='fa fa-check'></i> 派遣</button>";

                                return dispatch;
                            }
                        }
                    ]
                });
            },
            addOffter: function(){
                var html = "";
                html += " <div style='margin-bottom: 30px'>																											 ";
                html += " <div class='form-group'>																											 ";
                html += " 	<label for='mobile' class='col-sm-2 control-label'>收费名称</label>                                                              ";
                html += " 	<div class='col-sm-3'>                                                                                                           ";
                html += " 		<input type='text' id='offter_name' name='offter_name' value='' class='form-control' />                                      ";
                html += " 	</div>                                                                                                                           ";
                html += " 	<button type='button' onclick='$admin.fn.delOffter(this)' class='btn btn-primary'><i class='fa fa-minus-circle'></i></button>     ";
                html += " </div>                                                                                                                              ";
                html += " <div class='form-group'>                                                                                                            ";
                html += " 	<label for='mobile' class='col-sm-2 control-label'>金额</label>                                                                  ";
                html += " 	<div class='col-sm-3'>                                                                                                           ";
                html += " 		<input type='text' id='offter_amount' name='offter_amount' value='' class='form-control' />                                  ";
                html += " 	</div>                                                                                                                           ";
                html += " </div>                                                                                                                              ";
                html += " </div>                                                                                                                              ";
                $("#offterDiv").append(html);
            },
            delOffter: function(data) {
                $(data).parent().parent().remove()
            },
            endDateChk: function(data){
                if(data==2){
                    $("#endDate_hidden").css('display','block');
                }else {
                    $("#endDate_hidden").css('display','none');
                }
            },
            invoiceChk: function(data){
                if(data==1){
                    $("#invoice_hidden").css('display','block');
                }else {
                    $("#invoice_hidden").css('display','none');
                }
            },
            save : function() {
                if(!$("#formId").valid()) return;
                $("#formId").ajaxSubmit({
                    url : "${contextPath}/admin/carrental/save",
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            window.location.href = "${contextPath}/admin/carrental/index";
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
