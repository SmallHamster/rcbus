package com.leoman.coupon.service.impl;

import com.leoman.common.core.ErrorType;
import com.leoman.common.core.Result;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.coupon.dao.CouponDao;
import com.leoman.coupon.entity.Coupon;
import com.leoman.coupon.service.CouponService;
import com.leoman.user.entity.*;
import com.leoman.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 优惠券
 * Created by Daisy on 2016/9/13.
 */
@Service
public class CouponServiceImpl extends GenericManagerImpl<Coupon,CouponDao> implements CouponService{

    @Autowired
    private CouponDao couponDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private NotUserCouponService notUserCouponService;
    @Autowired
    private ReceiveCouponService receiveCouponService;
    @Autowired
    private CouponOrderService couponOrderService;

    @Override
    @Transactional
    public Integer receiveSave(String mobile, Long rentalId) {
        Coupon _c = new Coupon();
        if(rentalId==null){
            return 0;
        }
        List<Coupon> coupons =  queryAll();
        for(Coupon coupon : coupons){
            //1-好友分享
            if(coupon.getGainWay()==1){
                _c = coupon;
                break;
            }
        }

        UserInfo userInfo = userService.findByMobile(mobile);
        List<ReceiveCoupon> receiveCoupons = receiveCouponService.queryByProperty("rentalId",rentalId);
        if(receiveCoupons.size()>10){
            //超出领取上限
            return 2;
        }

        ReceiveCoupon receiveCoupon = receiveCouponService.findOne(mobile,rentalId);
        if(receiveCoupon!=null){
            //领取过了
            return 3;
        }else {
            receiveCoupon = new ReceiveCoupon();
        }

        if(userInfo!=null){
            //用户存在
            //新增一条用户优惠券
            UserCoupon userCoupon = new UserCoupon();
            CouponOrder couponOrder = new CouponOrder();

            //快照
            couponOrder.setName(_c.getName());
            couponOrder.setGainWay(_c.getGainWay());
            couponOrder.setCouponWay(_c.getCouponWay());
            couponOrder.setValidDateFrom(_c.getValidDateFrom());
            couponOrder.setValidDateTo(_c.getValidDateTo());
            couponOrder.setDiscountPercent(_c.getDiscountPercent());
            couponOrder.setDiscountTopMoney(_c.getDiscountTopMoney());
            couponOrder.setReduceMoney(_c.getReduceMoney());
            couponOrder.setIsLimit(_c.getIsLimit());
            couponOrder.setLimitMoney(_c.getLimitMoney());
            couponOrderService.save(couponOrder);

            userCoupon.setUserId(userInfo.getId());
            userCoupon.setCoupon(couponOrder);
            userCoupon.setIsUse(1);
            userCouponService.save(userCoupon);
            //新增一条领取信息
            receiveCoupon.setMobile(userInfo.getMobile());
            receiveCoupon.setRentalId(rentalId);
            receiveCouponService.save(receiveCoupon);
        }else {
            //用户不存在
            //新增一条非用户优惠券
            NotUserCoupon notUserCoupon = new NotUserCoupon();
            CouponOrder couponOrder = new CouponOrder();

            //快照
            couponOrder.setName(_c.getName());
            couponOrder.setGainWay(_c.getGainWay());
            couponOrder.setCouponWay(_c.getCouponWay());
            couponOrder.setValidDateFrom(_c.getValidDateFrom());
            couponOrder.setValidDateTo(_c.getValidDateTo());
            couponOrder.setDiscountPercent(_c.getDiscountPercent());
            couponOrder.setDiscountTopMoney(_c.getDiscountTopMoney());
            couponOrder.setReduceMoney(_c.getReduceMoney());
            couponOrder.setIsLimit(_c.getIsLimit());
            couponOrder.setLimitMoney(_c.getLimitMoney());
            couponOrderService.save(couponOrder);

            notUserCoupon.setMobile(mobile);
            notUserCoupon.setCouponId(couponOrder.getId());
            notUserCouponService.save(notUserCoupon);
            //新增一条领取信息
            receiveCoupon.setMobile(mobile);
            receiveCoupon.setRentalId(rentalId);
            receiveCouponService.save(receiveCoupon);
        }

        return 1;
    }

}
