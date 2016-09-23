package com.leoman.user.service;

import com.leoman.common.service.GenericManager;
import com.leoman.user.entity.ReceiveCoupon;

/**
 * Created by Administrator on 2016/9/23.
 */
public interface ReceiveCouponService extends GenericManager<ReceiveCoupon>{

    public ReceiveCoupon findOne(String mobile,Long rentalId);

}
