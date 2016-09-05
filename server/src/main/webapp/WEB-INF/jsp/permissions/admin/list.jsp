<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="format-detection" content="telephone=no">
    <meta charset="UTF-8">
    <meta name="description" content="Violate Responsive Admin Template">
    <meta name="keywords" content="Super Admin, Admin, Template, Bootstrap">
    <title>Super Admin Responsive Template</title>
    <!-- CSS -->
    <%@ include file="../inc/new/css.jsp" %>
</head>
<body id="skin-cloth">
<%@ include file="../inc/new/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="clearfix"></div>
<section id="main" class="p-relative" role="main">
    <%@ include file="../inc/new/menu.jsp" %>
    <section id="content" class="container">
        <!-- Breadcrumb -->
        <ol class="breadcrumb hidden-xs">
            <li><a href="javascript:history.go(-1);" title="返回"><span class="icon">&#61771;</span></a></li>
        </ol>
        <h1 class="page-title">密码修改</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data"
              class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="admin" name="admin" value="${admin.password}">
                <div class="row">
                    <div class="col-md-6 m-b-15">
                        <label>登录密码：</label>
                        <input type="password" id="password" name="password"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                        <label>新密码：</label>
                        <input type="password" id="newPassword" name="newPassword" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                        <label>确认新登录密码：</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="$admin.fn.save();">提交</button>
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="history.go(-1);">返回</button>
                    </div>
                </div>
            </div>
        </form>

    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>

<script>
    $admin = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {

//                $("#fromId").validationEngine();

            },
            checkData: function () {
                var flag = true;
                var p = $('#admin').val();
                var password = $('#password').val();
                var newPassword = $('#newPassword').val();
                var confirmPassword = $('#confirmPassword').val();

                if (null == password || password == '') {
                    $common.fn.notify("请输入登录密码", "error");
                    flag = false;
                    return;
                }

                /*if (!password != p) {
                    $common.fn.notify("登录密码错误", "error");
                    flag = false;
                    return;
                }*/

                if (null == newPassword || newPassword == '') {
                    $common.fn.notify("请输入新密码", "error");
                    flag = false;
                    return;
                }

                if (null == confirmPassword || confirmPassword == '') {
                    $common.fn.notify("请输入确认新密码", "error");
                    flag = false;
                    return;
                }

                if (newPassword != confirmPassword) {
                    $common.fn.notify("确认密码不正确，请重新输入", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            save: function () {
//                if(!$("#fromId").validationEngine("validate")) return;

                if ($admin.fn.checkData()) {
                    $("#fromId").ajaxSubmit({

                        url: "${contextPath}/admin/admin/save",
                        type: "POST",
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                            } else if (result == 2){
                                $common.fn.notify("登录密码错误", "error");
                            } else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
        }
    }
    $(function () {
        $admin.fn.init();
    })
</script>
</body>
</html>

