package com.leoman.wechat.pay.controller;

import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.common.controller.common.CommonController;
import com.leoman.entity.Configue;
import com.leoman.order.entity.Order;
import com.leoman.order.service.OrderService;
import com.leoman.user.entity.UserCoupon;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.WeChatUser;
import com.leoman.user.service.UserCouponService;
import com.leoman.user.service.WeChatUserService;
import com.leoman.utils.WebUtil;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpPayCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by wangbin on 2015/10/8.
 */
@Controller
@RequestMapping(value = "wechat/pay")
public class WeChatPayController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WeChatUserService weChatUserService;

    @Autowired
    private CarRentalService carRentalService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserCouponService userCouponService;

//
//    @Autowired
//    private WxUserService wxUserService;
//
//    @Autowired
//    private KUserService kUserService;
//
//    @Autowired
//    private PayrecordService payrecordService;
//
//    @Autowired
//    private CoinlogService coinlogService;
//
//    @Autowired
//    private WebPayrecordService webPayrecordService;
//
//    @Autowired
//    private SalamanRecordService salamanRecordService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request,
                        HttpServletResponse response) {
        return "weixin/支付测试";
    }

    @RequestMapping(value = "go")
    public void go(HttpServletRequest request,
                   HttpServletResponse response,
                   String orderId) {
        WeChatUser wxUser = weChatUserService.getWXUserByRequest(request);
        Map<String, String> result = wxMpService.getJSSDKPayInfo(wxUser.getOpenId(), orderId, 0.01, "xxx", "JSAPI",
                request.getRemoteAddr(), Configue.getBaseUrl() + "weixin/pay/callback");
        WebUtil.printJson(response, result);
    }

    @RequestMapping(value = "goPay")
    public void goPay(HttpServletRequest request,
                      HttpServletResponse response,
                      Long rentalId) {
        System.out.println("rentalId：" + rentalId);
        CarRental carRental = carRentalService.queryByPK(rentalId);
        System.out.println("order：" + carRental.getOrder().getOrderNo());
        //订单号
        String orderNo = carRental.getOrder().getOrderNo();
        //应付金额
        Double totalPrice = carRental.getIncome();
        WeChatUser weChatUser = weChatUserService.getWXUserByRequest(request);
        Map<String, String> result = wxMpService.getJSSDKPayInfo(weChatUser.getOpenId(), orderNo, 0.01, "租车出行", "JSAPI",
                request.getRemoteAddr(), Configue.getBaseUrl() + "weixin/pay/callback");

        WebUtil.printJson(response, result);
    }

    @RequestMapping(value = "callback")
    public void callBack(HttpServletRequest request,
                         HttpServletResponse response) {
        try {

            //把如下代码贴到的你的处理回调的servlet 或者.do 中即可明白回调操作
            System.out.print("微信支付回调数据开始");
            String inputLine;
            String notityXml = "";
            try {
                while ((inputLine = request.getReader().readLine()) != null) {
                    notityXml += inputLine;
                }
                request.getReader().close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("接收到的报文：" + notityXml);

            WxMpPayCallback wxMpPayCallback = wxMpService.getJSSDKCallbackData(notityXml);

            //判断支付成功，更改订单状态
            if ("SUCCESS".equals(wxMpPayCallback.getReturn_code())) {
                String orderNo = wxMpPayCallback.getOut_trade_no();

                //改变状态进行中
                Order order = orderService.findOne(orderNo);
                order.setStatus(2);
                orderService.save(order);

                //获取使用的优惠券
                Long couponId = (Long)request.getSession().getAttribute("couponId");
                CarRental carRental = carRentalService.findOne(order.getId());
                UserInfo userInfo = new CommonController().getSessionUser(request);

                //改变优惠券状态为已使用
                if(couponId!=null){
                    List<UserCoupon> userCoupons = userCouponService.findList(userInfo.getId(),couponId);
                    if(!userCoupons.isEmpty() && userCoupons.size()>0){
                        UserCoupon userCoupon = userCoupons.get(0);
                        userCoupon.setIsUse(2);
                        userCoupon.setRentalId(carRental.getId());
                        userCouponService.save(userCoupon);
                    }
                }
            }

            System.out.println("----------");
            System.out.println(wxMpPayCallback);
            String xmlResult = "<xml>" +
                    "<return_code><![CDATA[SUCCESS]]></return_code>" +
                    "<return_msg><![CDATA[OK]]></return_msg>" +
                    "</xml>";
            WebUtil.print(response, xmlResult);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
