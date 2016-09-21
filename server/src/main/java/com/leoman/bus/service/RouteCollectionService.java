package com.leoman.bus.service;

import com.leoman.bus.entity.Route;
import com.leoman.bus.entity.RouteCollection;
import com.leoman.common.service.GenericManager;

import java.util.List;

/**
 * 路线收藏
 * Created by Daisy on 2016/9/21.
 */
public interface RouteCollectionService extends GenericManager<RouteCollection>{

    public List<Route> findByUser(Long userId);

    public void doCollect(Long routeId, Long userId, Boolean isCollect);

}
