package com.leoman.bus.service;


import com.leoman.bus.entity.RouteTime;
import com.leoman.common.service.GenericManager;

import java.util.List;

/**
 * 路线
 * Created by Daisy on 2016/9/12.
 */
public interface RouteTimeService extends GenericManager<RouteTime> {

    public List<RouteTime> findByRouteId(Long routeId);

    public List<RouteTime> findByCurrentTime(Long routeId);

}
