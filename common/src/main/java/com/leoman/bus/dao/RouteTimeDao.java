package com.leoman.bus.dao;

import com.leoman.bus.entity.RouteTime;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 路线时间
 * Created by Daisy on 2016/9/8.
 */
public interface RouteTimeDao extends IBaseJpaRepository<RouteTime> {

    @Query("select a from RouteTime a where a.routeId = ?1")
    public List<RouteTime> findByRouteId(Long routeId);

}
