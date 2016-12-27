package com.leoman.handers;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import java.util.Map;

/**
 * 客服文本消息处理器
 * Created by wangbin on 2015/6/24.
 */
public class CustomerMessageHandler implements WxMpMessageHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

        try {
            String content =  wxMessage.getContent();
            System.out.println("CustomerMessageHandler : "+content);

//            Field field = XStreamTransformer.class.getDeclaredField("CLASS_2_XSTREAM_INSTANCE");
//            field.setAccessible(true);
//            Map object = (Map)field.get(null);
//            XStream xstream = XStreamInitializer.getInstance();
//            xstream.processAnnotations(WxMpXmlOutMessage.class);
//            xstream.processAnnotations(WxMpXmlOutTransferCustomerServiceMessage.class);
//            object.put(WxMpXmlOutMessage.class,xstream);
//            System.out.println("WxMpXmlOutMessage:"+object);

            System.out.println("message xml : "+WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build().toXml());
        }catch (Exception e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        }

        return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
    }

}