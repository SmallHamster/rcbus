<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
        <meta name="format-detection" content="telephone=no">
        <meta charset="UTF-8">
        <meta name="description" content="Violate Responsive Admin Template">
        <meta name="keywords" content="Super Admin, Admin, Template, Bootstrap">
        <title>Super Admin Responsive Template</title>
        <!-- CSS -->
        <%@ include file="../inc/new/css.jsp"%>
    </head>
    <body id="skin-cloth">
        <section id="login">
            <header>
                <h1>后台管理系统</h1>
            </header>
            <div class="clearfix"></div>
            <!-- Login -->
            <form role="form" class="box tile animated active form-validation-1" id="box-login" action="${contextPath}/admin/login/check">
                <h2 class="m-t-0 m-b-15">登录</h2>
                <input type="text" id="username" name="username" class="login-control m-b-10 validate[required]" placeholder="请输入用户名">
                <input type="password" id="password" name="password" class="login-control validate[required]" placeholder="请输入密码">
                <c:if test="${!empty error}">
                    <label class="control-label">${error}</label>
                </c:if>
                <div class="checkbox m-b-20">
                    <label>
                        <input type="checkbox" id="remark" name="remark">
                        记住我
                    </label>
                </div>
                <button class="btn btn-sm m-r-5"　type="submit">登录</button>

                <%--<small>--%>
                    <%--<a class="box-switcher" data-switch="box-register" href="">Don't have an Account?</a> or--%>
                    <%--<a class="box-switcher" data-switch="box-reset" href="">Forgot Password?</a>--%>
                <%--</small>--%>
            </form>
            
            <!-- Register -->
            <form class="box animated tile" id="box-register">
                <h2 class="m-t-0 m-b-15">Register</h2>
                <input type="text" class="login-control m-b-10" placeholder="Full Name">
                <input type="text" class="login-control m-b-10" placeholder="Username">
                <input type="email" class="login-control m-b-10" placeholder="Email Address">    
                <input type="password" class="login-control m-b-10" placeholder="Password">
                <input type="password" class="login-control m-b-20" placeholder="Confirm Password">
                <button class="btn btn-sm m-r-5">Register</button>
                <small><a class="box-switcher" data-switch="box-login" href="">Already have an Account?</a></small>
            </form>
            
            <!-- Forgot Password -->
            <form class="box animated tile" id="box-reset">
                <h2 class="m-t-0 m-b-15">Reset Password</h2>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla eu risus. Curabitur commodo lorem fringilla enim feugiat commodo sed ac lacus.</p>
                <input type="email" class="login-control m-b-20" placeholder="Email Address">    

                <button class="btn btn-sm m-r-5">Reset Password</button>

                <small><a class="box-switcher" data-switch="box-login" href="">Already have an Account?</a></small>
            </form>
        </section>
        <%@ include file="../inc/new/foot.jsp"%>
    </body>
</html>
