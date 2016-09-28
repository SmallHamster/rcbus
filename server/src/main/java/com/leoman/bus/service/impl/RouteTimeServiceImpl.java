package com.leoman.bus.service.impl;

import com.leoman.bus.dao.RouteTimeDao;
import com.leoman.bus.entity.RouteTime;
import com.leoman.bus.service.RouteTimeService;
import com.leoman.common.service.GenericManager;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 路线时间
 * Created by Daisy on 2016/9/8.
 */
@Service
public class RouteTimeServiceImpl extends GenericManagerImpl<RouteTime,RouteTimeDao> implements RouteTimeService{

    @Autowired
    private RouteTimeDao routeTimeDao;

    @Override
    public List<RouteTime> findByRouteId(Long routeId) {
        return routeTimeDao.findByRouteId(routeId);
    }

    /**
     * 获取大于当前时间的时间点列表
     * @param routeId
     * @return
     */
    @Override
    public List<RouteTime> findByCurrentTime(Long routeId){
        String sql = "SELECT \n" +
                "  * \n" +
                "FROM\n" +
                "  t_route_time t \n" +
                "WHERE t.`route_id` = "+routeId+" \n" +
                "  AND t.`depart_time` > DATE_FORMAT(NOW(), '%H:%i')";
        List<RouteTime> timeList = queryBySql(sql,RouteTime.class);
        return timeList;
    }
}
