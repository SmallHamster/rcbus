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
    <title>Dynamic Table</title>
    <%@ include file="../inc/new2/css.jsp" %>
</head>
<body class="sticky-header">
<section>
    <%@ include file="../inc/new2/menu.jsp" %>
    <!-- main content start-->
    <div class="main-content">
        <%@ include file="../inc/new2/header.jsp" %>
        <!--body wrapper start-->
        <div class="wrapper">
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel">
                        <div class="panel-body">
                            <div class="form-group col-sm-2">
                                <input type="text" id="orderNo" name="orderNo" class="form-control" placeholder="订单号">
                            </div>
                            <div class="form-group col-sm-2">
                                <select id="orderStatus" name="orderStatus" class="form-control input-sm">
                                    <option value="">订单状态</option>
                                    <option value="0">待审核</option>
                                    <option value="1">待付款</option>
                                    <option value="2">进行中</option>
                                    <option value="3">已完成</option>
                                    <option value="4">已取消</option>
                                </select>
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="carNo" name="carNo" class="form-control" placeholder="车牌号码">
                            </div>

                            <div class="form-group col-sm-2">
                                <input type="text" id="userName" name="userName" class="form-control" placeholder="客人姓名">
                            </div>

                            <div class="form-group col-sm-2">
                                <input type="text" id="start" name="start" class="form-control input-append date form_datetime" placeholder="发车时间(大于)">
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="end" name="end" class="form-control input-append date form_datetime" placeholder="发车时间(小于)">
                            </div>
                            <div class="form-group col-sm-2">
                                <button id="c_search" class="btn btn-info">搜索</button>
                                <button id="c_clear" class="btn btn-info"><i class="fa fa-recycle"></i> 清空</button>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel">
                        <header class="panel-heading">
                            会员列表
                            <span class="tools pull-right">
                               <button class="btn btn-default " type="button" id="c_refresh"><i class="fa fa-refresh"></i>刷新</button>
                            </span>
                        </header>
                        <div class="panel-body">
                            <div class="adv-table">
                                <table class="display table table-bordered table-striped" id="dataTables" width="100%">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" class="list-parent-check"
                                                   onclick="$leoman.checkAll(this);"/></th>
                                        <th>订单号</th>
                                        <th>客人姓名</th>
                                        <th>联系电话</th>
                                        <th>发车时间</th>
                                        <th>出发城市</th>
                                        <th>包车方式</th>
                                        <th>订单状态</th>
                                        <th>下单时间</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</section>
