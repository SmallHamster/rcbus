package com.leoman.user.service;

import com.leoman.common.service.GenericManager;
import com.leoman.common.service.ICommonService;
import com.leoman.user.entity.WeChatUser;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangbin on 2015/7/8.
 */
public interface WeChatUserService extends GenericManager<WeChatUser> {

    public WeChatUser create(WxMpUser wxMpUser);

    public WeChatUser findByOpenId(String openid);

    public WeChatUser getWxUserByToken(WxMpOAuth2AccessToken wxMpOAuth2AccessToken);

    public WeChatUser getWXUserByRequest(HttpServletRequest request);

}
