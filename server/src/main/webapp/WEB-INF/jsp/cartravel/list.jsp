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
                                <input type="text" id="userName" name="userName" class="form-control" placeholder="申请人">
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="travelName" name="travelName" class="form-control" placeholder="活动名称">
                            </div>

                            <div class="form-group col-sm-2">
                                <input type="text" id="start" name="start" class="form-control input-append date form_datetime" placeholder="出行时间(大于)">
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="end" name="end" class="form-control input-append date form_datetime" placeholder="出行时间(小于)">
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
                            收入明细
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
                                        <th>申请人</th>
                                        <th>联系方式</th>
                                        <th>报名时间</th>
                                        <th>出行时间</th>
                                        <th>报名人数</th>
                                        <th>活动名称</th>
                                        <th>备注信息</th>
                                        <th>备注详情</th>
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
<%@ include file="detail.jsp" %>
<script>
    $carTravel = {
        v: {
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $carTravel.fn.dataTableInit();
                $("#c_search").click(function () {
                    $carTravel.v.dTable.ajax.reload();
                });
                //清空
                $("#c_clear").click(function () {
                    $(this).parents(".panel-body").find("input,select").val("");
                });
                //时间控件初始化
                $('.form_datetime').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    minView: "month",
                    format: 'yyyy-mm-dd'
                });
            },
            dataTableInit: function () {
                $carTravel.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort": false,
                    "ajax": {
                        "url": "${contextPath}/admin/carTravel/list",
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
                            "data": "userName",
                            "sDefaultContent" : ""
                        },{
                            "data": "mobile",
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
                            "data": "travelTime",
                            "render": function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                            },
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "num",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "travelName",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "content",
                            render: function (data) {
                                if (null != data && data != '') {
                                    return data.length > 30 ? (data.substring(0, 30) + '...') : data;
                                } else {
                                    return "";
                                }
                            },
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "content",
                            "render": function (data, type, row, meta) {
                                var content  = data;
                                var detail = "<button title='详细' class='btn btn-primary btn-circle add' onclick=\"$carTravel.fn.detail(\'" + content.trim() + "\')\">" +
                                        "<i class='fa fa-eye'></i> 详细</button>";
                                return  detail ;
                            }
                        }

                    ],
                    "fnServerParams": function (aoData) {
                        aoData.userName = $("#userName").val();
                        aoData.travelName = $("#travelName").val();
                        aoData.Dstart = $("#start").val();
                        aoData.Dend = $("#end").val();
                    }
                });
            },
            detail: function (data) {
                $('#showText').html(data);
                $("#detail").modal("show");
            },

            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        $carTravel.v.dTable.ajax.reload(null, false);
                    } else {
                        $carTravel.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        $carTravel.fn.init();
    })
</script>
</body>
</html>
