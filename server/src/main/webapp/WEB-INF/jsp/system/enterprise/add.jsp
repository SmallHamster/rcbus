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
                                <c:if test="${enterprise.id ne null}">
                                    <div class="form-group">
                                        <label for="name" class="col-sm-1 control-label" >类型</label>
                                        <div class="col-sm-6">
                                            <input name="type" value="<c:if test="${enterprise.type eq 0}">企业</c:if><c:if test="${enterprise.type eq 1}">专线</c:if>" class="form-control" disabled/>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${enterprise.id eq null}">
                                    <div class="form-group">
                                        <label for="name" class="col-sm-1 control-label" >类型</label>
                                        <div class="col-sm-6">
                                            <select id="type" name="type" value="${enterprise.type}" class="form-control" required>
                                                <option value="">请选择</option>
                                                <option value="0">企业</option>
                                                <option value="1">专线</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group" id="userName_hidden" style="display: none">
                                        <label for="name" class="col-sm-1 control-label" >企业管理员账号</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="userName" name="userName" value="" class="form-control" required/>
                                        </div>
                                    </div>
                                </c:if>
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
                $("#type").change(function(){
                    $admin.fn.userNameChk($("#type").val());
                });
            },
            userNameChk: function(data){
                console.log(data);
                if(data!='' && data==0){
                    $("#userName_hidden").css('display','block');
                }else{
                    $("#userName_hidden").css('display','none');
                }
            },
            save : function() {
                if(!$("#formId").valid()) return;
                var userName = $("#userName").val();
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
