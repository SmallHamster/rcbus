<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">
    <title>Dynamic Table</title>
    <%@ include file="../../inc/new2/css.jsp" %>
</head>
<body class="sticky-header">
<section>
    <%@ include file="../../inc/new2/menu.jsp" %>
    <!-- main content start-->
    <div class="main-content">
        <%@ include file="../../inc/new2/header.jsp" %>
        <!--body wrapper start-->
        <div class="wrapper">
            <div class="row">
                <div class="col-sm-12">
                    <input type="hidden" id="userType" value="${userInfo.type}">
                    <section class="panel">
                        <div class="panel-body">
                            <div class="form-group col-sm-2">
                                <input type="text" id="username" name="username" class="form-control"
                                       id="exampleInputEmail2" placeholder="管理员账号">
                                <c:if test="${userInfo.type eq 0}">
                                    <input type="hidden" id="enterpriseId" name="enterpriseId" value="${userInfo.enterprise.id}">
                                </c:if>
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
                            管理员列表
                            <span class="tools pull-right" style="margin-right: 10px;margin-left: 10px">
                               <button class="btn btn-info" type="button" onclick="$admin.fn.del();" id="deleteBatch" style="display: none">删除</button>
                            </span>
                            <span class="tools pull-right">
                               <button class="btn btn-default " type="button"><i class="fa fa-refresh"></i>刷新</button>
                               <button class="btn btn-info" type="button" onclick="$admin.fn.add();">新增用户</button>
                            </span>
                        </header>
                        <div class="panel-body">
                            <div class="adv-table">
                                <table class="display table table-bordered table-striped" id="dataTables" width="100%">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" class="list-parent-check"
                                                   onclick="$leoman.checkAll(this);"/></th>
                                        <th>账号</th>
                                        <th>创建时间</th>
                                        <th>手机号</th>
                                        <th>最后登录时间</th>
                                        <th>权限</th>
                                        <th>企业</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                        <!-- Modal -->
                        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                             aria-hidden="true">
                            <input type="hidden" id="adminId" name="adminId" value>
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-hidden="true">&times;</button>
                                        <h4 class="modal-title">选择角色</h4>
                                    </div>
                                    <div class="modal-body row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <select multiple="multiple" class="multi-select" id="my_multi_select1"
                                                        name="my_multi_select1[]">
                                                </select>
                                            </div>
                                            <hr>
                                            <div class="form-group">
                                                <div class="col-sm-6">
                                                    <button type="button" id="getOptionBtn" class="btn btn-primary">保存
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</section>
<%@ include file="../../inc/new2/foot.jsp" %>
<%@ include file="../../inc/new2/confirm.jsp" %>
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

                $('#my_multi_select1').multiSelect();
                $("#getOptionBtn").click(function () {
                    $admin.fn.getOption();
                });
            },
            dataTableInit: function () {
                $admin.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort": false,
                    "ajax": {
                        "url": "${contextPath}/admin/admin/list",
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
                        {
                            "data": "createDate",
                            "render": function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                            }
                        },
                        {
                            "data": "mobile"
                        },
                        {
                            "data": "lastLoginDate",
                            "render": function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                            }
                        },
                        {
                            "data": "roleName",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "enterprise.name",
                            "render" : function(data){
                              if(data==null){
                                  return "——";
                              }else {
                                  return data;
                              }
                            },
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {
                                if(row.username == "admin") {
                                    return "";
                                }
                                else {

                                    var detail = "<button title='查看' class='btn btn-primary btn-circle add' onclick=\"$admin.fn.detail(\'" + data + "\')\">" +
                                            "<i class='fa fa-eye'></i> 查看</button>";

                                    var edit = "<button title='编辑' class='btn btn-primary btn-circle edit' onclick=\"$admin.fn.add(\'" + data + "\')\">" +
                                            "<i class='fa fa-pencil-square-o'></i> 编辑</button>";

                                    var reset = "<button title='重置' class='btn btn-primary btn-circle edit' onclick=\"$admin.fn.reset(\'" + data + "\')\">" +
                                            "<i class='fa fa-pencil-square-o'></i> 重置密码</button>";

                                    var del = "<button title='删除' class='btn btn-primary btn-circle edit' onclick=\"$admin.fn.del(\'" + data + "\')\">" +
                                            "<i class='fa fa-trash-o'></i> 删除</button>";

                                    var selectRole = "<button title='赋予角色' class='btn btn-primary btn-circle edit' onclick=\"$admin.fn.openModal(\'" + data + "\')\">" +
                                            "<i class='fa fa-exchange'></i> 赋予角色</button>";

                                    return edit  + "&nbsp;" + reset + "&nbsp;" + del;

                                }
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.username = $("#username").val();
                        aoData.enterpriseId = $("#enterpriseId").val();
                    }
                });
            },
            add: function (id) {
                var params = "";
                if (id != null && id != '') {
                    params = "?id=" + id;
                }
                window.location.href = "${contextPath}/admin/admin/add" + params;
            },
            reset: function (id) {
                $("#confirm").modal("show");
                $('#showText').html('您确定要重置该管理员的密码为 [888888] 吗？');
                $("#determine").off("click");
                $("#determine").on("click",function(){
                    $.ajax({
                        "url": "${contextPath}/admin/admin/reset",
                        "data": {
                            id:id
                        },
                        "dataType": "json",
                        "type": "POST",
                        success: function (result) {
                            if (result.status==0) {
                                alert("重置成功");
                                $admin.v.dTable.ajax.reload(null,false);
                            }else {
                                alert("重置失败");
                            }
                            $("#confirm").modal("hide");
                        }
                    });
                })
            },
            del: function (id) {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $("#confirm").modal("show");
                $('#showText').html('您确定要彻底删除所选的管理员吗？');
                $("#determine").off("click");
                $("#determine").on("click",function(){
                    $.ajax({
                        "url": "${contextPath}/admin/admin/del",
                        "data": {
                            id:id,
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
                                $("#deleteBatch").css('display','none');
                                $admin.v.dTable.ajax.reload(null,false);
                            }
                            $("#confirm").modal("hide");
                        }
                    });
                })
            },
            
            <%--delete: function () {--%>
                <%--var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');--%>
                <%--var ids = checkBox.getInputId();--%>
                <%--$.ajax({--%>
                    <%--url: "${contextPath}/admin/cq/deleteBatch",--%>
                    <%--data: {--%>
                        <%--"ids": JSON.stringify(ids)--%>
                    <%--},--%>
                    <%--type: "post",--%>
                    <%--dataType: "json",--%>
                    <%--success: function (result) {--%>
                        <%--if (!result.status) {--%>
                            <%--$common.fn.notify(result.msg);--%>
                            <%--return;--%>
                        <%--}--%>
                        <%--$admin.v.dTable.ajax.reload();--%>
                    <%--}--%>
                <%--});--%>
            <%--},--%>
            openModal: function (adminId) {
                $("#adminId").val(adminId);
                $('#my_multi_select1').multiSelect('refresh');
                $.ajax({
                    url: "${contextPath}/admin/admin/role/select",
                    type: "POST",
                    data: {
                        "adminId": adminId
                    },
                    success: function (result) {
                        var allRole = result.data.object.allRoles;
                        var hasRole = result.data.object.hasRoels;

                        console.log(hasRole);

                        var allRoleArray = [];
                        $.each(allRole, function (n, item) {
                            var option = {
                                value: item.id,
                                text: item.name
                            }
                            allRoleArray.push(option);
                        });
                        $('#my_multi_select1').multiSelect('addOption', allRoleArray);
                        $('#my_multi_select1').multiSelect('select', hasRole);
                    }

                });

                $("#myModal").modal("show");
            },
            getOption: function () {
                var selectedValues = $("#my_multi_select1").find('option:selected').map(function () {
                    return $(this).val();
                }).get();
                console.log(selectedValues);
                $.ajax({
                    url : "${contextPath}/admin/admin/role/save",
                    type : 'POST',
                    data : {
                        'adminId' : $("#adminId").val(),
                        'roleIds' : JSON.stringify(selectedValues)
                    },
                    success : function(result) {
                        if(result.status == 0) {
                            window.location.href = "${contextPath}/admin/admin/index";
                        }
                        else {
                            alert("操作错误");
                        }
                    }
                });
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
