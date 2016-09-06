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
                            添加班车
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post"  enctype="multipart/form-data">
                                <input id="id" name="id" type="hidden" value="${bus.id}">
                                <input type="hidden" id="addImageIds" name="tempAddImageIds">
                                <input type="hidden" id="delImageIds" name="tempDelImageIds">
                                <span id="tempAddImageIds" style="display: none"></span>
                                <span id="tempDelImageIds" style="display: none"></span>

                                <div class="form-group">
                                    <label for="modelNo" class="col-sm-1 control-label" >车型：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="modelNo" name="modelNo" value="${bus.username}" class="form-control" required minlength="6" maxlength="20"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="brand" class="col-sm-1 control-label">品牌：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="brand" name="brand" value="${bus.mobile}" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="seatNum" class="col-sm-1 control-label">座位数：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="seatNum" name="seatNum" value="${bus.mobile}" class="form-control" required />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="carNo" class="col-sm-1 control-label">车牌号：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="carNo" name="carNo" value="${bus.carNo}" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="policyNo" class="col-sm-1 control-label">保险单号：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="policyNo" name="policyNo" value="${bus.policyNo}" class="form-control" required />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="driverName" class="col-sm-1 control-label">司机姓名：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="driverName" name="driverName" value="${bus.driverName}" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="driverPhone" class="col-sm-1 control-label">司机联系电话：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="driverPhone" name="driverPhone" value="${bus.driverPhone}" class="form-control" required mobile="true"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="driverIDCard" class="col-sm-1 control-label">司机身份证号：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="driverIDCard" name="driverIDCard" value="${bus.driverIDCard}" class="form-control" required />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">司机性别：</label>
                                    <div class="col-sm-1">
                                        <select class="form-control input-sm" name="driverSex">
                                            <option value="1">男</option>
                                            <option value="0">女</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="carType.id" class="col-sm-1 control-label">用车类型：</label>
                                    <div class="col-sm-1">
                                        <select class="form-control input-sm" id="carType.id" name="carType.id">
                                            <option value="1">通勤班车</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">图片:</label>
                                    <div class="col-sm-1">
                                        <div style="float: left;margin-bottom: 30px;" id="lastImageDiv">
                                            <a href="javascript:void(0);" onclick="$bus.fn.AddTempImg()">
                                                <img id="tempPicture" src="${contextPath}/static/images/add.jpg" style="height: 200px; width: 200px; display: inherit; margin-bottom: 6px;" border="1"/>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <%--<div class="col-md-12 m-b-15">
                                    <label>封面：</label>
                                    <div class="fileupload fileupload-new" data-provides="fileupload">
                                        <div class="fileupload-preview thumbnail form-control">
                                            <img src="">
                                        </div>
                                        <div>
                                <span class="btn btn-file btn-alt btn-sm">
                                    <span class="fileupload-new">选择图片</span>
                                    <span class="fileupload-exists">更改</span>
                                    <input id="imageFile" name="imageFile" type="file"/>
                                </span>
                                            <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">移除</a>
                                        </div>
                                    </div>
                                </div>--%>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-6">
                                        <button type="button" onclick="$bus.fn.save()" class="btn btn-primary">保存</button>
                                    </div>
                                </div>

                                <form id="tempImageForm" method="post" action="common/file/save/image" enctype="multipart/form-data" class="form-horizontal" role="form">
                                    <input type="file" name="file" id="tempImage" data-rule="required" style="display:none;" onchange="$bus.fn.saveTempImage()"/>
                                </form>
                                <div id="tempDiv" style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 15px;">
                                    <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                                    <input name="imageIdTemp" type="hidden"/>
                                    <a href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 203px; left: 184px; display: none;" class="axx" onclick="$bus.fn.deleteImage(this)">
                                        <img id="pic" src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                    </a>
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
    $bus = {
        v: {
            list: [],
            chart: null,
            dTable: null,
            imageSize: 0
        },
        fn: {
            init: function () {
                if("${bus.driverSex}" != ''){
                    $("[name=driverSex] option[value='+${bus.driverSex}+']").click();
                }
            },
            AddTempImg: function () {
                $('#tempImage').click();
            },
            saveTempImage: function () {
                $("#tempImageForm").ajaxSubmit({
                    dataType: "json",
                    success: function (data) {
                        if (null != data.path && data.path != '') {
                            $('#tempAddImageIds').html($('#tempAddImageIds').html() + data.id + ',');
                            $bus.fn.insertImage(data.path, data.id);

                            $bus.v.imageSize = $bus.v.imageSize + 1;
                        } else {
                            $leoman.notify("图片格式不正确", "error");
                        }
                    }
                });
            },
            insertImage: function (path, id) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.children(":first").next().next().next().prop("value", id);
                tempDiv.insertBefore("#lastImageDiv");

                // 让所有的克隆出来的
                tempDiv.hover(function () {
                    $bus.fn.mouseover($(this));
                }, function () {
                    $bus.fn.mouseOut($(this));
                });
            },
            deleteImage: function (self) {
                $bus.v.imageSize = $bus.v.imageSize - 1;
                var imageId = $(self).prev().val();
                $('#tempDelImageIds').html($('#tempDelImageIds').html() + imageId + ',');
                $(self).parent().remove();
            },
            mouseover: function (mouse) {
                $(mouse).children("a").fadeIn(300);
            },
            mouseOut: function (mouse) {
                $(mouse).children("a").fadeOut(300);
            },
            //保存
            save : function() {
                var flag = true;
                if(!$("#formId").valid()) return;

                if($('.fileupload-preview img').size()<1 || $('.fileupload-preview img').width()==0){
                    flag = false;
                    $leoman.notify('图片不能为空', "error");
                }

                if(flag){
                    $("#formId").ajaxSubmit({
                        url : "${contextPath}/admin/bus/save",
                        type : "POST",
                        success : function(result) {
                            if(result.status == 0) {
                                window.location.href = "${contextPath}/admin/bus/index";
                            }
                            else {
                                alert("操作失败");
                            }
                        }
                    });
                }
            },
            initImage : function() {
                $('#file-fr').fileinput({
                    language: 'zh',
                    uploadAsync: false,
                    showUpload: false, // hide upload button
                    showRemove: false, // hide remove button
                    uploadUrl: '#',
                    minFileCount: 1,
                    maxFileCount: 3,
                    msgFilesTooMany:"只能上传三张图片",
                    allowedFileExtensions : ['jpg', 'png'],
                });
            }
        }
    }
    $(function () {
        $bus.fn.init();
    })
</script>
</body>
</html>
