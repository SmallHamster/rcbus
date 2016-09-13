package com.leoman.bus.service.impl;

import com.leoman.bus.dao.RouteOrderDao;
import com.leoman.bus.dao.RouteTimeDao;
import com.leoman.bus.entity.RouteOrder;
import com.leoman.bus.entity.RouteTime;
import com.leoman.bus.service.RouteOrderService;
import com.leoman.bus.service.RouteTimeService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 路线订单
 * Created by Daisy on 2016/9/13.
 */
@Service
public class RouteOrderServiceImpl extends GenericManagerImpl<RouteOrder,RouteOrderDao> implements RouteOrderService{

    @Autowired
    private RouteOrderDao routeOrderDao;

}
