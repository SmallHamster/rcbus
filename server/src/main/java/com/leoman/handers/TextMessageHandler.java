package com.leoman.handers;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import java.util.Map;

/**
 * 文本消息处理器
 * Created by wangbin on 2015/6/24.
 */
public class TextMessageHandler implements WxMpMessageHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        String content =  wxMessage.getContent();
        System.out.println("TextMessageHandler : "+content);
        System.out.println("text message xml : "+WxMpXmlOutMessage.TEXT().content(content).fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build().toXml());
        return  WxMpXmlOutMessage.TEXT().content("您有什么问题，请联系江城巴士的客服人员，谢谢").fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
    }



}