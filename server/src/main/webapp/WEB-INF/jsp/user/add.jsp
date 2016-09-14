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
                            会员
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <input id="id" name="id" type="hidden" value="">
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-1 control-label">手机</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="mobile" name="mobile" value="" class="form-control" required mobile="true"/>
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
                                        <input type="password" id="password2" name="password2" class="form-control" required equalTo="#password" minlength="6"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="enterpriseId" class="col-sm-1 control-label">企业</label>
                                    <div class="col-sm-6">
                                        <select class="form-control input-sm" id="enterpriseId" name="enterpriseId" required>
                                            <option value="">请选择</option>
                                            <c:forEach var="v" items="${enterprise}">
                                                <option value="${v.id}">${v.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <%--</div>--%>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-6">
                                        <button type="button" onclick="$user.fn.save()" class="btn btn-primary">保存</button>
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
<%@ include file="../inc/new2/foot.jsp" %>
<script>
    $user = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {
                $("#formId").validate();
            },
            save : function() {
                if(!$("#formId").valid()) return;
                $("#formId").ajaxSubmit({
                    url : "${contextPath}/admin/user/save",
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            window.location.href = "${contextPath}/admin/user/index";
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
        $user.fn.init();
    })
</script>
</body>
</html>
