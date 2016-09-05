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
    <div class="main-content">
        <%@ include file="../../inc/new2/header.jsp" %>
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            基本实例
                        </header>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal adminex-form" id="formId" method="post" >
                                <div class="form-group">
                                    <label for="username" class="col-sm-1 control-label" >必填验证</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="username" name="username" value="" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="text1" class="col-sm-1 control-label" >必填验证,最大最小验证</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="text1" name="text1" value="" class="form-control" required minlength="6" maxlength="20"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="password" class="col-sm-1 control-label">密码</label>
                                    <div class="col-sm-6">
                                        <input type="password" id="password" name="password" class="form-control" required minlength="6"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="password" class="col-sm-1 control-label">确认密码,输入一致性验证</label>
                                    <div class="col-sm-6">
                                        <input type="password" id="password2" name="password2" class="form-control" required equalTo="#password" required minlength="6"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-1 control-label">手机验证</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="mobile" name="mobile" value="${admin.mobile}" class="form-control" required mobile="true"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-1 control-label">请输入长度在 {0} 到 {1} 之间的字符串</label>
                                    <div class="col-sm-6">
                                        <input type="text" id="text2" name="text2" value="${admin.mobile}" class="form-control" required rangelength="2,5"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-1 control-label">类型</label>
                                    <div class="col-sm-1">
                                        <select class="form-control input-sm" required>
                                            <option value="">全部</option>
                                            <option value="10">10</option>
                                            <option value="10">20</option>
                                            <option value="10">30</option>
                                            <option value="10">40</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-1 control-label">其他说明</label>
                                    验证框架中没有的验证正则表达式，可自行在jquery.validate.extend.js文件中添加
                                    <br>
                                    具体参考手机验证
                                </div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-1 control-label">图片测试</label>
                                    <div class="col-sm-6">
                                        <input type="hidden" id="imageIds" name="imageIds" value>
                                        <input id="the_file" name="files" type="file" multiple=true class="file-loading">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-1 control-label">富文本测试</label>
                                    <div class="col-sm-6">
                                        <!--style给定宽度可以影响编辑器的最终宽度-->
                                        <script type="text/plain" id="myEditor" style="width:1000px;height:240px;">
                                            <p>这里我可以写一些输入提示</p>
                                        </script>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="mobile" class="col-sm-1 control-label">富文本基本功能测试</label>
                                    <div class="col-sm-6">
                                        <table>
                                            <tr>
                                                <td>
                                                    <button class="btn" unselected="on" onclick="getAllHtml()">获得整个html的内容</button>&nbsp;
                                                    <button class="btn" onclick="getContent()">获得内容</button>&nbsp;
                                                    <button class="btn" onclick="setContent()">写入内容</button>&nbsp;
                                                    <button class="btn" onclick="setContent(true)">追加内容</button>&nbsp;
                                                    <button class="btn" onclick="getContentTxt()">获得纯文本</button>&nbsp;
                                                    <button class="btn" onclick="getPlainTxt()">获得带格式的纯文本</button>&nbsp;
                                                    <button class="btn" onclick="hasContent()">判断是否有内容</button>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <button class="btn" onclick="setFocus()">编辑器获得焦点</button>&nbsp;
                                                    <button class="btn" onmousedown="isFocus();return false;">编辑器是否获得焦点</button>&nbsp;
                                                    <button class="btn" onclick="doBlur()">编辑器取消焦点</button>&nbsp;
                                                    <button class="btn" onclick="insertHtml()">插入给定的内容</button>&nbsp;
                                                    <button class="btn" onclick="getContentTxt()">获得纯文本</button>&nbsp;
                                                    <button class="btn" id="enable" onclick="setEnabled()">可以编辑</button>&nbsp;
                                                    <button class="btn" onclick="setDisabled()">不可编辑</button>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <button class="btn" onclick="UM.getEditor('myEditor').setHide()">隐藏编辑器</button>&nbsp;
                                                    <button class="btn" onclick="UM.getEditor('myEditor').setShow()">显示编辑器</button>&nbsp;
                                                    <button class="btn" onclick="UM.getEditor('myEditor').setHeight(300)">设置编辑器的高度为300</button>&nbsp;
                                                    <button class="btn" onclick="UM.getEditor('myEditor').setWidth(1200)">设置编辑器的宽度为1200</button>
                                                </td>
                                            </tr>

                                        </table>
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
                $admin.fn.initValidate();
                $admin.fn.initEdior();
                $admin.fn.initialPreview();
                $admin.fn.initialPreviewConfig();
                $admin.fn.initImage();
            },
            initEdior : function() {
                var um = UM.getEditor('myEditor');
            },
            initValidate : function() {
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
            // 初始化预览图
            initialPreview:function(){
                var imgPreViews = [];
                    <c:forEach var="_image" items="${list}" >
                    var img =  "<img src='${_image.caUrl}' style ='height:160px'>";
                    imgPreViews.push(img);
                    </c:forEach>
                return imgPreViews;
            },
            // 初始化预览图配置
            initialPreviewConfig:function(){
                var imgPreViewsConf = [];
                <c:forEach var="_image" items="${list}" >
                    var conf = {
                        caption: "",
                        width: "120px",
                        url: "${contextPath}/admin/dynamic/deleteImage?id=${_image.id}",
                        key: ${_image.id}
                    };
                    imgPreViewsConf.push(conf);
                </c:forEach>
                return imgPreViewsConf;

            },
            // 初始化图片上传控件
            initImage:function(){
                var $input = $("#the_file");
                $input.fileinput({
                    language: 'zh',
                    uploadUrl: "${contextPath}/admin/dynamic/uploadCa", // server upload action
                    minFileCount: 1,
                    maxFileCount: 3,
                    initialPreview: $admin.fn.initialPreview(),
                    initialPreviewConfig: $admin.fn.initialPreviewConfig(),
                    msgFilesTooMany:"只能上传三张图片",
                    allowedFileTypes:['image']
                }).on('filebatchuploadsuccess', function(event, data, previewId, index) {
                    var response = data.response;
                    if(response.status){
                        var imageIds = "";
                        $.each(response.data,function(index,data){
                            imageIds+=data.id+",";
                        })
                        if(imageIds.length>0){
                            imageIds =  imageIds.substr(0,imageIds.length-1);
                        }
                        $("#imageIds").val(imageIds);
                        $("#formId").ajaxSubmit({
                            dataType: "json",
                            success: function (result) {
                                if(result.status) {
                                    window.location.href = "${contextPath}/admin/dynamic/index";
                                }
                            }
                        });
                    }
                });
            },

            save : function() {
                if(!$("#formId").valid()) return;
                return;
            },


        }
    }
    $(function () {
        $admin.fn.init();
    })
