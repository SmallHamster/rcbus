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
                            模块信息
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <input id="id" name="id" type="hidden" value="${module.id}">
                                <input id="createDate" name="createDate" type="hidden" value="${module.createDate}">
                                <div id="fnameDiv" class="form-group" style="display: none">
                                    <label for="fname" class="col-sm-1 control-label" >父级模块名称</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="fname" name="fname" value="${fModuleName}" class="form-control" required maxlength="20" readonly/>
                                        <input type="hidden" id="pid" name="pid" value="${pid}" class="form-control" required maxlength="20" readonly/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-sm-1 control-label" >模块名称</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="name" name="name" value="${module.name}" class="form-control" required maxlength="20"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-sm-1 control-label" >模块URL</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="url" name="url" value="${module.url}" class="form-control" readonly/>
                                    </div>
                                </div>
                                <div id="iconDiv" class="form-group" style="display: none">
                                    <label for="name" class="col-sm-1 control-label" >模块Icon</label>
                                    <input type="hidden" id="moduleIcon" name="moduleIcon" value="${module.moduleIcon}">
                                    <div class="col-sm-6">
                                        <a href="#myModal" data-toggle="modal">选择</a>
                                        <i id="icon-demo" class=""></i>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-sm-1 control-label" >模块简介</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="description" name="description" value="${module.description}" class="form-control" maxlength="500"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-6">
                                        <button type="button" onclick="$admin.fn.save()" class="btn btn-primary">保存</button>
                                    </div>
                                </div>

                                <!-- Modal -->
                                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title">选择模块图标</h4>
                                            </div>
                                            <div class="modal-body row">
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-home')"><i class="fa fa-home"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-archive')"><i class="fa fa-archive"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-bars')"><i class="fa fa-bars"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-briefcase')"><i class="fa fa-briefcase"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-book')"><i class="fa fa-book"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-cloud')"><i class="fa fa-cloud"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-cloud-download')"><i class="fa fa-cloud-download"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-cloud-upload')"><i class="fa fa-cloud-upload"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-cog')"><i class="fa fa-cog"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-comment')"><i class="fa fa-comment"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-comments')"><i class="fa fa-comments"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-desktop')"><i class="fa fa-desktop"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-download')"><i class="fa fa-download"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-group')"><i class="fa fa-group"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-music')"><i class="fa fa-music"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-signal')"><i class="fa fa-signal"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-sitemap')"><i class="fa fa-sitemap"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-trunk')"><i class="fa fa-trunk"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-users')"><i class="fa fa-users"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-user')"><i class="fa fa-user"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-folder')"><i class="fa fa-folder"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-envelope')"><i class="fa fa-envelope"></i></a>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <a href="javascript:void(0);" onclick="$admin.fn.selectIcon('fa fa-bar-chart-0')"><i class="fa fa-bar-chart-0"></i></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
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
                var fname = $("#fname").val();
                if(fname == null || fname == '') {
                    $("#fnameDiv").css("display","none");
                    $("#iconDiv").css("display","block")
                }
                else {
                    $("#fnameDiv").css("display","block");
                    $("#url").removeAttr("readonly");
                }
            },

            save : function() {
                if(!$("#formId").valid()) return;

                $("#formId").ajaxSubmit({
                    url : "${contextPath}/admin/module/save",
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            window.location.href = "${contextPath}/admin/module/index";
                        }
                        else {
                            alert("操作失败");
                        }
                    }
                });
            },
            selectIcon : function(icon) {
                $('#myModal').modal('hide')
                $("#icon-demo").removeClass().addClass(icon);
                $("#moduleIcon").val(icon);
            }
        }
    }
    $(function () {
        $admin.fn.init();
    })
</script>
</body>
</html>
