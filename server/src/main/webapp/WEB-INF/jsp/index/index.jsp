<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
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
    <%@ include file="../inc/new/header.jsp"%>
        <div class="clearfix"></div>
        
        <section id="main" class="p-relative" role="main">
            <%@ include file="../inc/new/menu.jsp"%>
            <!-- Content -->
            <section id="content" class="container">
            
                <!-- Messages Drawer -->
                <div id="messages" class="tile drawer animated">
                    <div class="listview narrow">
                        <div class="media">
                            <a href="">Send a New Message</a>
                            <span class="drawer-close">&times;</span>
                            
                        </div>
                        <div class="overflow" style="height: 254px">
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${contextPath}/html/img/profile-pics/1.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Nadin Jackson - 2 Hours ago</small><br>
                                    <a class="t-overflow" href="">Mauris consectetur urna nec tempor adipiscing. Proin sit amet nisi ligula. Sed eu adipiscing lectus</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${contextPath}/html/img/profile-pics/2.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">David Villa - 5 Hours ago</small><br>
                                    <a class="t-overflow" href="">Suspendisse in purus ut nibh placerat Cras pulvinar euismod nunc quis gravida. Suspendisse pharetra</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${contextPath}/html/img/profile-pics/3.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Harris worgon - On 15/12/2013</small><br>
                                    <a class="t-overflow" href="">Maecenas venenatis enim condimentum ultrices fringilla. Nulla eget libero rhoncus, bibendum diam eleifend, vulputate mi. Fusce non nibh pulvinar, ornare turpis id</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${contextPath}/html/img/profile-pics/4.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Mitch Bradberry - On 14/12/2013</small><br>
                                    <a class="t-overflow" href="">Phasellus interdum felis enim, eu bibendum ipsum tristique vitae. Phasellus feugiat massa orci, sed viverra felis aliquet quis. Curabitur vel blandit odio. Vestibulum sagittis quis sem sit amet tristique.</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${contextPath}/html/img/profile-pics/1.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Nadin Jackson - On 15/12/2013</small><br>
                                    <a class="t-overflow" href="">Ipsum wintoo consectetur urna nec tempor adipiscing. Proin sit amet nisi ligula. Sed eu adipiscing lectus</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${contextPath}/html/img/profile-pics/2.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">David Villa - On 16/12/2013</small><br>
                                    <a class="t-overflow" href="">Suspendisse in purus ut nibh placerat Cras pulvinar euismod nunc quis gravida. Suspendisse pharetra</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${contextPath}/html/img/profile-pics/3.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Harris worgon - On 17/12/2013</small><br>
                                    <a class="t-overflow" href="">Maecenas venenatis enim condimentum ultrices fringilla. Nulla eget libero rhoncus, bibendum diam eleifend, vulputate mi. Fusce non nibh pulvinar, ornare turpis id</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${contextPath}/html/img/profile-pics/4.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Mitch Bradberry - On 18/12/2013</small><br>
                                    <a class="t-overflow" href="">Phasellus interdum felis enim, eu bibendum ipsum tristique vitae. Phasellus feugiat massa orci, sed viverra felis aliquet quis. Curabitur vel blandit odio. Vestibulum sagittis quis sem sit amet tristique.</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${contextPath}/html/img/profile-pics/5.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Wendy Mitchell - On 19/12/2013</small><br>
                                    <a class="t-overflow" href="">Integer a eros dapibus, vehicula quam accumsan, tincidunt purus</a>
                                </div>
                            </div>
                        </div>
                        <div class="media text-center whiter l-100">
                            <a href=""><small>VIEW ALL</small></a>
                        </div>
                    </div>
                </div>

                <div id="page-wrapper">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header" style="text-align: center;">${sessionScope.menu_adminRole.admin.username}，欢迎进入亿起娱乐后台管理系统</h1>
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>

                    <c:if test="${sessionScope.menu_adminRole.role.id == 6}">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <a href="/yqyl_server/admin/user/index" class="btn btn-outline btn-primary btn-group-lg" role="button">新增会员（${addUserNum}）</a>
                                        <a href="/yqyl_server/admin/userPost/index" class="btn btn-outline btn-primary btn-group-lg" role="button">新增用户帖子（${addUserPostNum}）</a>
                                        <a href="/yqyl_server/admin/postComment/index" class="btn btn-outline btn-primary btn-group-lg" role="button">新增帖子回复（${addPostCommentNum}）</a>
                                        <a href="/yqyl_server/admin/comment/indexNovel" class="btn btn-outline btn-primary btn-group-lg" role="button">新增小说评论（${addNovelCommentNum}）</a>
                                        <a href="/yqyl_server/admin/comment/index" class="btn btn-outline btn-primary btn-group-lg" role="button">新增视频评论（${addVideoCommentNum}）</a>
                                        <a href="/yqyl_server/admin/comment/indexComic" class="btn btn-outline btn-primary btn-group-lg" role="button">新增漫画评论（${addComicCommentNum}）</a>
                                        <a href="/yqyl_server/admin/feedback/index" class="btn btn-outline btn-primary btn-group-lg" role="button">新增意见反馈（${addFeedBackNum}）</a>
                                        <a href="/yqyl_server/admin/barrage/index" class="btn btn-outline btn-primary btn-group-lg" role="button">新增视频弹幕（${addVideoBarrageNum}）</a>
                                        <a href="/yqyl_server/admin/barrage/indexComic" class="btn btn-outline btn-primary btn-group-lg" role="button">新增漫画弹幕（${addComicBarrageNum}）</a>
                                        <a href="/yqyl_server/admin/report/index" class="btn btn-outline btn-primary btn-group-lg" role="button">新增举报（${addReportNum}）</a>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="panel-body">
                            <h4>帖子统计信息:</h4>
                            <div class="col-sm-12">
                                <table class="table table-striped table-bordered table-hover">
                                    <colgroup>
                                        <col class="gradeA odd"/>
                                        <col class="gradeA even"/>
                                        <col class="gradeA odd"/>
                                        <col class="gradeA even"/>
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <td width="25%;" class="textAling">用户帖子总数</td>
                                        <td width="25%;" class="textAling">${userPostNum}</td>
                                        <td width="25%;" class="textAling">官方帖子总数</td>
                                        <td width="25%;" class="textAling">${postNum}</td>
                                    </tr>
                                    <tr>
                                        <td width="25%;" class="textAling">加精帖子</td>
                                        <td width="25%;" class="textAling">${essencePostNum}</td>
                                        <td width="25%;" class="textAling">投票贴</td>
                                        <td width="25%;" class="textAling">${tpPostNum}</td>
                                    </tr>
                                    <tr>
                                        <td width="25%;" class="textAling">已锁帖</td>
                                        <td width="25%;" class="textAling">${lockPostNum}</td>
                                        <td width="25%;" class="textAling">直播贴</td>
                                        <td width="25%;" class="textAling">${zbPostNum}</td>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>

                        <div class="panel-body">
                            <h4>资源统计信息:</h4>
                            <div class="col-sm-12">
                                <table class="table table-striped table-bordered table-hover">
                                    <colgroup>
                                        <col class="gradeA odd"/>
                                        <col class="gradeA even"/>
                                        <col class="gradeA odd"/>
                                        <col class="gradeA even"/>
                                    </colgroup>
                                    <thead>
                                    <tr width="100px">
                                        <td width="25%;" class="textAling">视频总集数</td>
                                        <td width="25%;" class="textAling">${videoSum}</td>
                                        <td width="25%;" class="textAling">小说总部数</td>
                                        <td width="25%;" class="textAling">${novelSum}</td>
                                    </tr>
                                    <tr>
                                        <td width="25%;" class="textAling">漫画总部数</td>
                                        <td width="25%;" class="textAling">${comicSum}</td>
                                        <td width="25%;" class="textAling">弹幕总数</td>
                                        <td width="25%;" class="textAling">${barrageSum}</td>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>

                        <div class="panel-body">
                            <h4>用户统计信息:</h4>
                            <div class="col-sm-12">
                                <table class="table table-striped table-bordered table-hover">
                                    <colgroup>
                                        <col class="gradeA odd"/>
                                        <col class="gradeA even"/>
                                        <col class="gradeA odd"/>
                                        <col class="gradeA even"/>
                                    </colgroup>
                                    <thead>
                                    <tr width="100px">
                                        <td width="25%;" class="textAling">全部会员</td>
                                        <td width="25%;" class="textAling">${userInfoSum}</td>
                                        <td width="25%;" class="textAling">主创会员</td>
                                        <td width="25%;" class="textAling">${creatorSum}</td>
                                    </tr>
                                    <tr>
                                        <td width="25%;" class="textAling">来源安卓平台</td>
                                        <td width="25%;" class="textAling">${checkNum}</td>
                                        <td width="25%;" class="textAling">来源安卓平台</td>
                                        <td width="25%;" class="textAling">${spikesNum}</td>
                                    </tr>
                                    <tr>
                                        <td width="25%;" class="textAling">来源苹果平台</td>
                                        <td width="25%;" class="textAling">${checkNum}</td>
                                        <td width="25%;" class="textAling">来源苹果平台</td>
                                        <td width="25%;" class="textAling">${spikesNum}</td>
                                    </tr>
                                    <tr width="100px">
                                        <td width="25%;" class="textAling"></td>
                                        <td width="25%;" class="textAling"></td>
                                        <td width="25%;" class="textAling">后台添加</td>
                                        <td width="25%;" class="textAling">${packagesNum}</td>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>

                        <div class="panel-body">
                            <h4>统计访问:</h4>
                            <div class="col-sm-12">
                                <table class="table table-striped table-bordered table-hover">
                                    <colgroup>
                                        <col class="gradeA odd"/>
                                        <col class="gradeA even"/>
                                        <col class="gradeA odd"/>
                                        <col class="gradeA even"/>
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <td width="25%;" class="textAling">今日访问</td>
                                        <td width="25%;" class="textAling">${visitCount}</td>
                                        <td width="25%;" class="textAling">在线人数</td>
                                        <td width="25%;" class="textAling">${onlineCount}</td>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </c:if>

                </div>

            </section>

            <!-- Older IE Message -->
            <!--[if lt IE 9]>
                <div class="ie-block">
                    <h1 class="Ops">Ooops!</h1>
                    <p>You are using an outdated version of Internet Explorer, upgrade to any of the following web browser in order to access the maximum functionality of this website. </p>
                    <ul class="browsers">
                        <li>
                            <a href="https://www.google.com/intl/en/chrome/browser/">
                                <img src="img/browsers/chrome.png" alt="">
                                <div>Google Chrome</div>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.mozilla.org/en-US/firefox/new/">
                                <img src="img/browsers/firefox.png" alt="">
                                <div>Mozilla Firefox</div>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.opera.com/computer/windows">
                                <img src="img/browsers/opera.png" alt="">
                                <div>Opera</div>
                            </a>
                        </li>
                        <li>
                            <a href="http://safari.en.softonic.com/">
                                <img src="img/browsers/safari.png" alt="">
                                <div>Safari</div>
                            </a>
                        </li>
                        <li>
                            <a href="http://windows.microsoft.com/en-us/internet-explorer/downloads/ie-10/worldwide-languages">
                                <img src="img/browsers/ie.png" alt="">
                                <div>Internet Explorer(New)</div>
                            </a>
                        </li>
                    </ul>
                    <p>Upgrade your browser for a Safer and Faster web experience. <br/>Thank you for your patience...</p>
                </div>   
            <![endif]-->
        </section>
        <%@ include file="../inc/new/foot.jsp"%>
    </body>
</html>
