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
                            会员
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <input id="id" name="id" type="hidden" value="${banner.id}">
                                <input id="isOc" type="hidden" value="${banner.isOc}">
                                <input id="po" type="hidden" value="${banner.position}">
                                <div class="form-group img_tooltip" >
                                    <label for="imageId" class="col-sm-1 control-label">图片</label>
                                    <div class="col-sm-2">
                                        <input type="hidden" id="imageId" name="imageId" value="${banner.image.id}">
                                        <div class="image_show"  <c:if test="${banner.image==null}"> style="display: none"  </c:if>>
                                            <img src="${banner.image.uploadUrl}" class='img-responsive' >
                                        </div>
                                        <div class="image_handle"  <c:if test="${banner.image!=null}">  style="display: none"  </c:if>data-toggle="tooltip" data-placement="top" title="">
                                            <div class="dropped"></div>
                                        </div>
                                    </div>
                                    <div class="col-sm-5">
                                        <a href="javascript:void(0)" id="removeImg" class="btn btn-info" role="button" >删除图片</a>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label">图片位置</label>
                                    <div class="col-sm-6">
                                        <select class="form-control input-sm" id="position" name="position" required>
                                            <option value="">请选择</option>
                                            <option value="0">通勤巴士</option>
                                            <option value="1">永旺专线</option>
                                            <option value="2">用车预定</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">添加外链</label>
                                    <div class="col-sm-9 icheck " id="oc">
                                        <div class="square-red single-row">
                                            <div class="radio ">
                                                <input tabindex="3" type="radio"  name="isOc" value="1">
                                                <label>是 </label>
                                            </div>
                                        </div>
                                        <div class="square-yellow  single-row">
                                            <div class="radio ">
                                                <input tabindex="3" type="radio"  name="isOc" value="0">
                                                <label>否 </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group" style="margin-top: 15px;display: none" id="outsideChain_hidden" >
                                    <label class="col-sm-1 control-label">外链</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="outsideChain" name="outsideChain" value="${banner.outsideChain}" class="form-control" required/>
                                    </div>
                                </div>

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
                $("#formId").validate();

                $admin.fn.dropperInit();//显示图片
                $("#removeImg").click(function(){
                    $admin.fn.clearImageView();
                })

                //包车
                $admin.fn.outsideChainChk($("#isOc").val());
                $("#oc .iCheck-helper").click(function(){
                    if($("input[name='isOc']:checked").val()==0){
                        $("#outsideChain").val("");
                    }
                    $admin.fn.outsideChainChk($("input[name='isOc']:checked").val());
                });
                $("input[name='isOc']").each(function(){
                    var isOc = $("#isOc").val();
                    if(isOc == $(this).val()){
                        $(this).iCheck("check");
                    }
                });

                //图片位置
                $("#position option").each(function() {
                    if($(this).val()==$("#po").val()){
                        $(this).attr("selected","selected");
                    }
                })


            },
            outsideChainChk: function(data){
                if(data==1){
                    $("#outsideChain_hidden").css('display','block');
                }else {
                    $("#outsideChain_hidden").css('display','none');
                }
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
                }).on("fileComplete.dropper", $admin.fn.onFileComplete)
                        .on("fileError.dropper", $admin.fn.onFileError);
            },
            onFileComplete: function (e, file, response) {
                if (response.status == '0') {
                    $admin.fn.viewImage(response.data);
                } else {
                    $common.fn.notify('error');
                }
            },
            onFileError: function (e, file, error) {
                $common.fn.notify('error');
            },
            save : function() {
                if(!$("#formId").valid()) return;

                if($("#imageId").val() == ''){
                    $common.fn.notify('图片不能为空');
                    return;
                }

                $("#formId").ajaxSubmit({
                    url : "${contextPath}/admin/banner/save",
                    type : "POST",
                    success : function(result) {
                        if(result.status == 0) {
                            window.location.href = "${contextPath}/admin/banner/index";
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
