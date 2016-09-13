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
    <title>礼券列表</title>
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
                                <input type="text" id="carNo" class="form-control" placeholder="车牌号">
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="modelNo" class="form-control" placeholder="车型">
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="driverName" class="form-control" placeholder="司机姓名">
                            </div>
                            <button id="c_search" class="btn btn-info"><i class="fa fa-search"></i> 搜索</button>
                            <button id="c_clear" class="btn btn-info"><i class="fa fa-recycle"></i> 清空</button>
                        </div>
                    </section>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel">
                        <header class="panel-heading">
                            礼券列表
                            <span class="tools pull-right" style="margin-right: 10px;margin-left: 10px">
                               <button class="btn btn-info" type="button" onclick="$bus.fn.delete();" id="deleteBatch" style="display: none">
                                   <i class="fa fa-trash-o"></i> 删除</button>
                            </span>
                            <span class="tools pull-right">
                               <button class="btn btn-default " type="button"><i class="fa fa-refresh"></i> 刷新</button>
                               <button class="btn btn-info" type="button" onclick="$bus.fn.add();"><i class="fa fa-plus"></i> 新增车辆</button>
                            </span>
                        </header>
                        <div class="panel-body">
                            <div class="adv-table">
                                <table class="display table table-bordered table-striped" id="dataTables" width="100%">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" class="list-parent-check"
                                                   onclick="$leoman.checkAll(this);"/></th>
                                        <th>名称</th>
                                        <th>获得条件</th>
                                        <th>优惠方式</th>
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
<%@ include file="../confirm.jsp" %>
<script>
    $bus = {
        v: {
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $bus.fn.dataTableInit();
                $("#c_search").click(function () {
                    $bus.v.dTable.ajax.reload();
                });
                $("#c_clear").click(function () {
                    $(this).parents(".panel-body").find("input,select").val("");
                });
            },
            dataTableInit: function () {
                $bus.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort": false,
                    "ajax": {
                        "url": "${contextPath}/admin/coupon/list",
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
                        {"data": "name"},
                        {
                            "data": "gainWay",
                            "render": function (data) {

                                var str = "";
                                if(data == 1){
                                    str = "好友分享";
                                }else if(data == 2){
                                    str = "订单完成后";
                                }else if(data == 3){
                                    str = "注册后";
                                }
                                return str;
                            }
                        },
                        {
                            "data": "couponWay",
                            "render": function (data) {

                                var str = "";
                                if(data == 1){
                                    str = "折扣";
                                }else if(data == 2){
                                    str = "减免金额";
                                }
                                return str;
                            }
                        },
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {

                                var edit = "<button title='编辑' class='btn btn-primary btn-circle edit' onclick=\"$bus.fn.add(\'" + data + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 编辑</button>";

                                var del = "<button title='删除' class='btn btn-primary btn-circle edit' onclick=\"$bus.fn.delete(\'" + data + "\')\">" +
                                        "<i class='fa fa-trash-o'></i> 删除</button>";

                                return edit + "&nbsp;"+ del;
                            }
                        }
                    ]
                });
            },
            add: function (id) {
                var params = "";
                if (id != null && id != '') {
                    params = "?id=" + id;
                }
                window.location.href = "${contextPath}/admin/coupon/add" + params;
            },
            delete: function (id) {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = [];
                if(id != null){
                    ids.push(id);
                }else{
                    ids = checkBox.getInputId();
                }
                $("#confirm").modal("show");
                $('#showText').html('您确定要删除吗？');
                $("#determine").off("click");
                $("#determine").on("click",function(){
                    $.ajax({
                        "url": "${contextPath}/admin/bus/delete",
                        "data": {
                            ids:JSON.stringify(ids)
                        },
                        "dataType": "json",
                        "type": "POST",
                        success: function (result) {
                            if (result==1) {
                                alert("删除错误");
                            }else if(result==2){
                                alert("超级管理员无法删除");
                            }else {
                                $bus.v.dTable.ajax.reload(null,false);
                            }
                            $("#confirm").modal("hide");
                        }
                    });
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        $bus.v.dTable.ajax.reload(null, false);
                    } else {
                        $bus.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        $bus.fn.init();
    })
</script>
</body>
</html>
