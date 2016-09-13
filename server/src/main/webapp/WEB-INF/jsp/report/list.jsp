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
                                <input type="text" id="mobile" name="mobile" class="form-control" placeholder="联系方式">
                            </div>

                            <div class="form-group col-sm-2">
                                <input type="text" id="startDate" name="startDate" class="form-control input-append date form_datetime" placeholder="反馈时间(大于)">
                            </div>

                            <div class="form-group col-sm-2">
                                <input type="text" id="endDate" name="endDate" class="form-control input-append date form_datetime" placeholder="反馈时间(小于)">
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
                            <span class="tools pull-right" style="margin-right: 10px;margin-left: 10px">
                               <button class="btn btn-info" type="button" onclick="$report.fn.del();" id="deleteBatch" style="display: none">删除</button>
                            </span>
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
                                        <th>联系方式</th>
                                        <th>用户权限</th>
                                        <th>反馈时间</th>
                                        <th>意见详情</th>
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
<%@ include file="../cartravel/detail.jsp" %>
<script>
    $report = {
        v: {
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $report.fn.dataTableInit();
                $("#c_search").click(function () {
                    $report.v.dTable.ajax.reload();
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
                $report.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort": false,
                    "ajax": {
                        "url": "${contextPath}/admin/report/list",
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
                            "data": "userInfo.mobile",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "userInfo.type",
                            "render": function (data) {
                                if(data==0){
                                    return "企业管理"
                                }else if(data==1){
                                    return "员工"
                                }else{
                                    return "普通会员"
                                }
                            },
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
                                var detail = "<button title='详细' class='btn btn-primary btn-circle add' onclick=\"$report.fn.detail(\'" + content.trim() + "\')\">" +
                                        "<i class='fa fa-eye'></i> 详细</button>";
                                return  detail ;
                            }
                        }

                    ],
                    "fnServerParams": function (aoData) {
                        aoData.userName = $("#mobile").val();
                        aoData.startDate = $("#startDate").val();
                        aoData.endDate = $("#endDate").val();
                    }
                });
            },
            del: function (id) {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $("#confirm").modal("show");
                $('#showText').html('您确定要彻底删除所选的反馈记录吗？');
                $("#determine").off("click");
                $("#determine").on("click",function(){
                    $.ajax({
                        "url": "${contextPath}/admin/report/del",
                        "data": {
                            id:id,
                            ids:JSON.stringify(ids)
                        },
                        "dataType": "json",
                        "type": "POST",
                        success: function (result) {
                            if (result==1) {
                                $('#showText').html('删除错误');
                            }else {
                                $("#deleteBatch").css('display','none');
                                $enterprise.v.dTable.ajax.reload(null,false);
                            }
                            $("#confirm").modal("hide");
                        }
                    });
                })
            },
            detail: function (data) {
                $('#showText').html(data);
                $("#detail").modal("show");
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        $report.v.dTable.ajax.reload(null, false);
                    } else {
                        $report.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        $report.fn.init();
    })
</script>
</body>
</html>
