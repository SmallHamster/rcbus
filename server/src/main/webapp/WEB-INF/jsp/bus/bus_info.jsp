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
                            班车信息
                        </header>
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-sm-1 control-label" >车型：</label>
                                <div class="col-sm-6">
                                    ${bus.modelNo}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label" >品牌：</label>
                                <div class="col-sm-6">
                                    ${bus.brand}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label" >座位数：</label>
                                <div class="col-sm-6">
                                    ${bus.seatNum}
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </section>
    </div>
    <!-- main content end-->
</section>
<%@ include file="../inc/new2/foot.jsp" %>
<script>
</script>
</body>
</html>
