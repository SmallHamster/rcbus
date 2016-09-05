<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- left side start-->
<div class="left-side sticky-left-side">
    <!--logo and iconic logo start-->
    <div class="logo">
        <a href="index.html"><img src="${contextPath}/static/html/images/logo.png" alt=""></a>
    </div>
    <div class="logo-icon text-center">
        <a href="index.html"><img src="${contextPath}/static/html/images/logo_icon.png" alt=""></a>
    </div>
    <!--logo and iconic logo end-->
    <div class="left-side-inner">
        <!-- visible to small devices only -->
        <div class="visible-xs hidden-sm hidden-md hidden-lg">
            <div class="media logged-user">
                <img alt="" src="${contextPath}/static/html/images/photos/user-avatar.png" class="media-object">
                <div class="media-body">
                    <h4><a href="#">John Doe</a></h4>
                    <span>"Hello There..."</span>
                </div>
            </div>
            <h5 class="left-nav-title">Account Information</h5>
            <ul class="nav nav-pills nav-stacked custom-nav">
                <li><a href="#"><i class="fa fa-user"></i> <span>Profile</span></a></li>
                <li><a href="#"><i class="fa fa-cog"></i> <span>Settings</span></a></li>
                <li><a href="#"><i class="fa fa-sign-out"></i> <span>Sign Out</span></a></li>
            </ul>
        </div>
        <ul id="side-menu" class="nav nav-pills nav-stacked custom-nav">
            <li><a href="javascript:void(0);"><i class="fa fa-home"></i><span>Dashboard</span></a></li>
            <c:forEach items="${sessionScope.moduleList}" var="topModule">
            <li class="menu-list"><a href="javascript:void(0);"><i class="${topModule.moduleIcon}"></i><span>${topModule.name}</span></a>
            <%--<li class="menu-list"><a href="javascript:void(0);"><i class="fa fa fa-users"></i><span>权限管理</span></a>--%>
                    <ul class="sub-menu-list">
                        <c:forEach items="${topModule.list}" var="subModule">
                            <li><a href="${contextPath}${subModule.url}">${subModule.name}</a></li>
                        </c:forEach>
                        <%--<li><a href="${contextPath}/admin/admin/index">管理员列表</a></li>--%>
                        <%--<li><a href="${contextPath}/admin/role/index">角色列表</a></li>--%>
                        <%--<li><a href="${contextPath}/admin/module/index">模块列表</a></li>--%>
                    </ul>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
