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
    <style type="text/css">
        #dataTables tfoot tr th{
            background-color:rgba(191, 196, 208, 0.5);
            border:0;
        }

    </style>
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
                    <section class="panel">
                        <div class="panel-body">
                            <div class="form-group col-sm-2">
                                <input type="text" id="orderNo" name="orderNo" class="form-control" placeholder="订单号">
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="driverName" name="driverName" class="form-control" placeholder="司机姓名">
                            </div>

                            <div class="form-group col-sm-2">
                                <input type="text" id="carNo" name="carNo" class="form-control" placeholder="车牌号码">
                            </div>

                            <div class="form-group col-sm-2">
                                <input type="text" id="start" name="start" class="form-control input-append date form_datetime" placeholder="发车时间(大于)">
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="end" name="end" class="form-control input-append date form_datetime" placeholder="发车时间(小于)">
                            </div>
                            <div class="form-group col-sm-2">
                                <button id="c_search" class="btn btn-info">搜索</button>
                                <button id="c_clear" class="btn btn-info"><i class="fa fa-recycle"></i> 清空</button>
                            </div>
                            <div class="form-group col-sm-2">
                                <button id="c_export" onclick="$income.fn.export()" class="btn btn-info"><i class="fa fa-recycle"></i> 导出EXCEL</button>
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
                            <span class="tools pull-right">
                               <button class="btn btn-default " type="button" id="c_refresh"><i class="fa fa-refresh"></i>刷新</button>
                            </span>
                        </header>
                        <div class="panel-body">
                            <div class="adv-table">
                                <table class="display table table-bordered table-striped" id="dataTables" width="100%">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" class="list-parent-check"
                                                   onclick="$leoman.checkAll(this);"/></th>
                                        <th>订单号</th>
                                        <th>发车时间</th>
                                        <th>客人名称</th>
                                        <th>客人手机</th>
                                        <th>订单金额</th>
                                        <th>已收金额</th>
                                        <th>退款金额</th>
                                        <th>巴士数量</th>
                                        <th>详情</th>
                                    </tr>
                                    </thead>
                                    <tbody></tbody>
                                    <tfoot>
                                    <tr>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                    </tr>
                                    </tfoot>
                                </table>
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
    $income = {
        v: {
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $income.fn.dataTableInit();

                $("#c_search").click(function () {
                    $income.v.dTable.ajax.reload();
                });
                //清空
                $("#c_clear").click(function () {
                    $(this).parents(".panel-body").find("input,select").val("");
                });
                //刷新
                $("#c_refresh").click(function () {
                    $("#c_clear").parents(".panel-body").find("input,select").val("");
                    $income.v.dTable.ajax.reload();
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
                $income.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort": false,

                    "ajax": {
                        "url": "${contextPath}/admin/carRental/list",
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
                            "data": "order.orderNo",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "startDate",
                            "render": function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                            },
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "order.userName",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "order.mobile",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "totalAmount",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "income",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "refund",
                            "sDefaultContent" : ""
                        },
                        {
                            "data": "busNum",
                            "sDefaultContent" : ""

                        },
                        {
                            "data": "id",
                            "render": function (data, type, row, meta) {
                                var detail = "<button title='查看' class='btn btn-primary btn-circle add' onclick=\"$income.fn.detail(\'" + data + "\')\">" +
                                        "<i class='fa fa-eye'></i> 查看</button>";
                                return  detail ;
                            }
                        }

                    ],
                    "footerCallback": function( tfoot, data, start, end, display ) {
                        var api = this.api();
                        $( api.column( 5 ).footer() ).html(
                                api.column( 5 ).data().reduce( function ( a, b ) {
                                    var sum = a + b;
                                    console.log("sum1:"+sum);
                                    return sum;
                                } )
                        );
                        $( api.column( 6 ).footer() ).html(
                                api.column( 6 ).data().reduce( function ( a, b ) {
                                    var sum = a + b;
                                    console.log("sum2:"+sum);
                                    return sum;
                                } )
                        );
                        $( api.column( 7 ).footer() ).html(
                                api.column( 7 ).data().reduce( function ( a, b ) {
                                    var sum = a + b;
                                    console.log("sum3:"+sum);
                                    return sum;
                                } )
                        )
                    },

                    "fnServerParams": function (aoData) {
                        aoData.orderNo = $("#orderNo").val();
                        aoData.driverName = $("#driverName").val();
                        aoData.carNo = $("#carNo").val();
                        aoData.Dstart = $("#start").val();
                        aoData.Dend = $("#end").val();
                    }

                });
            },
            detail: function (id) {
                var params = "";
                if (id != null && id != '') {
                    params = "?id=" + id;
                }
                window.location.href = "${contextPath}/admin/carRental/income/detail" + params;
            },
            export: function () {
                window.location.href = "${contextPath}/admin/export/exportFeedback";
            },

            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        $income.v.dTable.ajax.reload(null, false);
                    } else {
                        $income.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    };
    $(function () {
        $income.fn.init();
    });
//    window.onload=function(){
//        var sum  = 0.0;
//        $("#dataTables tbody tr").each(function(){
//            var amount = $(this).find("td").eq("5").text()
//            sum += parseFloat(amount);
//        });
//        console.log(sum);
//        $("#dataTables tfoot th[name=totalAmount]").text(sum);
//        console.log(1)
//    }
</script>
</body>
</html>
