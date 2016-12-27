package com.leoman.common.filter;//package com.leoman.common.filter;

import com.leoman.entity.Constant;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.WeChatUser;
import com.leoman.user.service.UserService;
import com.leoman.user.service.WeChatUserService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by wangbin on 2015/8/4.
 */
public class CustomWeixinInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WeChatUserService weChatUserService;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String code = request.getParameter("code");
            WeChatUser wxUserPlus = (WeChatUser) request.getSession().getAttribute(Constant.SESSION_WEIXIN_WXUSER);

            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::;");
            System.out.println("code:" + code);
            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::;");

            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::;");
            System.out.println("wxUserPlus:" + wxUserPlus);
            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::;");

            if(wxUserPlus!=null && wxUserPlus.getId()!=null){
                System.out.println(":::::::::::::::::::::::::::::::::::::外面:::::::::11111111111111111111111111111111111111");
                System.out.println("id:");
                System.out.println(wxUserPlus.getId());
                UserInfo user = userService.findByWechatId(wxUserPlus.getId());
                if(user!=null){
                    request.getSession().setAttribute(Constant.SESSION_MEMBER_USER, user);
                }
            }

            // if (Constant.WEIXIN_STATE.equals(request.getParameter("state")) && StringUtils.isNotBlank(code)) {
            if (null == wxUserPlus && StringUtils.isNotBlank(code)) {
                System.out.println("---- start get wxuser -----");
                WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

                WeChatUser wxUser = weChatUserService.getWxUserByToken(wxMpOAuth2AccessToken);
                if(wxUser!=null && wxUser.getId()!=null){
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!里面:::::::::::::::::::::::::::::::::;:");
                    System.out.println("id:");
                    System.out.println(wxUser.getId());
                    UserInfo user = userService.findByWechatId(wxUser.getId());
                    if(user!=null){
                        request.getSession().setAttribute(Constant.SESSION_MEMBER_USER, user);
                    }
                }
                System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::;");
                System.out.println("wxUser:" + wxUser);
                System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::;");
                request.getSession().setAttribute(Constant.SESSION_WEIXIN_WXUSER, wxUser);

                return false;
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return true;
    }
}
