package com.leoman.user.service;

import com.leoman.common.service.GenericManager;
import com.leoman.user.entity.UserCoupon;

import java.util.List;

/**
 *
 * Created by 史龙 on 2016/9/14.
 */
public interface UserCouponService extends GenericManager<UserCoupon> {

    public List<UserCoupon> findList(Long userId, Long couponId);

    public List<UserCoupon> findList(Long userId);

    public UserCoupon findOne(Long userId, Long orderId);
}