</script>
<script type="text/javascript">
    //实例化编辑器
    var um = UM.getEditor('myEditor');
    um.addListener('blur',function(){
        $('#focush2').html('编辑器失去焦点了')
    });
    um.addListener('focus',function(){
        $('#focush2').html('')
    });
    //按钮的操作
    function insertHtml() {
        var value = prompt('插入html代码', '');
        um.execCommand('insertHtml', value)
    }
    function isFocus(){
        alert(um.isFocus())
    }
    function doBlur(){
        um.blur()
    }
    function createEditor() {
        enableBtn();
        um = UM.getEditor('myEditor');
    }
    function getAllHtml() {
        alert(UM.getEditor('myEditor').getAllHtml())
    }
    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UM.getEditor('myEditor').getContent());
        alert(arr.join("\n"));
    }
    function getPlainTxt() {
        var arr = [];
        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
        arr.push("内容为：");
        arr.push(UM.getEditor('myEditor').getPlainTxt());
        alert(arr.join('\n'))
    }
    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用umeditor')方法可以设置编辑器的内容");
        UM.getEditor('myEditor').setContent('欢迎使用umeditor', isAppendTo);
        alert(arr.join("\n"));
    }
    function setDisabled() {
        UM.getEditor('myEditor').setDisabled('fullscreen');
        disableBtn("enable");
    }

    function setEnabled() {
        UM.getEditor('myEditor').setEnabled();
        enableBtn();
    }

    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UM.getEditor('myEditor').selection.getRange();
        range.select();
        var txt = UM.getEditor('myEditor').selection.getText();
        alert(txt)
    }

    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UM.getEditor('myEditor').getContentTxt());
        alert(arr.join("\n"));
    }
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UM.getEditor('myEditor').hasContents());
        alert(arr.join("\n"));
    }
    function setFocus() {
        UM.getEditor('myEditor').focus();
    }
    function deleteEditor() {
        disableBtn();
        UM.getEditor('myEditor').destroy();
    }
    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }
    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            domUtils.removeAttributes(btn, ["disabled"]);
        }
    }
</script>
</body>
</html>
