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
    <style type="text/css">

        .border{
            background-color: #EEE;
            margin:20px 0px 20px 120px;
            width:500px;
            height:160px;
            border:1px solid #CCCCCC;
            -moz-border-radius: 15px;
            -webkit-border-radius: 15px;
            border-radius:15px;
            float: left;
        }
        .pl{
            margin-left: 20px;
            margin-top: 15px;
            font-size: 20px
        }
        .pr{
            margin-right: 20px;
            margin-top: 15px;
            font-size: 20px
        }


    </style>
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
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <input id="id" name="id" type="hidden" value="${carRental.id}">
                                <header class="panel-heading">
                                    出发信息
                                </header>
                                <div class="form-group" style="margin-top: 15px">
                                    <label for="city" class="col-sm-2 control-label">出发城市</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="city" name="city" value="${carRental.city.name}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="rentalWay" class="col-sm-2 control-label">包车方式</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="rentalWay" name="rentalWay" value="<c:if test="${carRental.rentalWay eq 0}">单程</c:if><c:if test="${carRental.rentalWay eq 1}">返程</c:if><c:if test="${carRental.rentalWay eq 2}">往返</c:if>" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="startPoint" class="col-sm-2 control-label">起点</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="startPoint" name="startPoint" value="${carRental.startPoint}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="endPoint" class="col-sm-2 control-label">终点</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="endPoint" name="endPoint" value="${carRental.endPoint}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="startDate" class="col-sm-2 control-label">出发时间</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="startDate" name="startDate" value="<date:date format='yyyy-MM-dd HH:mm:ss' value='${carRental.startDate}'></date:date>" class="form-control" disabled/>
                                    </div>
                                </div>
                                <c:if test="${carRental.rentalWay eq 2}">
                                    <div class="form-group">
                                        <label for="endDate" class="col-sm-2 control-label">回城时间</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="endDate" name="endDate" value="<date:date format='yyyy-MM-dd HH:mm:ss' value='${carRental.endDate}'></date:date>" class="form-control" disabled/>
                                        </div>
                                    </div>
                                </c:if>
                                <div class="form-group">
                                    <label for="carType" class="col-sm-2 control-label">车型</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="carType" name="carType" value="${carRental.carType.name}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="totalNumber" class="col-sm-2 control-label">总人数</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="totalNumber" name="totalNumber" value="${carRental.totalNumber}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="busNum" class="col-sm-2 control-label">车辆数</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="busNum" name="busNum" value="${carRental.busNum}" class="form-control" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="invoice" class="col-sm-2 control-label">发票</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="invoice" name="invoice" value="<c:if test="${carRental.isInvoice eq 0}">无</c:if><c:if test="${carRental.isInvoice eq 1}">有(${carRental.invoice})</c:if>" class="form-control" disabled/>
                                    </div>
                                </div>
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
                                    报价信息
                                </header>
                                <div style="margin-top: 15px"></div>

                                <div id="offterDiv" >
                                    <c:forEach var="v" items="${carRentalOffer}">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">收费名称</label>
                                            <div class="col-sm-3">
                                                <input type="text" name="offter_name" value="${v.name}" class="form-control" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">金额</label>
                                            <div class="col-sm-3">
                                                <input type="text" name="offter_amount" value="${v.amount}" class="form-control" onblur="count()"/>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <div>
                                    <div class="form-group"  style='margin-top: 30px'>
                                        <label class="col-sm-2 control-label">总金额</label>
                                        <div class="col-sm-3">
                                            <input type="text" id="totalAmount" name="totalAmount" value="${totalAmount}" class="form-control" disabled/>
                                        </div>
                                        <button type="button" onclick="$admin.fn.addOffter()" class="btn btn-primary"><i class='fa fa-plus-circle'></i></button>
                                    </div>
                                </div>

                                <c:if test="${carRental.order.status eq 4}">
                                    <div class="form-group">
                                        <label for="mobile" class="col-sm-2 control-label">取消原因</label>
                                        <div class="col-sm-3">
                                            <input type="text" id="unsubscribe" name="unsubscribe" value="${carRental.unsubscribe}" class="form-control" disabled/>
                                        </div>
                                    </div>
                                </c:if>

                                <header class="panel-heading">
                                    车辆信息
                                </header>
                                <div style="margin-top: 15px" id="car_rental">
                                    <c:forEach items="${busSend}" var="v">
                                        <div class='form-group'>
                                            <input type='hidden' value='${v.bus.id}' name='dispatch'>
                                            <label class='col-sm-2 control-label'></label>
                                            <div class='col-sm-3'>
                                                <input type='text' id='d' name='d' value='&nbsp;${v.bus.driverName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${v.bus.carNo}&nbsp;&nbsp;&nbsp;&nbsp;${v.bus.seatNum}座' class='form-control' disabled />
                                            </div>
                                            <button type='button' onclick='$admin.fn.delDispatch(this)' class='btn btn-primary'><i class='fa fa-minus-circle'></i></button>
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="form-group" style="margin-top: 15px">
                                    <label class="col-sm-2 control-label"></label>
                                    <div class="col-sm-6">
                                        <button type="button" onclick="$admin.fn.openDispatch()" class="btn btn-info"><i class='fa fa-plus'></i> 新增派遣车辆</button>
                                    </div>
                                </div>
                                <%--</div>--%>
                                <div class="form-group">
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
<%@ include file="carRental.jsp" %>
<script>
    $admin = {
        v: {
            list: [],
            dispatch: [],
            offter_name: [],
            offter_amount: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {
                $("#formId").validate();
                //表格初始化
                $admin.fn.dataTableInit();

                //搜索
                $("#c_search").on("click",function () {
                    $admin.v.dTable.ajax.reload(null,false);
                });

                //清空
                $("#c_clear").click(function () {
                    $(this).parents(".modal-body").find("input,select").val("");
                });

                $admin.v.dispatch = [];
                $("#car_rental input[name=dispatch]").each(function(){
                    $admin.v.dispatch.push($(this).val());
                });

            },
            dataTableInit: function () {
                $admin.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort": false,
                    "ajax": {
                        "url": "${contextPath}/admin/bus/list",
                        "type": "POST"
                    },
                    "columns": [
                        {
                            "data": "id",
                            "render": function (data) {
                                var checkbox = "<input type='checkbox' class='list-check' onclick='$leoman.subSelect(this);' value=" + data + ">";
                                return checkbox;
                            }
                        },
                        {"data": "carNo"},
                        {"data": "driverName"},
                        {"data": "modelNo"},
                        {"data": "carType.name"},
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {

                                var dispatch = "<button title='派遣' class='btn btn-primary btn-circle edit' onclick=\"$admin.fn.dispatch(\'" + row.driverName + "\',\'" + row.carNo + "\',\'" + row.seatNum + "\',\'" + data + "\')\">" +
                                        "<i class='fa fa-check'></i> 派遣</button>";

                                return dispatch;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.carNo = $("#carNo").val();
                        aoData.driverName = $("#driverName").val();
                        aoData.carType1 = $("#carType1").val();
                    }
                });
            },


            addOffter: function(){
                var html = "";
                html += " <div style='margin-bottom: 30px'>																											 ";
                html += " <div class='form-group'>																											 ";
                html += " 	<label class='col-sm-2 control-label'>收费名称</label>                                                              ";
                html += " 	<div class='col-sm-3'>                                                                                                           ";
                html += " 		<input type='text' id='offter_name' name='offter_name' value='' class='form-control' />                                      ";
                html += " 	</div>                                                                                                                           ";
                html += " 	<button type='button' onclick='$admin.fn.delOffter(this)' class='btn btn-primary'><i class='fa fa-minus-circle'></i></button>     ";
                html += " </div>                                                                                                                              ";
                html += " <div class='form-group'>                                                                                                            ";
                html += " 	<label class='col-sm-2 control-label'>金额</label>                                                                  ";
                html += " 	<div class='col-sm-3'>                                                                                                           ";
                html += " 		<input type='text' id='offter_amount' name='offter_amount' value='' class='form-control' onblur=\"count()\"/>                                  ";
                html += " 	</div>                                                                                                                           ";
                html += " </div>                                                                                                                              ";
                html += " </div>                                                                                                                              ";
                $("#offterDiv").append(html);
            },

            delOffter: function(data) {
                $(data).parent().parent().remove()
            },

            dispatch: function (driverName,carNo,seatNum,id) {

                driverName = driverName =='null' ? '暂无' : driverName;
                carNo = carNo =='null' ? '暂无' : carNo;
                seatNum = seatNum =='null' ? '0' : seatNum;

                var dispatchs = $admin.v.dispatch;
                for(var i=0;i<dispatchs.length;i++){
                    if(dispatchs[i] == id){
                        alert("不能多次派遣同一辆车!");
                        return;
                    }
                }

                var html = "";
                html += " <div class='form-group'>																																							";
                html += " <input type='hidden' value='"+id+"' name='dispatch'>																																							";
                html += " 	<label class='col-sm-2 control-label'></label>                                                                                                                     ";
                html += " 	<div class='col-sm-3'>                                                                                                                                                          ";
                html += " 		<input type='text' id='d' name='d' value='&nbsp;"+driverName+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+carNo+"&nbsp;&nbsp;&nbsp;&nbsp;"+seatNum+"' class='form-control' disabled />  ";
                html += " 	</div>                                                                                                                                                                          ";
                html += " 	<button type='button' onclick='$admin.fn.delDispatch(this)' class='btn btn-primary'><i class='fa fa-minus-circle'></i></button>                                                   ";
                html += " </div>                                                                                                                                                                            ";

                $("#car_rental").append(html);

                $admin.v.dispatch = [];
                $("#car_rental input[name=dispatch]").each(function(){
                    $admin.v.dispatch.push($(this).val());
                });

            },
            delDispatch: function(data) {
                var id = $(data).parent().find('input').eq(0).val();
                var dispatchs = $admin.v.dispatch;
                for(var i=0;i<dispatchs.length;i++){
                    if(dispatchs[i]==id){
                        $admin.v.dispatch.splice(i, 1);
                    }
                }
                $(data).parent().remove();
            },
            openDispatch: function () {
                $("#myModal").modal("show");
            },


            save : function() {
                if(!$("#formId").valid()) return;
                //参数
                var id = $("#id").val();

                $admin.v.offter_name = [];
                $("#offterDiv input[name=offter_name]").each(function(){
                    console.log($(this).val())
                    $admin.v.offter_name.push($(this).val());
                });

                $admin.v.offter_amount = [];
                $("#offterDiv input[name=offter_amount]").each(function(){
                    console.log($(this).val())
                    $admin.v.offter_amount.push($(this).val());
                });

                $.ajax({
                    url : "${contextPath}/admin/carRental/saveDispatch",
                    data: {
                        "id" : id ,
                        "dispatch": JSON.stringify($admin.v.dispatch),
                        "offter_name": JSON.stringify($admin.v.offter_name),
                        "offter_amount": JSON.stringify($admin.v.offter_amount)
                    },
                    type : "POST",
                    success : function(result) {
                        if(result == 0) {
                            window.location.href = "${contextPath}/admin/carRental/index";
                        }
                        else {
                            alert("操作失败");
                        }
                    }
                });
            }
        }
    };
    $(function () {
        $admin.fn.init();
    })

    function count(){
        var num = 0;
        $("#offterDiv input[name=offter_amount]").each(function(){
            console.log($(this).val())
            num += parseFloat($(this).val());

        });
        $("#totalAmount").val(num.toFixed(2));
    }

</script>
</body>
</html>
