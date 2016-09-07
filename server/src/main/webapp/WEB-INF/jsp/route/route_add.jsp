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
    <title>Form Layouts</title>
    <%@ include file="../inc/new2/css.jsp" %>
</head>

<body class="sticky-header">

<section>
    <%@ include file="../inc/new2/menu.jsp" %>
    <!-- main content start-->
    <div class="main-content">
        <%@ include file="../inc/new2/header.jsp" %>
        <!--body wrapper start-->
        <section class="wrapper">
            <!-- page start-->

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            添加班车
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post">
                                <input id="id" name="id" type="hidden" value="${route.id}">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" ><span style="color: red;">* </span>所属企业：</label>
                                    <div class="col-sm-1">
                                        <select class="form-control input-sm" name="enterprise.id">
                                            <c:forEach items="${enterpriseList}" var="enterprise">
                                                <option value="${enterprise.id}">${enterprise.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">线路添加：</label>
                                    <div class="col-sm-2">
                                        <input type="file" name="file" id="file" title="选择EXCEL文件导入" class="btn btn-default required" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.externalLink+xml">
                                    </div>
                                    &nbsp;&nbsp;<button type="button" id="importBtn" class="btn btn-info">导入</button>
                                </div>
                                <div class="form-group" id="timeDiv">

                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">车辆指派：</label>
                                    <div class="col-sm-6">
                                        <button id="add_dispach" class="btn btn-primary"><i class='fa fa-plus'></i> 新增派遣车辆</button>
                                        <div class="adv-table">
                                            <table class="display table table-bordered table-striped" id="dataTables" width="100%">
                                                <thead>
                                                <tr>
                                                    <th><input type="checkbox" class="list-parent-check"
                                                               onclick="$leoman.checkAll(this);"/></th>
                                                    <th>车牌号</th>
                                                    <th>司机姓名</th>
                                                    <th>车型</th>
                                                    <th>座位数</th>
                                                    <th>操作</th>
                                                </tr>
                                                </thead>
                                            </table>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-6">
                                        <button type="button" onclick="$route.fn.save()" class="btn btn-primary">保存</button>
                                        <button type="button" onclick="$route.fn.back()" class="btn btn-primary">返回</button>
                                    </div>
                                </div>

                            </form>
                        </div>
                    </section>
                </div>
            </div>
        </section>
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

    <!-- 发车时间模板 -->
    <div id="timeModel" style="display: none;margin-bottom: 10px;">
        <label class="col-sm-1 control-label">发车时间:</label>
        <div class="col-sm-2">
            <input type="text" class="form-control input-append date form_datetime" style="width: 180px;" readonly maxlength="20" value="">
        </div>
        <button type="button" onclick="$route.fn.addRow(this)" class="btn btn-primary"><i class='fa fa-plus-circle'></i></button>
        <button type="button" onclick="$route.fn.removeRow(this)" class="btn btn-primary"><i class='fa fa-minus-circle'></i></button>
    </div>

    <!-- main content end-->
</section>
<%@ include file="../inc/new2/foot.jsp" %>
<script>
    $route = {
        v: {
            list: [],
            chart: null,
            dTable: null,
            imageSize: 0
        },
        fn: {
            init: function () {

                //初始化已指派车辆
                $route.fn.dataTableInit();

                //初始化发车时间
                $route.fn.timeInit();

                //时间控件初始化
                $('.form_datetime').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 'hour',
                    forceParse: 0,
                    showMeridian: false,
                    format: 'hh:ii'
                });
            },
            dataTableInit: function () {
                $route.v.dTable = $leoman.dataTable($('#dataTables'), {
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
                        {"data": "driverName"},
                        {"data": "modelNo"},
                        {"data": "seatNum"},
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {

                                var dispatch = "<button title='派遣' class='btn btn-primary btn-circle edit' onclick=\"$route.fn.dispatch(\'" + data + "\')\">" +
                                        "<i class='fa fa-check'></i> 派遣</button>";

                                return dispatch;
                            }
                        }
                    ]
                });
            },
            timeInit : function(){
                var model = $("#timeModel").clone().removeAttr("id");
                model.find(".fa-minus-circle").parents("button").hide();
                model.show();
                $("#timeDiv").append(model);
            },
            back : function(){
                window.location.href = "${contextPath}/admin/route/index";
            },
            dispatch : function(){

            },
            addRow : function(obj){
                var model = $("#timeModel").clone().removeAttr("id");
                model.find("label").text("");
                model.find(".fa-plus-circle").parents("button").hide();
                model.show();
                $(obj).parents(".form-group").append(model);
            },
            //保存
            save : function() {
                var flag = true;
                if(!$("#formId").valid()) return;

                if(flag){
                    $("#formId").ajaxSubmit({
                        url : "${contextPath}/admin/route/save",
                        type : "POST",
                        success : function(result) {
                            if(result.status == 0) {
                                window.location.href = "${contextPath}/admin/route/index";
                            }
                            else {
                                $common.fn.notify('操作失败');
                            }
                        }
                    });
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
