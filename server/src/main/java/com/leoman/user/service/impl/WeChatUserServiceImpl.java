package com.leoman.user.service.impl;


import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.entity.Constant;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.user.dao.WeChatUserDao;
import com.leoman.user.entity.WeChatUser;
import com.leoman.user.service.WeChatUserService;
import com.leoman.utils.ClassUtil;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangbin on 2015/7/8.
 */
@Service
public class WeChatUserServiceImpl extends GenericManagerImpl<WeChatUser,WeChatUserDao> implements WeChatUserService {

    @Autowired
    private WeChatUserDao weChatUserDao;

    @Autowired
    private WxMpService wxMpService;

    @Override
    @Transactional
    public WeChatUser create(WxMpUser wxMpUser) {

        WeChatUser wxUser = weChatUserDao.findByOpenId(wxMpUser.getOpenId());
        if (wxUser != null) {
            return wxUser;
        } else {
            wxUser = new WeChatUser();
            ClassUtil.copyProperties(wxUser, wxMpUser);
            return save(wxUser);
        }

    }

    @Override
    public WeChatUser findByOpenId(String openid) {
        return weChatUserDao.findByOpenId(openid);
    }

    @Override
    @Transactional
    public WeChatUser getWxUserByToken(WxMpOAuth2AccessToken wxMpOAuth2AccessToken) {
        WeChatUser wxUser = weChatUserDao.findByOpenId(wxMpOAuth2AccessToken.getOpenId());
        if (wxUser != null) {
            try {
                WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
                ClassUtil.copyProperties(wxUser, wxMpUser);
                return update(wxUser);
            } catch (Exception e) {
                GeneralExceptionHandler.log(e);
                return null;
            }
        } else {
            try {
                wxUser = new WeChatUser();
                WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
                ClassUtil.copyProperties(wxUser, wxMpUser);
                return save(wxUser);
            } catch (Exception e) {
                GeneralExceptionHandler.log(e);
                return null;
            }

        }
    }

    @Override
    public WeChatUser getWXUserByRequest(HttpServletRequest request) {
        WeChatUser weChatUser = (WeChatUser) request.getSession().getAttribute(Constant.SESSION_WEIXIN_WXUSER);
        return weChatUser;
    }
}