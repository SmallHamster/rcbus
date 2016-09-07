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
                                <input type="text" id="mobile" name="mobile" class="form-control" placeholder="手机号">
                            </div>
                            <div class="form-group col-sm-2">
                                <select id="type" name="type" class="form-control input-sm">
                                    <option value="">权限</option>
                                    <option value="0">企业管理</option>
                                    <option value="1">员工</option>
                                    <option value="2">普通会员</option>
                                </select>
                            </div>
                            <div class="form-group col-sm-2">
                                <select class="form-control input-sm" id="enterpriseId" name="enterpriseId" >
                                    <option value="">企业</option>
                                    <c:forEach var="v" items="${enterprise}">
                                        <option value="${v.id}">${v.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

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
                               <button class="btn btn-info" type="button" onclick="$admin.fn.del();" id="deleteBatch" style="display: none">删除</button>
                            </span>
                            <span class="tools pull-right">
                               <button class="btn btn-default " type="button"><i class="fa fa-refresh"></i>刷新</button>
                               <button class="btn btn-info" type="button" onclick="$admin.fn.add();">新增会员</button>
                            </span>
                        </header>
                        <div class="panel-body">
                            <div class="adv-table">
                                <table class="display table table-bordered table-striped" id="dataTables" width="100%">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" class="list-parent-check"
                                                   onclick="$leoman.checkAll(this);"/></th>
                                        <th>手机号码</th>
                                        <th>注册时间</th>
                                        <th>企业名称</th>
                                        <th>账号权限</th>
                                        <th>优惠券</th>
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
    $admin = {
        v: {
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $admin.fn.dataTableInit();
                $("#c_search").click(function () {
                    $admin.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                $admin.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort": false,
                    "ajax": {
                        "url": "${contextPath}/admin/user/list",
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
                            "data": "enterprise.name",
                            "render": function (data) {
                                if(data==null){
                                    return "——";
                                }else{
                                    return data;
                                }
                            },
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "type",
                            "render": function (data) {
                                if(data==0){
                                    return "企业管理";
                                }else if(data==1){
                                    return "员工";
                                }else{
                                    return "普通会员";
                                }
                            },
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "优惠券",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {
                                var detail = "<button title='查看' class='btn btn-primary btn-circle add' onclick=\"$admin.fn.detail(\'" + data + "\')\">" +
                                        "<i class='fa fa-eye'></i> 查看</button>";

                                var edit = "<button title='编辑' class='btn btn-primary btn-circle edit' onclick=\"$admin.fn.add(\'" + data + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 编辑</button>";

                                var del = "<button title='删除' class='btn btn-primary btn-circle edit' onclick=\"$admin.fn.del(\'" + data + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 删除</button>";
                                var departure = "<button title='离职' class='btn btn-primary btn-circle edit' onclick=\"$admin.fn.departure(\'" + data + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 离职</button>";

                                var giving = "<button title='赠送' class='btn btn-primary btn-circle edit' onclick=\"$admin.fn.giving(\'" + data + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i> 赠送</button>";

                                if(row.type==1){
                                    return giving  + "&nbsp;" + detail  + "&nbsp;" + departure + "&nbsp;" + del;
                                }else {
                                    return giving  + "&nbsp;" + detail  + "&nbsp;"  + del;
                                }
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.mobile = $("#mobile").val();
                        aoData.enterpriseId = $("#enterpriseId").val();
                        aoData.type = $("#type").val();
                    }
                });
            },
            departure: function (id){
                $("#confirm").modal("show");
                $('#showText').html('您确定要将该会员离职吗？');
                $("#determine").off("click");
                $("#determine").on("click",function(){
                    $.ajax({
                        "url": "${contextPath}/admin/user/departure",
                        "data": {
                            id:id
                        },
                        "dataType": "json",
                        "type": "POST",
                        success: function (result) {
                            if (result==1) {
                                alert("错误");
                            }else if (result==2) {
                                alert("所选员工不是一般员工");
                            }else {
                                $admin.v.dTable.ajax.reload(null,false);
                            }
                            $("#confirm").modal("hide");
                        }
                    });
                })
            },
            add: function (id) {
                var params = "";
                if (id != null && id != '') {
                    params = "?id=" + id;
                }
                window.location.href = "${contextPath}/admin/user/add" + params;
            },
            detail: function (id) {
                var params = "";
                if (id != null && id != '') {
                    params = "?id=" + id;
                }
                window.location.href = "${contextPath}/admin/user/detail" + params;
            },
            del: function (id) {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $("#confirm").modal("show");
                $('#showText').html('您确定要彻底删除所选的会员吗？');
                $("#determine").off("click");
                $("#determine").on("click",function(){
                    $.ajax({
                        "url": "${contextPath}/admin/user/del",
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
                                $admin.v.dTable.ajax.reload(null,false);
                            }
                            $("#confirm").modal("hide");
                        }
                    });
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        $admin.v.dTable.ajax.reload(null, false);
                    } else {
                        $admin.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        $admin.fn.init();
    })
</script>
</body>
</html>
