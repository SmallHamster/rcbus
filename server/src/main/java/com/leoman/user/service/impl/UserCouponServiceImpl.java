package com.leoman.user.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.user.dao.UserCouponDao;
import com.leoman.user.entity.UserCoupon;
import com.leoman.user.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by 史龙 on 2016/9/14.
 */
@Service
public class UserCouponServiceImpl extends GenericManagerImpl<UserCoupon,UserCouponDao> implements UserCouponService{

    @Autowired
    private UserCouponDao userCouponDao;

    @Override
    public List<UserCoupon> findList(Long userId, Long couponId) {

        return userCouponDao.findList(userId,couponId);
    }

    @Override
    public List<UserCoupon> findList(Long userId) {
        return userCouponDao.findList(userId);
    }

    @Override
    public UserCoupon findOne(Long userId, Long orderId) {
        return userCouponDao.findOne(userId,orderId);
    }
}
