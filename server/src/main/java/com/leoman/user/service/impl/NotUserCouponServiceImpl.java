package com.leoman.user.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.user.dao.NotUserCouponDao;
import com.leoman.user.entity.NotUserCoupon;
import com.leoman.user.service.NotUserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by 史龙 on 2016/9/23.
 */
@Service
public class NotUserCouponServiceImpl extends GenericManagerImpl<NotUserCoupon,NotUserCouponDao> implements NotUserCouponService {

    @Autowired
    private NotUserCouponDao notUserCouponDao;


}
