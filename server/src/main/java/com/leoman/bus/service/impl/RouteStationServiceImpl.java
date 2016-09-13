package com.leoman.bus.service.impl;

import com.leoman.bus.dao.RouteStationDao;
import com.leoman.bus.entity.RouteStation;
import com.leoman.bus.service.RouteStationService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 班车站点
 * Created by Daisy on 2016/9/8.
 */
@Service
public class RouteStationServiceImpl extends GenericManagerImpl<RouteStation,RouteStationDao> implements RouteStationService{

    @Autowired
    private RouteStationDao routeStationDao;

    @Override
    public List<RouteStation> findByRouteId(Long routeId) {
        return routeStationDao.findByRouteId(routeId);
    }
}