<%@ include file="../inc/new2/foot.jsp" %>
<%@ include file="../inc/new2/confirm.jsp" %>
<script>
    $carRental = {
        v: {
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $carRental.fn.dataTableInit();
                $("#c_search").click(function () {
                    $carRental.v.dTable.ajax.reload();
                });
                //清空
                $("#c_clear").click(function () {
                    $(this).parents(".panel-body").find("input,select").val("");
                });
                //刷新
                $("#c_refresh").click(function () {
                    $("#c_clear").parents(".panel-body").find("input,select").val("");
                    $carRental.v.dTable.ajax.reload();
                });
                //时间控件初始化
                $('.form_datetime').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
//                    forceParse: 1,
//                    showMeridian: false,
                    minView: "month",
                    format: 'yyyy-mm-dd'
                });
            },
            dataTableInit: function () {
                $carRental.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort": false,
                    "ajax": {
                        "url": "${contextPath}/admin/carRental/list",
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
                        {
                            "data": "order.orderNo",
                            "sDefaultContent" : ""

                        },
                        {
                            "data": "order.userName",
                            "sDefaultContent" : ""

                        },
                        {
                            "data": "order.mobile",
                            "sDefaultContent" : ""

                        },
                        {
                            "data": "startDate",
                            "render": function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                            },
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "city.name",
                            "sDefaultContent" : ""

                        },
                        {
                            "data": "rentalWay",
                            "render": function (data) {
                                if(data==0){
                                    return "单程";
                                }else if(data==1){
                                    return "返程";
                                }else{
                                    return "往返";
                                }
                            },
                            "sDefaultContent" : ""

                        },
                        {
                            "data": "order.status",
                            "render": function (data) {
                                if(data==0){
                                    return "待审核";
                                }else if(data==1){
                                    return "待付款";
                                }else if(data==2){
                                    return "进行中";
                                }else if(data==3){
                                    return "已完成";
                                }else{
                                    return "已取消";
                                }
                            },
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "createDate",
                            "render": function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                            },
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {
                                var timestamp=new Date().getTime();  //获取当前时间
                                var detail = "<button title='查看' class='btn btn-primary btn-circle add' onclick=\"$carRental.fn.detail(\'" + data + "\')\">" +
                                        "<i class='fa fa-eye'></i> 查看</button>";

                                var edit = "<button title='编辑' class='btn btn-primary btn-circle edit' onclick=\"$carRental.fn.edit(\'" + data + "\',\'" + row.order.status + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 编辑</button>";

                                var again = "<button title='重新派车' class='btn btn-primary btn-circle edit' onclick=\"$carRental.fn.edit(\'" + data + "\',\'" + row.order.status + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 重新派车</button>";

                                var del = "<button title='删除' class='btn btn-primary btn-circle edit' onclick=\"$carRental.fn.del(\'" + data + "\')\">" +
                                        "<i class='fa fa-trash-o'></i> 删除</button>";
                                var departure = "<button title='离职' class='btn btn-primary btn-circle edit' onclick=\"$carRental.fn.departure(\'" + data + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 离职</button>";

                                if(row.order.status==0){
                                    return  detail  + "&nbsp;" + edit + "&nbsp;" + del;
                                }else if(row.order.status==1){
                                    return  detail  + "&nbsp;" + again + "&nbsp;" + del;
                                }else if(row.order.status==2){
                                    if(row.startDate > timestamp){
                                        return  detail  + "&nbsp;" + again ;
                                    }else {
                                        return  detail  + "&nbsp;*车辆已开始服务，不能继续派车";
                                    }
                                }else if(row.order.status==3){
                                    return  detail ;
                                }else {
                                    return  detail  + "&nbsp;" + del;
                                }

                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.orderNo = $("#orderNo").val();
                        aoData.orderStatus = $("#orderStatus").val();
                        aoData.carNo = $("#carNo").val();
                        aoData.userName = $("#userName").val();
                        aoData.Dstart = $("#start").val();
                        aoData.Dend = $("#end").val();
                    }
                });
            },
            edit: function (id,status) {
                var params = "";
                if (id != null && id != '') {
                    params = "?id=" + id +"&status="+status;
                }
                window.location.href = "${contextPath}/admin/carRental/edit" + params;
            },
            detail: function (id) {
                var params = "";
                if (id != null && id != '') {
                    params = "?id=" + id;
                }
                window.location.href = "${contextPath}/admin/carRental/detail" + params;
            },
            del: function (id) {
                $("#confirm").modal("show");
                $('#showText').html('您确定要彻底删除所选的订单吗？');
                $("#determine").off("click");
                $("#determine").on("click",function(){
                    $.ajax({
                        "url": "${contextPath}/admin/carRental/del",
                        "data": {
                            id:id,
                        },
                        "dataType": "json",
                        "type": "POST",
                        success: function (result) {
                            if (result==1) {
                                alert("删除错误");
                            }else {
                                $("#deleteBatch").css('display','none');
                                $carRental.v.dTable.ajax.reload(null,false);
                            }
                            $("#confirm").modal("hide");
                        }
                    });
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        $carRental.v.dTable.ajax.reload(null, false);
                    } else {
                        $carRental.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        $carRental.fn.init();
    })
</script>
</body>
</html>
