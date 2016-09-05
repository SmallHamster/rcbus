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
                            角色信息
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <input id="id" name="id" type="hidden" value="${role.id}">
                                <input id="createDate" name="createDate" type="hidden" value="${role.createDate}">
                                <div class="form-group">
                                    <label for="name" class="col-sm-1 control-label" >角色名称</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="name" name="name" value="${role.name}" class="form-control" required maxlength="20"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-sm-1 control-label" >模块授权</label>
                                    <div class="col-sm-6">
                                        <c:forEach items="${modules}" var="module">
                                            ${module.name}:&nbsp;
                                            <c:forEach items="${module.list}" var="subModule">
                                                <input type="checkbox" name="moduleIds" value="${subModule.id}" <c:if test="${fns:contains(subModule.id, subModules)}"> checked="checked" </c:if> /> ${subModule.name}
                                                &nbsp;
                                            </c:forEach>
                                            <hr>
                                        </c:forEach>
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

            },

            save : function() {
                if(!$("#formId").valid()) return;
                var checked = $("input[name='moduleIds']:checkbox:checked");
                var moduleIds = [];
                checked.each(function(){
                    moduleIds.push($(this).val());
                });
                console.log("moduleIds:" + moduleIds);
                $("#formId").ajaxSubmit({
                    url : "${contextPath}/admin/role/save",
                    data : {
                        "moduleIds" : moduleIds
                    },
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            window.location.href = "${contextPath}/admin/role/index";
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
