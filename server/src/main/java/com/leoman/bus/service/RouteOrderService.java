package com.leoman.bus.service;


import com.leoman.bus.entity.RouteOrder;
import com.leoman.common.service.GenericManager;

import java.util.List;

/**
 * 路线订单
 * Created by Daisy on 2016/9/13.
 */
public interface RouteOrderService extends GenericManager<RouteOrder> {

    public List<RouteOrder> findList(Long id);

    public Integer findOrderNum(Long routeId, String departTime);

}
