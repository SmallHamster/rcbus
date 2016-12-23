package com.leoman.handers;

import com.leoman.entity.Constant;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.outxmlbuilder.NewsBuilder;

import java.util.List;
import java.util.Map;

/**
 * Created by wangbin on 2015/8/6.
 */
public class EventMessageHandler implements WxMpMessageHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        if (WxConsts.EVT_SUBSCRIBE.equals(wxMessage.getEvent())) {
            return WxMpXmlOutMessage.TEXT().content(Constant.EVENT_DEF_SUBSCRIBE_TEXT).fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
        }
//        if (WxConsts.EVT_CLICK.equals(wxMessage.getEvent())) {
//            // 活动资讯
//            if (Constant.EVENT_ACTIVITY_LIST.equals(wxMessage.getEventKey())) {
//                // 获取活动资讯列表
//                InfomationService infomationService = (InfomationService) BeanUtil.getBean("informationServiceImpl");
//                List<Information> list = infomationService.findList(1, 10).getContent();
//
//                if (null == list || list.size() == 0) {
//                    return WxMpXmlOutMessage.TEXT().content("小编正在努力加班中......").fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
//                }
//
//                NewsBuilder news = WxMpXmlOutMessage.NEWS();
//
//                for (Information info : list) {
//                    WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
//                    item.setUrl(Configue.getBaseUrl() + "weixin/information/detail?id=" + info.getId());
//                    item.setPicUrl(Configue.getUploadUrl() + info.getImage().getPath());
//                    item.setDescription(info.getIntroduction());
//                    item.setTitle(info.getTitle());
//                    news.addArticle(item);
//                }
//                WxMpXmlOutNewsMessage wxMpXmlOutNewsMessage = null;
//                try {
//                    wxMpXmlOutNewsMessage = news.fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                return wxMpXmlOutNewsMessage;
//            }
//            // 限时抢购
//            else if (Constant.EVENT_PRODUCT_LIST.equals(wxMessage.getEventKey())) {
//                // 获取限时抢购列表
//                List<Product> list = null;
//                try {
//                    com.leoman.service.ProductService productService = (com.leoman.service.ProductService) BeanUtil.getBean("productServiceImpl");
//                    list = productService.findList(1, 10).getContent();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                if (null != list && list.size() > 0) {
//                    NewsBuilder news = WxMpXmlOutMessage.NEWS();
//                    for (Product product : list) {
//                        WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
//                        item.setUrl(Configue.getBaseUrl() + "weixin/product/detail?id=" + product.getId());
//                        item.setPicUrl(Configue.getUploadUrl() + product.getCoverImage().getPath());
//                        item.setDescription(product.getDescription());
//                        item.setTitle(product.getTitle());
//                        news.addArticle(item);
//                    }
//                    return news.fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
//                } else {
//                    return WxMpXmlOutMessage.TEXT().content("此次抢购已结束，期待下次抢购").fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
//                }
//            }
//        }
        return null;
    }
}
