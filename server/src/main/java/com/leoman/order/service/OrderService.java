package com.leoman.order.service;

import com.leoman.common.service.GenericManager;
import com.leoman.order.entity.Order;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface OrderService extends GenericManager<Order>{

    public Order findOne(String orderNo);

}
