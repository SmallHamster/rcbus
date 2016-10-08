package com.leoman.common.filter;

import com.leoman.common.logger.Logger;
import com.leoman.entity.Configue;
import com.leoman.entity.Constant;
import com.leoman.utils.BeanUtils;
import com.leoman.permissions.admin.entity.Admin;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.WeChatUser;
import com.leoman.utils.HttpUtil;
import com.leoman.utils.WebUtil;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangbin on 2015/8/10.
 */
public class WechatFilter implements Filter {

    private static String[] SKIP_URLS = new String[]{};

    private static String[] USER_2_FORBID_URLS = new String[]{"/wechat/route/index/0"};//普通会员不能访问的路径

    private static String[] USER_0_FORBID_URLS = new String[]{"/wechat/enterprise/apply"};//企业不能访问的路径

    private static Map<Integer,String[]> FORBID_URL_Map = new HashedMap();

    public WechatFilter() {
        FORBID_URL_Map.put(0,USER_0_FORBID_URLS);
        FORBID_URL_Map.put(2,USER_2_FORBID_URLS);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("skipUrls");
        if (StringUtils.isNotBlank(urls)) {
            String temp[] = urls.split(",");
            List<String> list = new ArrayList<String>();

            for (String skipUrl : temp) {
                if (StringUtils.isNotBlank(skipUrl)) {
                    skipUrl = "^" + skipUrl.replaceAll("\\*", ".*") + "$";
                    list.add(skipUrl);
                }
            }
            SKIP_URLS = list.toArray(SKIP_URLS);
        }
    }


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String url = httpRequest.getRequestURI().toString();
        String contextPath = httpRequest.getContextPath();
        url = url.substring(contextPath.length());
        Logger.info(contextPath + ", " + url);

        if (SKIP_URLS != null) {
            for (String skipUrl : SKIP_URLS) {
                Pattern pattern = Pattern.compile(skipUrl, Pattern.DOTALL);
                Matcher matcher = pattern.matcher(url);
                if (matcher.find()) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        UserInfo user = (UserInfo) httpRequest.getSession().getAttribute(Constant.SESSION_MEMBER_USER);
        WeChatUser weChatUser = (WeChatUser) httpRequest.getSession().getAttribute(Constant.SESSION_WEIXIN_WXUSER);

        if (null != weChatUser) {
            System.out.println("weChatUser:" + weChatUser.getOpenId());
        }

//        if (null == weChatUser) {
//            WxMpService wxMpService = (WxMpService) BeanUtils.getBean("wxMpService");
//
//            String fullUrl = HttpUtil.getFullUrl(httpRequest, Configue.getBaseDomain());
//            System.out.println("fullUrl:" + fullUrl);
//
//            String OAUTH_URL = wxMpService.oauth2buildAuthorizationUrl(fullUrl, WxConsts.OAUTH2_SCOPE_USER_INFO, Constant.WEIXIN_STATE);
//            System.out.println("domain:" + httpRequest.getSession().getAttributeNames());
//
//            httpResponse.sendRedirect(OAUTH_URL);
//            System.out.println("OAUTH_URL:" + OAUTH_URL);
//            chain.doFilter(request, response);
//            return;
//        }



        if (null != user) {
            //如果该用户为普通会员，则只能访问部分菜单
            if(user.getType().equals(2) || user.getType().equals(0)){
                String [] FORBID_URLS = FORBID_URL_Map.get(user.getType());
                for (String forbinUrl : FORBID_URLS) {
                    Pattern pattern = Pattern.compile(forbinUrl, Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(url);
                    if (matcher.find()) {
                        httpResponse.sendRedirect(contextPath + "/wechat/error?errorMsg=您的权限无法访问");
                    }else{
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }
            else{
                chain.doFilter(request, response);
            }
            return;
        }

        String xRequested = httpRequest.getHeader("X-Requested-With");
        if (xRequested != null && xRequested.indexOf("XMLHttpRequest") != -1) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            WebUtil.print(httpResponse, "重新登录！");
        } else {
            httpResponse.sendRedirect(contextPath + "/wechat/login");
        }

    }

    @Override
    public void destroy() {

    }

}
