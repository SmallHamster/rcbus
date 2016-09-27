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
    <title>新增车辆</title>
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
                            新增车辆
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post"  enctype="multipart/form-data">
                                <input id="id" name="id" type="hidden" value="${bus.id}">

                                <div class="form-group">
                                    <label for="modelNo" class="col-sm-1 control-label" ><span style="color: red;">* </span>车型：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="modelNo" name="modelNo" value="${bus.modelNo}" class="form-control" required maxlength="20"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="brand" class="col-sm-1 control-label"><span style="color: red;">* </span>品牌：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="brand" name="brand" value="${bus.brand}" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="seatNum" class="col-sm-1 control-label"><span style="color: red;">* </span>座位数：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="seatNum" name="seatNum" value="${bus.seatNum}" class="form-control" required number-0="true"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="uuid" class="col-sm-1 control-label"><span style="color: red;">* </span>车辆唯一标识：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="uuid" name="uuid" value="${bus.uuid}" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="carNo" class="col-sm-1 control-label"><span style="color: red;">* </span>车牌号：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="carNo" name="carNo" value="${bus.carNo}" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="policyNo" class="col-sm-1 control-label">保险单号：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="policyNo" name="policyNo" value="${bus.policyNo}" class="form-control" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="driverName" class="col-sm-1 control-label"><span style="color: red;">* </span>司机姓名：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="driverName" name="driverName" value="${bus.driverName}" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="driverPhone" class="col-sm-1 control-label"><span style="color: red;">* </span>联系电话：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="driverPhone" name="driverPhone" value="${bus.driverPhone}" class="form-control" required mobile="true"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="driverIDCard" class="col-sm-1 control-label">司机身份证号：</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="driverIDCard" name="driverIDCard" value="${bus.driverIDCard}" class="form-control" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">司机性别：</label>
                                    <div class="col-sm-1">
                                        <input type="radio" name="driverSex" value="0" checked="checked">男
                                        &nbsp;
                                        <input type="radio" name="driverSex" value="1">女

                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="carType.id" class="col-sm-1 control-label">用车类型：</label>
                                    <div class="col-sm-1">
                                        <select class="form-control input-sm" id="carType.id" name="carType.id" style="width: 150px;">
                                            <c:forEach items="${typeList}" var="type">
                                                <c:if test="${type.id == bus.carType.id}">
                                                    <option value="${type.id}" selected="selected">${type.name}</option>
                                                </c:if>
                                                <c:if test="${type.id != bus.carType.id}">
                                                    <option value="${type.id}">${type.name}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group img_tooltip" >
                                    <label for="imageId" class="col-sm-1 control-label"><span style="color: red;">* </span>图片:</label>

                                    <div class="col-sm-2">
                                        <input type="hidden" id="imageId" name="image.id" value="${bus.image.id}">

                                        <div class="image_show"  <c:if test="${bus.image==null}"> style="display: none"  </c:if>>
                                            <img src="${bus.image.path}" class='img-responsive' >
                                        </div>
                                        <div class="image_handle"  <c:if test="${bus.image!=null}">  style="display: none"  </c:if>data-toggle="tooltip" data-placement="top" title="">
                                            <div class="dropped"></div>
                                        </div>
                                    </div>
                                    <div class="col-sm-5">
                                        <a href="javascript:void(0)" id="removeImg" class="btn btn-info" role="button" >删除图片</a>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label"></label>
                                    <div class="col-sm-6">
                                        <button type="button" onclick="$bus.fn.save()" class="btn btn-primary">保存</button>
                                        <button type="button" onclick="$bus.fn.back()" class="btn btn-primary">返回</button>
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
                    $("[name=driverSex][value="+"${bus.driverSex}"+"]").click();
                }
                $bus.fn.dropperInit();//显示图片
                $("#removeImg").click(function(){
                    $bus.fn.clearImageView();
                })
            },
            clearImageView: function(){
                $("#imageId").val("");
                $(".image_show").html("");
                $(".image_handle").show();
                $(".dropper-input").val("");
            },
            viewImage: function (image) {
                if (image) {
                    $(".dropper-input").val("");
                    $(".image_handle").hide();
                    $(".image_show").show();
                    $("#imageId").val(image.id);
                    $(".image_show").html("<img src='" + image.path + "' class='img-responsive' >");
                }
            },
            dropperInit: function () {
                $(".dropped").dropper({
                    postKey: "file",
                    action: "${contextPath}/common/file/save/image",
                    postData: {thumbSizes: '480x800'},
                    label: "把图片拖拽到此处",
                    maxSize: 204857
                }).on("fileComplete.dropper", $bus.fn.onFileComplete)
                        .on("fileError.dropper", $bus.fn.onFileError);
            },
            onFileComplete: function (e, file, response) {
                if (response.status == '0') {
                    $bus.fn.viewImage(response.data);
                } else {
                    $leoman.alertMsg('error');
                }
            },
            onFileError: function (e, file, error) {
                $leoman.alertMsg('error');
            },
            back : function(){
                window.location.href = "${contextPath}/admin/bus/index";
            },
            //保存
            save : function() {
                var flag = true;
                if(!$("#formId").valid()) return;

                if($("#imageId").val() == ''){
                    flag = false;
                    $leoman.alertMsg('图片不能为空');
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
                                $leoman.alertMsg('操作失败');
                            }
                        }
                    });
                }
            }
        }
    }
    $(function () {
        $bus.fn.init();
    })
</script>
</body>
</html>
