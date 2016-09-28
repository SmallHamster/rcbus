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
    <title>企业报名信息列表</title>
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
                                <input type="text" id="username" class="form-control" placeholder="联系人">
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="mobile" class="form-control" placeholder="联系人电话">
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="enterpriseName" class="form-control" placeholder="企业名称">
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
                            企业报名信息列表
                            <span class="tools pull-right" style="margin-right: 10px;margin-left: 10px">
                               <button class="btn btn-info" type="button" onclick="$bus.fn.delete();" id="deleteBatch" style="display: none">
                                   <i class="fa fa-trash-o"></i> 删除</button>
                            </span>
                        </header>
                        <div class="panel-body">
                            <div class="adv-table">
                                <table class="display table table-bordered table-striped" id="dataTables" width="100%">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" class="list-parent-check"
                                                   onclick="$leoman.checkAll(this);"/></th>
                                        <th>联系人</th>
                                        <th>联系人电话</th>
                                        <th>申请时间</th>
                                        <th>企业名称</th>
                                        <th>企业地址</th>
                                        <th>备注</th>
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
                        "url": "${contextPath}/admin/enterprise/apply/list",
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
                        {"data": "username"},
                        {"data": "mobile"},
                        {
                            "data": "createDate",
                            "render": function (data, type, row, meta) {
                                var date = new Date(data);
                                return date.format('yyyy-MM-dd h:m:s');
                            }
                        },
                        {"data": "enterpriseName"},
                        {"data": "enterpriseAddress"},
                        {"data": "remark"},
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {

                                var del = "<button title='删除' class='btn btn-primary btn-circle edit' onclick=\"$bus.fn.delete(\'" + data + "\')\">" +
                                        "<i class='fa fa-trash-o'></i> 删除</button>";

                                return del;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.username = $("#username").val();//车牌号
                        aoData.mobile = $("#mobile").val();//车型
                        aoData.enterpriseName = $("#enterpriseName").val();//车型
                    }
                });
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
                    $.post("${contextPath}/admin/enterprise/apply/delete",{'ids':JSON.stringify(ids)},function(result){
                        if(result.status == 0){
                            $bus.v.dTable.ajax.reload(null,false);
                        }else{
                            $leoman.alertMsg("删除失败");
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
