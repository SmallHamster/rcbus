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
    <title>礼券图片</title>
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
                            礼券图片
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post"  enctype="multipart/form-data">
                                <input id="id" name="id" type="hidden" value="${config.id}">

                                <div class="form-group img_tooltip" >
                                    <label for="imageId" class="col-sm-1 control-label">图片:</label>

                                    <div class="col-sm-2">
                                        <input type="hidden" id="imageId" name="image.id" value="${bus.image.id}">

                                        <div class="image_show"  <c:if test="${config.image==null}"> style="display: none"  </c:if>>
                                            <img src="${config.image.path}" class='img-responsive' >
                                        </div>
                                        <div class="image_handle"  <c:if test="${config.image!=null}">  style="display: none"  </c:if>data-toggle="tooltip" data-placement="top" title="">
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
                                        <button type="button" onclick="$coupon.fn.save()" class="btn btn-primary">保存</button>
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
    $coupon = {
        v: {
            list: [],
            chart: null,
            dTable: null,
            imageSize: 0
        },
        fn: {
            init: function () {

                $coupon.fn.dropperInit();//显示图片
                $("#removeImg").click(function(){
                    $coupon.fn.clearImageView();
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
                }).on("fileComplete.dropper", $coupon.fn.onFileComplete)
                        .on("fileError.dropper", $coupon.fn.onFileError);
            },
            onFileComplete: function (e, file, response) {
                if (response.status == '0') {
                    $coupon.fn.viewImage(response.data);
                } else {
                    $common.fn.notify('error');
                }
            },
            onFileError: function (e, file, error) {
                $common.fn.notify('error');
            },
            //保存
            save : function() {
                var flag = true;
                if(!$("#formId").valid()) return;

                if($("#imageId").val() == ''){
                    flag = false;
                    $common.fn.notify('图片不能为空');
                }

                if(flag){
                    $("#formId").ajaxSubmit({
                        url : "${contextPath}/admin/coupon/saveImage",
                        type : "POST",
                        success : function(result) {
                            if(result.status == 0) {
                                $common.fn.notify('保存成功');
                            }
                            else {
                                $common.fn.notify('操作失败');
                            }
                        }
                    });
                }
            }
        }
    }
    $(function () {
        $coupon.fn.init();
    })
</script>
</body>
</html>
