package com.leoman.bus.dao;

import com.leoman.bus.entity.RouteStation;
import com.leoman.bus.entity.RouteTime;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 路线站点
 * Created by Daisy on 2016/9/9.
 */
public interface RouteStationDao extends IBaseJpaRepository<RouteStation> {

    @Query("select a from RouteStation a where a.routeId = ?1 order by a.stationOrder")
    public List<RouteStation> findByRouteId(Long routeId);

}
