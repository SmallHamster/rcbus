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
    <title>新增/编辑路线</title>
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
                            新增/编辑路线
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" enctype="multipart/form-data">
                                <input type="hidden" id="routeId" name="id" value="${route.id}">
                                <input type="hidden" name="isShow" value="${route.isShow}">
                                <input type="hidden" id="busIds" name="busIds" value="${busIds}">
                                <%--<input type="hidden" id="isRoundTrip" name="isRoundTrip" value="0">--%>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >所属企业：</label>
                                    <div class="col-sm-1">
                                        <select class="form-control input-sm" name="enterprise.id">
                                            <c:forEach items="${enterpriseList}" var="enterprise">
                                                <c:if test="${enterprise.id == route.enterprise.id}">
                                                    <option value="${enterprise.id}" selected>${enterprise.name}</option>
                                                </c:if>
                                                <c:if test="${enterprise.id != route.enterprise.id}">
                                                    <option value="${enterprise.id}">${enterprise.name}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label" ><span style="color: red;">* </span>所属路线：</label>
                                    <div class="col-sm-2">
                                        <input type="text" name="lineName" value="${route.lineName}" class="form-control" required maxlength="30"/>
                                    </div>
                                </div>

                                <c:if test="${route.id == null}">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >是否有返程：</label>
                                    <div class="col-sm-1">
                                        <select class="form-control input-sm" onchange="$route.fn.tripChange(this)"name="isRoundTrip">
                                            <option value="0">否</option>
                                            <option value="1">是</option>
                                        </select>
                                    </div>
                                </div>
                                </c:if>


                                <div class="form-group">
                                    <label class="col-sm-1 control-label">线路添加：</label>
                                    <div class="col-sm-2">
                                        <input type="file" name="file" id="file" title="选择gpx文件导入" class="btn btn-default required" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.externalLink+xml">
                                        (重新导入保存后，则覆盖原有路线)
                                    </div>
                                    <%--&nbsp;&nbsp;<button type="button" id="importBtn" class="btn btn-info" onclick="$route.fn.uploadRoute()">导入</button>--%>
                                </div>

                                <c:if test="${route.id != null}">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">原有路线：</label>
                                    <div class="col-sm-6" style="margin-bottom: 10px;">
                                        ${stationStr}
                                    </div>
                                </div>
                                </c:if>

                                <div class="form-group" id="departTimeDiv">

                                </div>

                                <div class="form-group" id="backTimeDiv" style="display: none;">
                                    <div style="margin-bottom: 10px;">
                                        <label class="col-sm-1 control-label">返程时间:</label>
                                        <div class="col-sm-2">
                                            <input type="text" name="backTimes" class="form-control input-append date form_datetime" style="width: 180px;" readonly maxlength="20" value="">
                                        </div>
                                        <button type="button" onclick="$route.fn.addRow(this)" class="btn btn-primary"><i class='fa fa-plus-circle'></i></button>
                                    </div>
                                </div>

                                <div class="form-group">
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
                                                    <th>是否监控</th>
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
                                <th>是否监控</th>
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
        <button type="button" onclick="$route.fn.addRow(this)" class="btn btn-primary"><i class='fa fa-plus-circle'></i></button>
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

                //初始化发车时间和返程时间
                $route.fn.timeInit('depart');

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
            //是否有返程
            tripChange :function(obj){
                //有往返
                if( $(obj).val() == 1 && $("#routeId").val() ==''){
                    $("#backTimeDiv").show();
                }
                //单程
                else{
                    $("#backTimeDiv").hide();
                }
            },
            //返回
            back : function(){
                window.location.href = "${contextPath}/admin/route/index";
            },
            //时间控件初始化
            datetimepickerInit : function(){
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
            //初始化时间
            timeInit : function(){

                //新增
                if($("#routeId").val() == ''){
                    var model = $("#timeModal").clone().removeAttr("id");
                    model.find("label").text("发车时间：");
                    model.find(".fa-minus-circle").parents("button").hide();
                    model.find("input[type=text]").attr("name","departTimes");
                    model.show();
                    $("#departTimeDiv").append(model);
                }
                //编辑
                else{
                    var arr = eval('('+'${timeJson}'+')');
                    for(var i = 0; i < arr.length; i++){
                        var model = $("#timeModal").clone().removeAttr("id");
                        if(i == 0){
                            model.find("label").text("发车时间：");
                            model.find(".fa-minus-circle").parents("button").hide();
                        }else{
                            model.find(".fa-plus-circle").parents("button").hide();
                        }
                        model.find("input[type=text]").attr("name","departTimes");
                        model.find("input[type=text]").val(arr[i].departTime);
                        model.show();
                        $("#departTimeDiv").append(model);
                    }
                }

                //时间控件初始化
                $route.fn.datetimepickerInit();

            },
            //添加时间行
            addRow : function(obj){
                var model = $("#timeModal").clone().removeAttr("id");
                model.find(".fa-plus-circle").parents("button").hide();
                var divId = $(obj).parents(".form-group").attr("id");
                var name = "";
                if(divId == 'departTimeDiv'){
                    name = 'departTimes';
                }else if(divId == 'backTimeDiv'){
                    name = 'backTimes';
                }
                model.find("input[type=text]").attr("name",name);
                model.show();
                $(obj).parents(".form-group").append(model);

                //时间控件初始化
                $route.fn.datetimepickerInit();

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
                    busIds = busId;
                }
                $("#busIds").val(busIds);
                $("#myModal").modal("hide");
                $route.v.dTable.ajax.reload();
                $("#operTableId").val("dataTables");
            },
            //删除派遣
            delDispatch : function(busId){
                $("#operTableId").val("dataTables");
                var newBusIds = [];
                var busIds = $("#busIds").val();
                var arr = busIds.split(",");
                for(var i = 0; i < arr.length; i++){
                    if(busId != arr[i]){
                        newBusIds.push(arr[i]);
                    }
                }
                $("#busIds").val(newBusIds.join(","));
                $route.v.dTable.ajax.reload();
            },
            //一键派遣
            multiDispatch : function (){
                var checkBox = $("#dataTablesModal tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                if(ids == false){
                    $leoman.alertMsg('请至少勾选一条数据');
                    return ;
                }

                $leoman.alertConfirm("你确定要派遣已勾选的车辆吗？",function(){
                    var busIds = $("#busIds").val();
                    if(busIds != ''){
                        busIds = busIds + "," + ids;
                    }else{
                        busIds = ids;
                    }
                    $("#busIds").val(busIds);
                    $("#myModal").modal("hide");
                    $route.v.dTable.ajax.reload();
                });

            },
            //保存
            save : function() {
                var flag = true;

                if($("#busIds").val() == ''){
                    flag = false;
                    $leoman.alertMsg('请至少派遣一辆车');
                }

                if(flag){
                    $("#formId").ajaxSubmit({
                        url : "${contextPath}/admin/route/save",
                        type : "POST",
                        success : function(result) {
                            if(result.status == 0) {
                                window.location.href = "${contextPath}/admin/route/index";
                            }
                            else {
                                $leoman.alertMsg(result.msg);
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
