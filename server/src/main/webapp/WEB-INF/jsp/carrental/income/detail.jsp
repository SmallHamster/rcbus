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
    <title>Form Layouts</title>
    <%@ include file="../../inc/new2/css.jsp" %>
    <style type="text/css">

        .border{
            background-color: #EEE;
            margin:20px 0px 20px 120px;
            width:500px;
            height:160px;
            border:1px solid #CCCCCC;
            -moz-border-radius: 15px;
            -webkit-border-radius: 15px;
            border-radius:15px;
            float: left;
        }
        .pl{
            margin-left: 20px;
            margin-top: 15px;
            font-size: 20px
        }
        .pr{
            margin-right: 20px;
            margin-top: 15px;
            font-size: 20px
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
        <section class="wrapper">
            <!-- page start-->

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            详情
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <input id="id" name="id" type="hidden" value="">
                                <header class="panel-heading">
                                    报价信息
                                </header>
                                <div style="margin-top: 15px"></div>
                                <c:forEach var="v" items="${carRentalOffer}">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">${v.name}</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="offter" name="offter" value="${v.amount}" class="form-control" disabled/>
                                        </div>
                                    </div>
                                </c:forEach>
                                <c:if test="${carRentalOffer ne null}">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">总金额</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="totalAmount" name="totalAmount" value="${carRental.totalAmount}" class="form-control" disabled/>
                                        </div>
                                    </div>
                                </c:if>
                                <header class="panel-heading">
                                    车辆信息
                                </header>
                                <div style="margin-top: 15px"></div>
                                <div class="row">
                                    <c:forEach items="${busSend}" var="v">
                                        <div class="col-md-6">
                                            <div class="border">
                                                <div style="float: left">
                                                    <p class="pl">车牌号：${v.bus.carNo}</p>
                                                    <p class="pl">座位数：${v.bus.seatNum}</p>
                                                    <p class="pl">司机姓名：${v.bus.driverName}</p>
                                                    <p class="pl">性别：<c:if test="${v.bus.driverSex eq 0}">男</c:if><c:if test="${v.bus.driverSex eq 1}">女</c:if></p>
                                                </div>
                                                <div style="float: right">
                                                    <p class="pr">车型：${v.bus.modelNo}</p>
                                                    <p class="pr">品牌：${v.bus.brand}</p>
                                                    <p class="pr">身份证：${v.bus.driverIDCard}</p>
                                                    <p class="pr">联系方式：${v.bus.driverPhone}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <%--</div>--%>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"></label>
                                    <div class="col-sm-6">
                                        <button type="button" class="btn btn-primary" onclick="history.go(-1);">返回</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>
        </section>
    </div>
    <!-- main content end-->
</section>
<%@ include file="../../inc/new2/foot.jsp" %>
<script>
    $admin = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {
                $("#formId").validate();
            }
        }
    }
    $(function () {
        $admin.fn.init();
    })
</script>
</body>
</html>
