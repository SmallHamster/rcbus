<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside id="sidebar">
    <!-- Sidbar Widgets -->
    <%--<div class="side-widgets overflow">
        <!-- Profile Menu -->
        <div class="text-center s-widget m-b-25 dropdown" id="profile-menu">
            <a href="" data-toggle="dropdown">
                <img class="profile-pic animated" src="${contextPath}/html/img/profile-pic.jpg" alt="">
            </a>
            <ul class="dropdown-menu profile-menu">
                <li><a href="${contextPath}/admin/logout">退&nbsp;&nbsp;&nbsp;出</a> <i class="icon left">&#61903;</i><i class="icon right">&#61815;</i></li>
            </ul>
        </div>

        <!-- Calendar -->
        <div class="s-widget m-b-25">
            <div id="sidebar-calendar"></div>
        </div>
    </div>--%>
    
    <!-- Side Menu -->
    <ul class="list-unstyled side-menu">
        <c:if test="${sessionScope.menu_adminRole.role.id == 1}">
            <li>
                <a href="admin/dashboard"><i class="fa fa-gear fa-fw"></i>首页</a>
            </li>
        </c:if>

        <c:forEach var="n" items="${sessionScope.menu_moduleList}">
            <li class="dropdown">
                <a class="${n.description}" href="${n.url}"><span class="menu-item">${n.name}</span></a>
                <ul class="list-unstyled menu-item">
                    <c:forEach var="m" items="${n.modulesList}">
                        <li><a href="${contextPath}/admin/${m.url}">${m.name}</a></li>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
        <%--<li class="dropdown">
            <a class="sa-side-form" href="">
                <span class="menu-item">权限管理</span>
            </a>
            <ul class="list-unstyled menu-item">
                <li><a href="${contextPath}/admin/adminRole/index">管理员列表</a></li>
                <li><a href="${contextPath}/admin/role/index">权限列表</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a class="sa-side-form" href="">
                <span class="menu-item">用户管理</span>
            </a>
            <ul class="list-unstyled menu-item">
                <li><a href="${contextPath}/admin/user/index">会员列表</a></li>
                <li><a href="${contextPath}/admin/creator/index">主创列表</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a class="sa-side-chart" href="">
                <span class="menu-item">作品管理</span>
            </a>
            <ul class="list-unstyled menu-item">
                <li><a href="${contextPath}/admin/work/index">视频列表</a></li>
                <li><a href="${contextPath}/admin/novel/index">小说列表</a></li>
                <li><a href="${contextPath}/admin/comic/index">漫画列表</a></li>
                <li><a href="${contextPath}/admin/barrage/index">弹幕列表</a></li>
                <li><a href="${contextPath}/admin/comment/index">评论列表</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a class="sa-side-ui" href="">
                <span class="menu-item">分类管理</span>
            </a>
            <ul class="list-unstyled menu-item">
                <li><a href="${contextPath}/admin/category/index">作品集分类</a></li>
                <li><a href="${contextPath}/admin/category/indexPost">帖子分类</a></li>
                <li><a href="${contextPath}/admin/category/indexStill">剧照分类</a></li>
                <li><a href="${contextPath}/admin/category/indexMusic">音乐分类</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a class="sa-side-folder" href="">
                <span class="menu-item">帖子管理</span>
            </a>
            <ul class="list-unstyled menu-item">
                <li><a href="${contextPath}/admin/userPost/index">用户帖子</a></li>
                <li><a href="${contextPath}/admin/post/index">官方帖子</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a class="sa-side-page" href="">
                <span class="menu-item">资源管理</span>
            </a>
            <ul class="list-unstyled menu-item">
                <li><a href="${contextPath}/admin/still/index">剧照资源</a></li>
                <li><a href="${contextPath}/admin/music/index">音乐资源</a></li>
                <li><a href="${contextPath}/admin/game/index">游戏资源</a></li>
                <li><a href="${contextPath}/admin/welfare/index">福利社</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a class="sa-side-typography" href="">
                <span class="menu-item">站长工具</span>
            </a>
            <ul class="list-unstyled menu-item">
                <li><a href="${contextPath}/admin/banner/index">广告banner</a></li>
                <li><a href="${contextPath}/admin/adsIndex/index">首页推荐</a></li>
                <li><a href="${contextPath}/admin/floatWin/index">浮窗广告</a></li>
                <li><a href="${contextPath}/admin/creatorDynamic/index">主创互动数据</a></li>
                <li><a href="${contextPath}/admin/prize/index">相关设置</a></li>
                <li><a href="${contextPath}/admin/report/index">举报管理</a></li>
                <li><a href="${contextPath}/admin/feedback/index">意见反馈</a></li>
                <li><a href="${contextPath}/admin/exchange/index">实物兑换记录</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a class="sa-side-ui" href="">
                <span class="menu-item">消息管理</span>
            </a>
            <ul class="list-unstyled menu-item">
                <li><a href="${contextPath}/admin/message/index">消息列表</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a class="sa-side-ui" href="">
                <span class="menu-item">管理员信息</span>
            </a>
            <ul class="list-unstyled menu-item">
                <li><a href="${contextPath}/admin/admin/index">修改密码</a></li>
            </ul>
        </li>--%>
    </ul>
</aside>