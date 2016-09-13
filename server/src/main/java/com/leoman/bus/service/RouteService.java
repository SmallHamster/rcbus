package com.leoman.bus.service;


import com.leoman.bus.entity.Route;
import com.leoman.common.service.GenericManager;
import com.leoman.user.entity.UserInfo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * 路线
 * Created by Daisy on 2016/9/7.
 */
public interface RouteService extends GenericManager<Route> {

    public Page<Route> page(Integer pageNum, Integer pageSize);

    public void saveRoute(Route route, String departTimes, String backTimes, String busIds, Integer isRoundTrip,List<Map> list);

    public void saveOrder(Long routeId, String departTime, UserInfo user);

    public void deleteRoute(Long routeId);

}
