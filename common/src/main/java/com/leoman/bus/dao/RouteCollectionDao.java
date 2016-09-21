package com.leoman.bus.dao;

import com.leoman.bus.entity.Route;
import com.leoman.bus.entity.RouteCollection;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 路线收藏
 * Created by Daisy on 2016/9/21.
 */
public interface RouteCollectionDao extends IBaseJpaRepository<RouteCollection>{

    @Query("SELECT a.route FROM RouteCollection a WHERE a.userId = ?1")
    public List<Route> findByUser(Long userId);

    @Query("select a from RouteCollection a where a.route.id = ?1 and a.userId = ?2")
    public RouteCollection findOne(Long routeId, Long userId);

}
