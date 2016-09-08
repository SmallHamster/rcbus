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
    <title>新增路线</title>
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
                            新增路线
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post">
                                <input type="hidden" name="departTimes" value="">
                                <input type="hidden" name="backTimes" value="">
                                <input type="hidden" id="busIds" name="busIds" value="1">

                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >所属企业：</label>
                                    <div class="col-sm-1">
                                        <select class="form-control input-sm" name="enterprise.id" onchange="$route.fn.enterpriseChange(this)">
                                            <c:forEach items="${enterpriseList}" var="enterprise">
                                                <option value="${enterprise.id}" type="${enterprise.type}">${enterprise.name}</option>
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

                                <div class="form-group" id="departTimeDiv">
                                    <div style="margin-bottom: 10px;">
                                        <label class="col-sm-1 control-label">发车时间:</label>
                                        <div class="col-sm-2">
                                            <input type="text" class="form-control input-append date form_datetime" style="width: 180px;" readonly maxlength="20" value="">
                                        </div>
                                        <button type="button" onclick="$route.fn.addRow(this)" class="btn btn-primary"><i class='fa fa-plus-circle'></i></button>
                                    </div>
                                </div>

                                <div class="form-group" id="backTimeDiv" style="display: none;">
                                    <div style="margin-bottom: 10px;">
                                        <label class="col-sm-1 control-label">返程时间:</label>
                                        <div class="col-sm-2">
                                            <input type="text" class="form-control input-append date form_datetime" style="width: 180px;" readonly maxlength="20" value="">
                                        </div>
                                        <button type="button" onclick="$route.fn.addRow(this)" class="btn btn-primary"><i class='fa fa-plus-circle'></i></button>
                                    </div>
                                </div>

                                <div class="form-group" id="dispatchDiv">
                                    <input type="hidden" id="operTableId" value="">
                                    <label class="col-sm-1 control-label">车辆指派：</label>
                                    <div class="col-sm-6">
                                        已派遣车辆如下：
                                        <button type="button" class="btn btn-info" onclick="$route.fn.openModal()" style="margin-bottom: 10px;">
                                            <i class='fa fa-plus'></i> 新增派遣车辆</button>
                                        <div class="adv-table">
                                            <table class="display table table-bordered table-striped" id="dataTables" width="100%">
                                                <thead>
                                                <tr>
                                                    <th><input type="checkbox" class="list-parent-check"
                                                               onclick="$leoman.checkAll(this);"/></th>
                                                    <th>车牌号</th>
                                                    <th>车型</th>
                                                    <th>司机姓名</th>
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
        <div class="modal-dialog">
            <div class="modal-content" style="width: 800px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">&times;</button>
                    <h4 class="modal-title">选择派遣车辆</h4>
                </div>
                <div class="modal-body row" style="margin-bottom: -30px;">
                    <div class="form-group col-sm-2">
                        <input type="text" id="carNo" class="form-control" placeholder="车牌号">
                    </div>
                    <div class="form-group col-sm-2">
                        <input type="text" id="modelNo" class="form-control" placeholder="车型">
                    </div>
                    <div class="form-group col-sm-2">
                        <input type="text" id="driverName" class="form-control" placeholder="司机姓名">
                    </div>
                    <button id="c_search" class="btn btn-info"><i class="fa fa-search"></i> 搜索</button>
                    <button id="c_clear" class="btn btn-info"><i class="fa fa-recycle"></i> 清空</button>
                </div>
                <div class="modal-body row">
                    <button type="button" class="btn btn-info" onclick="$route.fn.multiDispatch()"><i class="fa fa-check"></i> 一键派遣</button>
                    <div class="adv-table">
                        <table class="display table table-bordered table-striped" id="dataTablesModal" width="100%">
                            <thead>
                            <tr>
                                <th><input type="checkbox" class="list-parent-check"
                                           onclick="$leoman.checkAll(this);"/></th>
                                <th>车牌号</th>
                                <th>车型</th>
                                <th>司机姓名</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 发车时间模板 -->
    <div id="timeModal" style="display: none;margin-bottom: 10px;">
        <label class="col-sm-1 control-label"></label>
        <div class="col-sm-2">
            <input type="text" class="form-control input-append date form_datetime" style="width: 180px;" readonly maxlength="20" value="">
        </div>
        <button type="button" onclick="$route.fn.removeRow(this)" class="btn btn-primary"><i class='fa fa-minus-circle'></i></button>
    </div>

    <!-- main content end-->
