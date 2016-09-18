package com.leoman.user.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.user.dao.UserCouponDao;
import com.leoman.user.entity.UserCoupon;
import com.leoman.user.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by 史龙 on 2016/9/14.
 */
@Service
public class UserCouponServiceImpl extends GenericManagerImpl<UserCoupon,UserCouponDao> implements UserCouponService{

    @Autowired
    private UserCouponDao userCouponDao;

}
