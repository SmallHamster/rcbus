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
                            用户基本信息
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <input id="id" name="id" type="hidden" value="${admin.id}">
                                <input id="createDate" name="createDate" type="hidden" value="${admin.createDate}">
                                <div class="form-group">
                                    <label for="username" class="col-sm-1 control-label" >账号</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="username" name="username" value="${admin.username}" class="form-control" required minlength="6" maxlength="20"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="password" class="col-sm-1 control-label">密码</label>
                                    <div class="col-sm-6">
                                        <input type="password" id="password" name="password" class="form-control" required minlength="6"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="password" class="col-sm-1 control-label">确认密码</label>
                                    <div class="col-sm-6">
                                        <input type="password" id="password2" name="password2" class="form-control" required equalTo="#password" required minlength="6"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-1 control-label">手机</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="mobile" name="mobile" value="${admin.mobile}" class="form-control" required mobile="true"/>
                                    </div>
                                </div>
                                <%--<div class="form-group">--%>
                                    <%--<label for="mobile" class="col-sm-1 control-label">类型</label>--%>
                                    <%--<div class="col-sm-1">--%>
                                        <%--<select class="form-control input-sm">--%>
                                            <%--<option value="10">10</option>--%>
                                            <%--<option value="10">20</option>--%>
                                            <%--<option value="10">30</option>--%>
                                            <%--<option value="10">40</option>--%>
                                        <%--</select>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
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
                $("#formId").validate({
                    rules : {
                        username : {
                            remote : {
                                url : "${contextPath}/admin/admin/check/username",
                                type : "post",
                                data : {
                                    "username" : function() {
                                        return $("#username").val()
                                    },
                                    "id" : function() {
                                        return $("#id").val()
                                    }
                                }
                            }
                        }
                    },
                    messages : {
                        username : {
                            remote : "用户名已存在"
                        }
                    }
                });
            },

            save : function() {
                if(!$("#formId").valid()) return;

                $("#formId").ajaxSubmit({
                    url : "${contextPath}/admin/admin/save",
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            window.location.href = "${contextPath}/admin/admin/index";
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