</section>
<%@ include file="../inc/new2/foot.jsp" %>
<%@ include file="../confirm.jsp" %>
<script>
    $route = {
        v: {
            list: [],
            chart: null,
            dTable: null,
            modalTable : null,
            imageSize: 0
        },
        fn: {
            init: function () {

                //初始化已指派车辆 和 弹出框的车辆列表
                $("#operTableId").val("dataTables");//指定要操作的表id
                $route.fn.dataTableInit("dataTables");
                $route.fn.dataTableInit("dataTablesModal");

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

                //弹出框表格的查询和清空
                $("#c_search").click(function () {
                    $route.v.modalTable.ajax.reload();
                });
                $("#c_clear").click(function () {
                    $(this).parents(".modal-body").find("input,select").val("");
                });

            },
            dataTableInit: function (tableId) {
                var inOrNot = '';
                if(tableId == 'dataTablesModal'){
                    inOrNot = 'not';

                }else if(tableId == 'dataTables'){
                    inOrNot = 'in';
                }
                var table = $leoman.dataTable($('#'+tableId), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort": false,
                    "ajax": {
                        "url": "${contextPath}/admin/bus/getSelect",
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
                        {"data": "modelNo"},
                        {"data": "driverName"},
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {
                                var oper = "";
                                var operTableId = $("#operTableId").val();
                                //如果是弹出框的车辆列表，则操作里为：派遣
                                if(operTableId == 'dataTablesModal'){
                                    oper = "<button type='button' title='派遣' class='btn btn-primary btn-circle edit' onclick=\"$route.fn.dispatch(\'" + data + "\')\">" +
                                            "<i class='fa fa-check'></i> 派遣</button>";
                                }
                                //如果是显示已经派遣的车辆列表，则操作里为：删除
                                else if(operTableId == 'dataTables') {
                                    oper = "<button type='button' title='删除' class='btn btn-primary btn-circle edit' onclick=\"$route.fn.delDispatch(\'" + data + "\')\">" +
                                            "<i class='fa fa-minus'></i> 删除</button>";
                                }
                                return oper;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.carNo = $("#carNo").val();//车牌号
                        aoData.modelNo = $("#modelNo").val();//车型
                        aoData.driverName = $("#driverName").val();//车型
                        aoData.busIds = $("#busIds").val();//已派遣的车辆，加载弹出框时过滤掉这些，避免重复选择
                        aoData.inOrNot = inOrNot;//决定显示busIds还是显示排除busIds的车辆
                    }
                });

                if(tableId == 'dataTablesModal'){
                    $route.v.modalTable = table;
                }else if(tableId == 'dataTables'){
                    $route.v.dTable = table;
                }
            },
            //企业下拉框改变事件
            enterpriseChange : function (obj){
                //如果企业类型为专线，则需要填写返程时间，不需要分派车辆
                var type = $(obj).find("option:checked").attr("type");
                if( type == '1'){
                    $("#dispatchDiv").hide();
                    $("#backTimeDiv").show();
                }
                //如果企业类型为一般，则不需要填写返程时间，但是需要分派车辆
                else{
                    $("#dispatchDiv").show();
                    $("#backTimeDiv").hide();
                }
            },
            //返回
            back : function(){
                window.location.href = "${contextPath}/admin/route/index";
            },
            //添加时间行
            addRow : function(obj){
                var model = $("#timeModal").clone().removeAttr("id");
                model.show();
                $(obj).parents(".form-group").append(model);

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
            //移除时间行
            removeRow : function(obj){
                $(obj).parent("div").remove();
            },
            //打开新增派遣车辆对话框
            openModal: function () {
                $("#operTableId").val("dataTablesModal");
                $route.v.modalTable.ajax.reload();
                $("#myModal").modal("show");
            },
            //派遣
            dispatch : function(busId){
                var busIds = $("#busIds").val();
                if(busIds != ''){
                    busIds = busIds + "," + busId;
                }else{
                    busIds = busIds + busId;
                }
                $("#busIds").val(busIds);
                $("#myModal").modal("hide");
                $route.v.dTable.ajax.reload();
                $("#operTableId").val("dataTables");
            },
            //删除派遣
            delDispatch : function(busId){
                $("#operTableId").val("dataTables");
                var newBusIds = "";
                var busIds = $("#busIds").val();
                var arr = busIds.split(",");
                for(var i = 0; i < arr.length; i++){
                    if(busId != arr[i]){
                        newBusIds += arr[i]+",";
                    }
                }
                newBusIds = newBusIds.substr(0,newBusIds.length-1);
                $("#busIds").val(newBusIds);
                $route.v.dTable.ajax.reload();
            },
            //一键派遣
            multiDispatch : function (){
                /*$leoman.optNotify(function () {
                    var checkBox = $("#dataTablesModal tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();
                    var busIds = $("#busIds").val();
                    busIds += + "," + ids;
                    $("#busIds").val(busIds);
                },"你确定要派遣已勾选的车辆吗？","确定");*/
                var checkBox = $("#dataTablesModal tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                if(ids == false){
                    alert('请至少勾选一条数据');
                    return ;
                }

                $("#confirm").modal("show");
                $('#showText').html('你确定要派遣已勾选的车辆吗？');
                $("#determine").off("click");
                $("#determine").on("click",function(){

                    var busIds = $("#busIds").val();
                    busIds += "," + ids;
                    $("#busIds").val(busIds);
                    $("#confirm").modal("hide");
                    $("#myModal").modal("hide");
                    $route.v.dTable.ajax.reload();
                });

            },
            //保存
            save : function() {
                var flag = true;



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
