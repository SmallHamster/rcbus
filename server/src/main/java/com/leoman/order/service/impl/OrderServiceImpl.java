package com.leoman.order.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.order.dao.OrderDao;
import com.leoman.order.entity.Order;
import com.leoman.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/9/7.
 */
@Service
public class OrderServiceImpl extends GenericManagerImpl<Order,OrderDao> implements OrderService{

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order findOne(String orderNo) {
        return orderDao.findOne(orderNo);
    }
}
