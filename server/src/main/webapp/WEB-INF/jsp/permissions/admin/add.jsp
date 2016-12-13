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
                            <input type="hidden" id="role" name="role" value="${roleId}">
                            <input type="hidden" id="enterprise" name="enterprise" value="${admin.enterprise.id}">
                            <input type="hidden" id="userEp" name="userEp" value="${userInfo.enterprise.id}">
                            <input type="hidden" id="userType" name="userType" value="${userInfo.type}">
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
                                        <input type="password" id="password2" name="password2" class="form-control" required equalTo="#password" minlength="6"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-1 control-label">手机</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="mobile" name="mobile" value="${admin.mobile}" class="form-control" mobile="true"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="roleId" class="col-sm-1 control-label">权限</label>
                                    <div class="col-sm-6">
                                        <select class="form-control input-sm" id="roleId" name="roleId" required>
                                            <option value="">请选择</option>
                                            <c:forEach var="v" items="${role}">
                                                <option value="${v.id}">${v.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="div_hidden" id="div_hidden" style="display: none">
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
                var userType = $("#userType").val();
                console.log("userType :"+userType);
/*                var role = $("#role").val();
                var enterprise = $("#enterprise").val();*/

                var role,
                    enterprise;

                if(userType != ''){
                    if(userType == 0){
                        role = 12;
                        enterprise = $("#userEp").val();
                    }else {
                        role = $("#role").val();
                        enterprise = $("#enterprise").val();
                    }

                }
                console.log("role :"+role);
                console.log("enterprise :"+enterprise);
                if(role!=null && role!=""){
                    $("#roleId").attr("disabled",true);
                    $("#roleId option").each(function(){
                        if($(this).val() == role){
                            $(this).attr("selected",true)
                        }
                    })
                }

                if(enterprise!=null && enterprise!=""){
                    $("#enterpriseId").attr("disabled",true);
                    $("#enterpriseId option").each(function(){
                        if($(this).val() == enterprise){
                            $(this).attr("selected",true)
                        }
                    })
                }
                $admin.fn.chk();
                $("#roleId").change(function(){
//                    var checkText=$("#role").find("option:selected").text();
//                    if(checkText=="企业管理员"){
                    $admin.fn.chk();
                });

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
            chk: function(){
                if($("#roleId").val()==12){
                    $("#div_hidden").css('display','block');
                }else {
                    $("#div_hidden").css('display','none');
                }
            },
            save : function() {
                var enterprise_id = $("#enterpriseId").val();
                var role_id = $("#roleId").val();
                console.log(enterprise_id);
                console.log(role_id);
                if(!$("#formId").valid()) return;
                $("#formId").ajaxSubmit({
                    url : "${contextPath}/admin/admin/save",
                    data : {
                        enterprise_id : enterprise_id,
                        role_id : role_id
                    },
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            window.location.href = "${contextPath}/admin/admin/index";
                        }
                        else {
                            alert(result.msg);
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
