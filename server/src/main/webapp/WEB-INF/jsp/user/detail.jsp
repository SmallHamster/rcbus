<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags" prefix="date" %>
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
<div class="clearfix"></div>
<section id="main" class="p-relative" role="main">
    <%@ include file="../inc/new/menu.jsp" %>
    <section id="content" class="container">
        <!-- Breadcrumb -->
        <ol class="breadcrumb hidden-xs">
            <li><a href="javascript:history.go(-1);" title="返回"><span class="icon">&#61771;</span></a></li>
        </ol>
        <h1 class="page-title">帖子详情</h1>
        <div class="block-area">
            <h2 class="tile-title">基本信息</h2>
            <div class="p-10" style="height:520px">
                <hr class="whiter m-t-20"/>
                <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                    <label>头像:</label>
                    <img class="imgs" alt="" src="${userInfo.avater}" style="height: 200px;width: 300px; z-index: 1;"/>
                </div>
                <hr class="whiter m-t-20"/>
                <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                    <label>账号:</label>
                    <input type="text" id="mobile" value="${userInfo.mobile}" name="mobile" class="input-sm form-control validate[required]" placeholder="..." disabled>
                </div>
                <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                    <label>昵称:</label>
                    <input type="text" id="nickname" value=" ${userInfo.nickname}" name="nickname" class="input-sm form-control validate[required]" placeholder="..." disabled>
                </div>
                <div class="col-md-6 m-b-15">
                    <label>性别:</label>
                    <input type="text" id="gender" name="gender" value="<c:if test="${userInfo.gender eq 0}">男</c:if><c:if test="${userInfo.gender eq 1}">女</c:if>" class="input-sm form-control validate[required]" placeholder="..." disabled>
                </div>
                <div class="col-md-6 m-b-15">
                    <label>状态:</label>
                    <input type="text" id="status" name="status" value="<c:if test="${userInfo.status eq 0}">正常</c:if><c:if test="${userInfo.status eq 1}">封禁</c:if>" class="input-sm form-control validate[required]" placeholder="..." disabled>
                </div>
                <div class="col-md-6 m-b-15">
                    <label>主册时间:</label>
                    <input type="text" id="createDate" name="createDate" class="input-sm form-control validate[required]" placeholder="..." disabled value="<date:date value="${userInfo.createDate}" format="yyyy-MM-dd HH:mm:ss"></date:date>" />
                </div>
                <div class="col-md-6 m-b-15">
                    <label>会员类型:</label>
                    <input type="text" id="isCreator" value="<c:if test="${userInfo.isCreator eq 0}">普通会员</c:if><c:if test="${userInfo.isCreator eq 1}">主创</c:if>" name="isCreator" class="input-sm form-control validate[required]" placeholder="..." disabled>
                </div>
                <div class="col-md-6 m-b-15">
                    <label>会员等级:</label>
                    <input type="text" id="level" value="<c:if test="${userInfo.level eq 0}">非会员</c:if><c:if test="${userInfo.level ne 0}">Lv${userInfo.level}</c:if>" name="level" class="input-sm form-control validate[required]" placeholder="..." disabled>
                </div>
                <div class="col-md-6 m-b-15">
                    <label>粉丝数:</label>
                    <input type="text" id="funs" value="${userInfo.funs}" name="funs" class="input-sm form-control validate[required]" placeholder="..." disabled>
                </div>
                <div class="col-md-6 m-b-15">
                    <label>关注数:</label>
                    <input type="text" id="focus" value="${userInfo.focus}" name="focus" class="input-sm form-control validate[required]" placeholder="..." disabled>
                </div>
                <div class="col-md-6 m-b-15">
                    <label>发帖数:</label>
                    <input type="text" id="posts" value="${userInfo.posts}" name="posts" class="input-sm form-control validate[required]" placeholder="..." disabled>
                </div>
                <div class="col-md-6 m-b-15">
                    <label>主创点赞数:</label>
                    <input type="text" id="praises" value="${userInfo.praises}" name="praises" class="input-sm form-control validate[required]" placeholder="..." disabled>
                </div>
                <hr class="whiter m-t-20"/>
                <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                    <label>收货地址：</label>
                    <c:if test="${null == addressList || addressList.size() == 0 }">
                        暂无收货地址
                    </c:if>
                    <c:if test="${null != addressList && addressList.size() > 0 }">
                        <c:forEach var="co" items="${addressList}">
                            <ul id="ul" + ${co.id }>
                                <span style="color:white;">${co.name}</span>&nbsp;&nbsp;
                                <span style="color:white;">${co.mobile}</span>&nbsp;&nbsp;
                                <span style="color:white;">${co.address}</span>&nbsp;&nbsp;
                            </ul>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>
<script>
    var user = {
        v: {
            id: "post",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

            },
        }
    }
    $(function () {
        user.fn.init();
    })
</script>
<script>
    $('.form_datetime').datetimepicker({
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: "2",
        forceParse: 0,
        showMeridian: 1,
        format: 'yyyy-mm-dd'
    });
</script>
</body>
</html>

