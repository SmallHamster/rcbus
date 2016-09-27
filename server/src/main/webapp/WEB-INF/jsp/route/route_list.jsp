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
    <title>路线列表</title>
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
                                <input type="text" id="startStation" class="form-control" placeholder="始发站">
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="endStation" class="form-control" placeholder="终点站">
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
                                        <th><input type="checkbox" class="list-parent-check"
                                                   onclick="$leoman.checkAll(this);"/></th>
                                        <th>路线起始</th>
                                        <th>所属企业</th>
                                        <th>状态</th>
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
                        "url": "${contextPath}/admin/route/list",
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
                            "data": "startStation",
                            "render": function (data, type, row, meta) {
                                return row.startStation + " ------> " +row.endStation;
                            }
                        },
                        {"data": "enterprise.name"},
                        {
                            "data": "isShow",
                            "render": function (data, type, row, meta) {

                                var status = "";
                                if(data == 0){
                                    status = "隐藏";
                                }else{
                                    status = "显示";
                                }
                                return status;
                            }
                        },
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {

                                var detail = "<button title='查看' class='btn btn-primary btn-circle add' onclick=\"$route.fn.detail(\'" + data + "\')\">" +
                                        "<i class='fa fa-eye'></i> 查看</button>";

                                var edit = "<button title='编辑' class='btn btn-primary btn-circle edit' onclick=\"$route.fn.add(\'" + data + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 编辑</button>";

                                var txt = "";
                                var icon = "";
                                if(row.isShow == 1){
                                    txt = " 隐藏";
                                    icon = "<i class='fa fa-circle-o'></i>";
                                }else if(row.isShow == 0){
                                    txt = " 显示";
                                    icon = "<i class='fa fa-circle'></i>";
                                }
                                var showHide = "<button title='+txt+' class='btn btn-primary btn-circle edit' onclick=\"$route.fn.showHide(\'" + data + "\',\'" + row.isShow + "\')\">" +
                                        icon + txt + "</button>";

                                var del = "<button title='删除' class='btn btn-primary btn-circle edit' onclick=\"$route.fn.delete(\'" + data + "\')\">" +
                                        "<i class='fa fa-trash-o'></i> 删除</button>";

                                return detail + "&nbsp;" + edit + "&nbsp;" + showHide + "&nbsp;"+ del;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.startStation = $("#startStation").val();//车牌号
                        aoData.endStation = $("#endStation").val();//车型
                        aoData.enterpriseId = $("#enterpriseId").val();//车型
                    }
                });
            },
            detail: function (id) {
                var params = "";
                if (id != null && id != '') {
                    params = "?id=" + id;
                }
                window.location.href = "${contextPath}/admin/route/detail" + params;
            },
            add: function (id) {
                var params = "";
                if (id != null && id != '') {
                    params = "?id=" + id;
                }
                window.location.href = "${contextPath}/admin/route/add" + params;
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
                    $.post("${contextPath}/admin/route/delete",{'ids':JSON.stringify(ids)},function(result){
                        if(result.status == 0){
                            $bus.v.dTable.ajax.reload(null,false);
                        }else{
                            $leoman.alertMsg("删除错误");
                        }
                    });
                });
            },
            showHide : function(id,isShow){
                $("#confirm").modal("show");
                var oper = "";
                if(isShow == 1){
                    oper = 0;
                }else if(isShow == 0){
                    oper = 1;
                }
                $leoman.alertConfirm("您确定要执行此操作吗？",function(){
                    $.post("${contextPath}/admin/route/updateIsShow",{'id':id,'isShow':oper},function(result){
                        if(result.status == 0){
                            $bus.v.dTable.ajax.reload(null,false);
                        }else{
                            $leoman.alertMsg("操作失败");
                        }
                    });
                });
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
