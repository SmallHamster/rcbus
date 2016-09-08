package com.leoman.bus.service;


import com.leoman.bus.entity.Route;
import com.leoman.common.service.GenericManager;
import org.springframework.data.domain.Page;

/**
 * 路线
 * Created by Daisy on 2016/9/7.
 */
public interface RouteService extends GenericManager<Route> {

    public Page<Route> page(Integer pageNum, Integer pageSize);

    public void saveRoute(Route route, String departTimes, String backTimes, String busIds);

}
