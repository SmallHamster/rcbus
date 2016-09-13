package com.leoman.bus.service;

import com.leoman.bus.entity.RouteStation;
import com.leoman.common.service.GenericManager;

import java.util.List;

/**
 * Created by Daisy on 2016/9/13.
 */
public interface RouteStationService extends GenericManager<RouteStation>{

    public List<RouteStation> findByRouteId(Long routeId);
}
