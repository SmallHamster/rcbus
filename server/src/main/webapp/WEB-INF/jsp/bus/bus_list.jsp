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
    <title>车辆列表</title>
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
                            <div class="form-group col-sm-2" style="width: 100px;margin-top: 5px;">
                                用车类型：
                            </div>
                            <div class="form-group col-sm-2">
                                <select class="form-control input-sm" id="carType">
                                    <option value="">---请选择---</option>
                                    <c:forEach items="${typeList}" var="type">
                                        <c:if test="${type.id == bus.carType.id}">
                                            <option value="${type.id}" selected="selected">${type.name}</option>
                                        </c:if>
                                        <c:if test="${type.id != bus.carType.id}">
                                            <option value="${type.id}">${type.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
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
                            车辆列表
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
                                        <th>车牌号</th>
                                        <th>品牌</th>
                                        <th>车型</th>
                                        <th>座位数</th>
                                        <th>司机姓名</th>
                                        <th>司机联系电话</th>
                                        <th>司机身份证号</th>
                                        <th>司机性别</th>
                                        <th>用车类型</th>
                                        <th>是否监控</th>
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
                        {"data": "brand"},
                        {"data": "modelNo"},
                        {"data": "seatNum"},
                        {"data": "driverName"},
                        {"data": "driverPhone"},
                        {"data": "driverIDCard"},
                        {
                            "data": "driverSex",
                            "render": function (data) {
                                var sex = '-';
                                if(data == 1){
                                    sex = '女';
                                }else if(data == 0){
                                    sex = '男';
                                }
                                return sex;
                            }
                        },
                        {"data": "carType.name"},
                        {
                            "data": "curLat",
                            "render": function (data) {
                                var str = "";
                                if(data == null || data == ''){
                                    str = '否';
                                }else{
                                    str = '是';
                                }
                                return str;
                            }
                        },
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {

                                var detail = "<button title='查看' class='btn btn-primary btn-circle add' onclick=\"$bus.fn.detail(\'" + data + "\')\">" +
                                        "<i class='fa fa-eye'></i> 查看</button>";

                                var edit = "<button title='编辑' class='btn btn-primary btn-circle edit' onclick=\"$bus.fn.add(\'" + data + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 编辑</button>";

                                var del = "<button title='删除' class='btn btn-primary btn-circle edit' onclick=\"$bus.fn.delete(\'" + data + "\')\">" +
                                        "<i class='fa fa-trash-o'></i> 删除</button>";

                                return detail + "&nbsp;" + edit + "&nbsp;"+ del;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.carNo = $("#carNo").val();//车牌号
                        aoData.modelNo = $("#modelNo").val();//车型
                        aoData.driverName = $("#driverName").val();//车型
                        aoData.carType1 = $("#carType").val();//车型
                    }
                });
            },
            detail: function (id) {
                var params = "";
                if (id != null && id != '') {
                    params = "?id=" + id;
                }
                window.location.href = "${contextPath}/admin/bus/detail" + params;
            },
            add: function (id) {
                var params = "";
                if (id != null && id != '') {
                    params = "?id=" + id;
                }
                window.location.href = "${contextPath}/admin/bus/add" + params;
            },
            delete: function (id) {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = [];
                if(id != null){
                    ids.push(id);
                }else{
                    ids = checkBox.getInputId();
                }
                $leoman.alertConfirm("确定要删除吗？",function(){
                    $.ajax({
                        "url": "${contextPath}/admin/bus/delete",
                        "data": {
                            ids:JSON.stringify(ids)
                        },
                        "dataType": "json",
                        "type": "POST",
                        success: function (result) {
                            if(result.status == 0){
                                $bus.v.dTable.ajax.reload(null,false);
                            }else{
                                $leoman.alertMsg("删除失败");
                            }
                        }
                    });
                });
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        $bus.v.dTable.ajax.reload(null, false);
                    } else {
                        $bus.v.dTable.ajax.reload();
                    }
                    $leoman.alertMsg(result.msg, "success");
                } else {
                    $leoman.alertMsg(result.msg, "error");
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
