package com.leoman.coupon.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.coupon.dao.CouponDao;
import com.leoman.coupon.entity.Coupon;
import com.leoman.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券
 * Created by Daisy on 2016/9/13.
 */
@Service
public class CouponServiceImpl extends GenericManagerImpl<Coupon,CouponDao> implements CouponService{

    @Autowired
    private CouponDao couponDao;

//    @Override
//    public List<Coupon> findList(Long userId) {
////        return couponDao.findList(System.currentTimeMillis(),userId);
//        return null;
//    }
//
//    @Override
//    public Coupon findOne(Long userId, Long orderId) {
//        return couponDao.findOne(userId,orderId);
//    }
}
