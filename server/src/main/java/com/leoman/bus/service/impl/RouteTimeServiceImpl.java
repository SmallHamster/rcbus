package com.leoman.bus.service.impl;

import com.leoman.bus.dao.RouteTimeDao;
import com.leoman.bus.entity.RouteTime;
import com.leoman.bus.service.RouteTimeService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/9/8.
 */
@Service
public class RouteTimeServiceImpl extends GenericManagerImpl<RouteTime,RouteTimeDao> implements RouteTimeService{

    @Autowired
    private RouteTimeDao routeTimeDao;

}
