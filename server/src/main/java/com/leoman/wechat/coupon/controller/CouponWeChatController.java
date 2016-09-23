package com.leoman.wechat.coupon.controller;

import com.leoman.common.controller.common.CommonController;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.coupon.entity.Coupon;
import com.leoman.coupon.service.CouponService;
import com.leoman.coupon.service.impl.CouponServiceImpl;
import com.leoman.user.entity.UserCoupon;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 礼券设置
 * Created by 史龙 on 2016/9/23.
 */
@Controller
@RequestMapping(value = "/wechat/coupon")
public class CouponWeChatController extends GenericEntityController<Coupon,Coupon,CouponServiceImpl>{

    @Autowired
    private CouponService couponService;
    @Autowired
    private UserCouponService userCouponService;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model){
        UserInfo userInfo = new CommonController().getSessionUser(request);
        List<Coupon> coupons = couponService.findList(userInfo.getId());
        List<Coupon> couponWay1 = new ArrayList<>();
        List<Coupon> couponWay2 = new ArrayList<>();
        for(Coupon coupon : coupons){
            //折扣
            if(coupon.getCouponWay()==1){
                couponWay1.add(coupon);
            }
            //减免金额
            if(coupon.getCouponWay()==2){
                couponWay2.add(coupon);
            }
        }
        model.addAttribute("couponWay1",couponWay1);
        model.addAttribute("couponWay2",couponWay2);
        return "wechat/my_coupon";
    }

}
