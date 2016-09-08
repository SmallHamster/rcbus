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
                                <input type="text" id="mobile" name="mobile" class="form-control" placeholder="订单号">
                            </div>
                            <%--<div class="form-group col-sm-2">--%>
                                <%--<select id="type" name="type" class="form-control input-sm">--%>
                                    <%--<option value="">订单状态</option>--%>
                                    <%--<option value="0">企业管理</option>--%>
                                    <%--<option value="1">员工</option>--%>
                                    <%--<option value="2">普通会员</option>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                            <%--<div class="form-group col-sm-2">--%>
                                <%--<input type="text" id="1" name="1" class="form-control" placeholder="车牌号码">--%>
                            <%--</div>--%>

                            <%--<div class="form-group col-sm-2">--%>
                                <%--<input type="text" id="userName" name="userName" class="form-control" placeholder="客人姓名">--%>
                            <%--</div>--%>

                            <%--<div class="form-group col-sm-2">--%>
                                <%--<input type="text" id="2" name="2" class="form-control" placeholder="出行日期">--%>
                            <%--</div>--%>

                            <button id="c_search" class="btn btn-info">搜索</button>
                        </div>
                    </section>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel">
                        <header class="panel-heading">
                            会员列表
                            <span class="tools pull-right" style="margin-right: 10px;margin-left: 10px">
                               <button class="btn btn-info" type="button" onclick="$carRental.fn.del();" id="deleteBatch" style="display: none">删除</button>
                            </span>
                            <span class="tools pull-right">
                               <button class="btn btn-default " type="button"><i class="fa fa-refresh"></i>刷新</button>
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
                                        <th>出行时间</th>
                                        <th>出发城市</th>
                                        <th>包车方式</th>
                                        <th>订单状态</th>
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
                            "data": "id",
                            "render": function (data, type, row, meta) {
                                var detail = "<button title='查看' class='btn btn-primary btn-circle add' onclick=\"$carRental.fn.detail(\'" + data + "\')\">" +
                                        "<i class='fa fa-eye'></i> 查看</button>";

                                var edit = "<button title='编辑' class='btn btn-primary btn-circle edit' onclick=\"$carRental.fn.edit(\'" + data + "\',\'" + row.order.status + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 编辑</button>";

                                var del = "<button title='删除' class='btn btn-primary btn-circle edit' onclick=\"$carRental.fn.del(\'" + data + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 删除</button>";
                                var departure = "<button title='离职' class='btn btn-primary btn-circle edit' onclick=\"$carRental.fn.departure(\'" + data + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 离职</button>";

                                return  detail  + "&nbsp;" + edit + "&nbsp;" + del;

                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
//                        aoData.mobile = $("#mobile").val();
//                        aoData.enterpriseId = $("#enterpriseId").val();
//                        aoData.type = $("#type").val();
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
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $("#confirm").modal("show");
                $('#showText').html('您确定要彻底删除所选的订单吗？');
                $("#determine").off("click");
                $("#determine").on("click",function(){
                    $.ajax({
                        "url": "${contextPath}/admin/carRental/del",
                        "data": {
                            id:id,
                            ids:JSON.stringify(ids)
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
