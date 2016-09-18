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
    <title>通勤巴士历史记录</title>
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
                                <input type="text" id="routeName" class="form-control" placeholder="路线起始">
                            </div>
                            <div class="form-group col-sm-2" style="width: 100px;margin-top: 5px;">
                                所属企业：
                            </div>
                            <div class="form-group col-sm-2">
                                <select class="form-control input-sm" id="enterpriseId">
                                    <option value="">---请选择---</option>
                                    <c:forEach items="${enterpriseList}" var="enterprise">
                                        <option value="${enterprise.id}">${enterprise.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group col-sm-1" style="width: 50px;margin-top: 5px;">
                                从：
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="startDate" class="form-control input-append date form_datetime" style="width: 180px;" readonly maxlength="20" value="" placeholder="请选择起始时间">
                            </div>
                            <div class="form-group col-sm-1" style="width: 50px;margin-top: 5px;">
                                至：
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="endDate" class="form-control input-append date form_datetime" style="width: 180px;" readonly maxlength="20" value="" placeholder="请选择结束时间">
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
                            路线列表
                            <span class="tools pull-right" style="margin-right: 10px;margin-left: 10px">
                               <button class="btn btn-info" type="button" onclick="$route.fn.delete();" id="deleteBatch" style="display: none">
                                   <i class='fa fa-trash-o'></i> 删除</button>
                            </span>
                            <span class="tools pull-right">
                               <button class="btn btn-default " type="button"><i class="fa fa-refresh"></i> 刷新</button>
                                <button class="btn btn-info" type="button" onclick="$route.fn.add();"><i class="fa fa-plus"></i> 新增路线</button>
                            </span>
                        </header>
                        <div class="panel-body">
                            <div class="adv-table">
                                <table class="display table table-bordered table-striped" id="dataTables" width="100%">
                                    <thead>
                                    <tr>
                                        <th>路线起始</th>
                                        <th>公司名称</th>
                                        <th>发车时间</th>
                                        <th>人数</th>
                                        <th>司机评价</th>
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
                $('.form_datetime').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 'month',
                    minView: "month",
                    forceParse: 0,
                    showMeridian: false,
                    format: 'yyyy-mm-dd'
                });
            },
            dataTableInit: function () {
                $route.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort": false,
                    "ajax": {
                        "url": "${contextPath}/admin/route/order/list",
                        "type": "POST"
                    },
                    "columns": [
                        {
                            "data": "id",//起点站--终点站
                            "render": function (data, type, row, meta) {
                                return row.startStation+" ------> "+row.endStation;
                            }
                        },
                        {"data": "enterpriseName"},//企业名称
                        {"data": "rideTime"},//企业名称
                        {"data": "peopleNum"},//企业名称
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {

                                var detail = "<button title='查看' class='btn btn-primary btn-circle add' onclick=\"$route.fn.orderComment(\'" + row.routeId + "\',\'" + row.rideTime + "\')\">" +
                                        "<i class='fa fa-eye'></i> 查看</button>";
                                return detail;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.routeName = $("#routeName").val();//路线起始
                        aoData.enterpriseId = $("#enterpriseId").val();//企业
                        aoData.startDate = $("#startDate").val();//起始时间
                        aoData.endDate = $("#endDate").val();//结束时间
                    }
                });
            },
            //查看司机评价
            orderComment: function (routeId, departTime) {
                var params = "?routeId=" + routeId+"&departTime="+departTime;
                location.href = "${contextPath}/admin/route/order/comment" + params;
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
