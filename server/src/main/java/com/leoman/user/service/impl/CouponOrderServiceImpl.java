package com.leoman.user.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.user.dao.CouponOrderDao;
import com.leoman.user.entity.CouponOrder;
import com.leoman.user.service.CouponOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/10/8.
 */
@Service
public class CouponOrderServiceImpl extends GenericManagerImpl<CouponOrder,CouponOrderDao> implements CouponOrderService{

    @Autowired
    private CouponOrderDao couponOrderDao;

}
