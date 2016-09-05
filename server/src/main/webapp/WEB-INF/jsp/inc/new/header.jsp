<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglibs.jsp" %>
<header id="header">
    <a href="" id="menu-toggle"></a> 
    <a class="logo pull-left" href="${contextPath}/admin/dashboard">亿起娱乐后台管理系统</a>
    
    <div class="media-body">
        <div class="media" id="top-menu">
            <div id="time" class="pull-right">
                <span id="hours"></span>
                :
                <span id="min"></span>
                :
                <span id="sec"></span>
                <a href="${contextPath}/admin/logout">退&nbsp;&nbsp;&nbsp;出</a>
            </div>

            <div class="media-body">
                <input type="text" class="main-search">
            </div>
        </div>
    </div>

</header>