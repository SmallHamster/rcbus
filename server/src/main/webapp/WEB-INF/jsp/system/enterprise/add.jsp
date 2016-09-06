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
                            企业
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <input id="id" name="id" type="hidden" value="${enterprise.id}">
                                <div class="form-group">
                                    <label for="name" class="col-sm-1 control-label" >企业名称</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="name" name="name" value="${enterprise.name}" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-6">
                                        <button type="button" onclick="$admin.fn.save()" class="btn btn-primary">保存</button>
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
                <%--$("#formId").validate({--%>
                    <%--rules : {--%>
                        <%--username : {--%>
                            <%--remote : {--%>
                                <%--url : "${contextPath}/admin/admin/check/username",--%>
                                <%--type : "post",--%>
                                <%--data : {--%>
                                    <%--"username" : function() {--%>
                                        <%--return $("#username").val()--%>
                                    <%--},--%>
                                    <%--"id" : function() {--%>
                                        <%--return $("#id").val()--%>
                                    <%--}--%>
                                <%--}--%>
                            <%--}--%>
                        <%--}--%>
                    <%--},--%>
                    <%--messages : {--%>
                        <%--username : {--%>
                            <%--remote : "该企业已存在"--%>
                        <%--}--%>
                    <%--}--%>
                <%--});--%>
            },

            save : function() {
                if(!$("#formId").valid()) return;

                $("#formId").ajaxSubmit({
                    url : "${contextPath}/admin/enterprise/save",
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            window.location.href = "${contextPath}/admin/enterprise/index";
                        }
                        else {
                            alert("操作失败");
                        }
                    }
                });
            }
        }
    }
    $(function () {
        $admin.fn.init();
    })
</script>
</body>
</html>
