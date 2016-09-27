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
    <title>司机详细评价</title>
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
                        <header class="panel-heading">
                            ${route.startStation} ------> ${route.endStation}&nbsp;发车时间：${departTime}
                        </header>
                        <div class="panel-body">

                            <div class="form-group col-sm-2">
                                <input type="text" id="mobile" class="form-control" placeholder="账号">
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
                            司机评价
                            <span class="tools pull-right">
                               <button type="button" onclick="$route.fn.back()" class="btn btn-primary">返回</button>
                            </span>
                        </header>
                        <div class="panel-body">
                            <div class="adv-table">
                                <table class="display table table-bordered table-striped" id="dataTables" width="100%">
                                    <thead>
                                    <tr>
                                        <th>用户账号</th>
                                        <th>评价时间</th>
                                        <th>司机服务</th>
                                        <th>汽车环境</th>
                                        <th>行程安全</th>
                                        <th>准时到达</th>
                                        <th>平均评分</th>
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
    $route = {
        v: {
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $route.fn.dataTableInit();
                $("#c_search").click(function () {
                    $route.v.dTable.ajax.reload();
                });
                $("#c_clear").click(function () {
                    $(this).parents(".panel-body").find("input,select").val("");
                });

            },
            dataTableInit: function () {
                $route.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort": false,
                    "ajax": {
                        "url": "${contextPath}/admin/route/comment/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "order.userInfo.mobile"},
                        {
                            "data": "order.commentTime",
                            "render": function (data, type, row, meta) {
                                var date = new Date(data);
                                return date.format('yyyy-MM-dd h:m:s');
                            }
                        },
                        {"data": "order.driverService"},
                        {"data": "order.busEnvironment"},
                        {"data": "order.safeDriving"},
                        {"data": "order.arriveTime"},
                        {
                            "data": "arriveTime",//平均评分
                            "render": function (data, type, row, meta) {
                                var avgScore = (row.order.driverService + row.order.busEnvironment + row.order.safeDriving + row.order.arriveTime)/4;
                                return avgScore.toFixed(1);
                            }
                        },
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.routeId = "${routeId}";//路线id
                        aoData.departTime = "${departTime}";//发车时间id
                        aoData.mobile = $("#mobile").val();//账号
                    }
                });
            },
            //返回
            back : function(){
                location.href = "${contextPath}/admin/route/order/index";
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        $route.v.dTable.ajax.reload(null, false);
                    } else {
                        $route.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        $route.fn.init();
    })
</script>
</body>
</html>
