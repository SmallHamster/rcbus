package com.leoman.user.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.user.dao.ReceiveCouponDao;
import com.leoman.user.entity.ReceiveCoupon;
import com.leoman.user.service.ReceiveCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Administrator on 2016/9/23.
 */
@Service
public class ReceiveCouponServiceImpl extends GenericManagerImpl<ReceiveCoupon,ReceiveCouponDao> implements ReceiveCouponService{

    @Autowired
    private ReceiveCouponDao receiveCouponDao;

    @Override
    public ReceiveCoupon findOne(String mobile, Long rentalId) {
        return receiveCouponDao.findOne(mobile,rentalId);
    }
}
