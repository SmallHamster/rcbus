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
                                <input type="text" id="mobile" name="mobile" class="form-control" placeholder="联系方式">
                            </div>

                            <div class="form-group col-sm-2">
                                <input type="text" id="startPoint" name="startPoint" class="form-control input-append date form_datetime" placeholder="出发点">
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="endPoint" name="endPoint" class="form-control input-append date form_datetime" placeholder="目的地">
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
                                        <th>出发地</th>
                                        <th>目的地</th>
                                        <th>出行人数</th>
                                        <th>出发时间</th>
                                        <th>到达时间</th>
                                        <th>返程时间</th>
                                        <th>通勤方式</th>
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
                        "url": "${contextPath}/admin/commuting/list",
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
                        },
                        {
                            "data": "mobile",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "startPoint",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "endPoint",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "num",
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
                            "data": "endDate",
                            "render": function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                            },
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "returnDate",
                            "render": function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                            },
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "status",
                            "render": function (data) {
                                if(data = 0){
                                    return "每天";
                                }else if(data = 1){
                                    return "工作日";
                                }else{
                                    return "周末";
                                }
                            },
                            "sDefaultContent" : ""
                        }


                    ],
                    "fnServerParams": function (aoData) {
                        aoData.userName = $("#userName").val();
                        aoData.mobile = $("#mobile").val();
                        aoData.startPoint = $("#startPoint").val();
                        aoData.endPoint = $("#endPoint").val();
                    }
                });
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
