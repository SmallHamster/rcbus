package com.leoman.wechat.coupon.controller;

import com.leoman.common.controller.common.CommonController;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.coupon.entity.Coupon;
import com.leoman.coupon.service.CouponService;
import com.leoman.coupon.service.impl.CouponServiceImpl;
import com.leoman.user.entity.*;
import com.leoman.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信礼券设置
 * Created by 史龙 on 2016/9/23.
 */
@Controller
@RequestMapping(value = "/wechat/coupon")
public class CouponWeChatController extends GenericEntityController<Coupon,Coupon,CouponServiceImpl>{

    @Autowired
    private UserService userService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private NotUserCouponService notUserCouponService;
    @Autowired
    private ReceiveCouponService receiveCouponService;
    @Autowired
    private CouponOrderService couponOrderService;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model){
        List<UserCoupon> couponWay1 = new ArrayList<>();
        List<UserCoupon> couponWay2 = new ArrayList<>();
        UserInfo userInfo = new CommonController().getSessionUser(request);
        List<UserCoupon> userCoupons = userCouponService.findList(userInfo.getId());
        if(!userCoupons.isEmpty() && userCoupons.size()>0){
            for(UserCoupon userCoupon : userCoupons){
                //有效期
                if(userCoupon.getCoupon().getValidDateFrom()<System.currentTimeMillis() && userCoupon.getCoupon().getValidDateTo()>System.currentTimeMillis()){
                    //折扣
                    if(userCoupon.getCoupon().getCouponWay()==1){
                        couponWay1.add(userCoupon);
                    }
                    //减免金额
                    if(userCoupon.getCoupon().getCouponWay()==2){
                        couponWay2.add(userCoupon);
                    }
                }
            }
        }
        model.addAttribute("couponWay1",couponWay1);
        model.addAttribute("couponWay2",couponWay2);
        return "wechat/my_coupon";
    }

    /**
     * 转赠页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/giving")
    public String giving(Long id,Model model){
        model.addAttribute("userCoupon",userCouponService.queryByPK(id));
        return "wechat/giving_coupon";
    }

    /**
     * 转赠
     * @param id
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/givingSave")
    @ResponseBody
    public Integer givingSave(Long id,String mobile){
        UserInfo userInfo = userService.findByMobile(mobile.trim());
            if(userInfo==null){
                return 2;
            }
        try{
            UserCoupon userCoupon = userCouponService.queryByPK(id);
            userCoupon.setUserId(userInfo.getId());
            userCouponService.save(userCoupon);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }


    /**
     * 领取页面
     * @param rentalId
     * @param model
     * @return
     */
    @RequestMapping(value = "/receive")
    public String receive(Long rentalId, Model model){
        model.addAttribute("rentalId",rentalId);
        return "wechat/receive_coupon";
    }


    /**
     * 领取
     * @param mobile
     * @param rentalId
     * @return
     */
    @RequestMapping(value = "/receiveSave")
    @ResponseBody
    public Integer receiveSave(String mobile,Long rentalId){
        Integer index;
        try{
            index = couponService.receiveSave(mobile,rentalId);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return index;

    }


}
