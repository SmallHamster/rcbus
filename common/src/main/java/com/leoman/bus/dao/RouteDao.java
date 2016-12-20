package com.leoman.bus.dao;

import com.leoman.bus.entity.Route;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 路线
 * Created by Daisy on 2016/9/7.
 */
public interface RouteDao extends IBaseJpaRepository<Route> {

    @Query("select a from Route a where a.lineName = ?1")
    public List<Route> findByLineName(String lineName);

    @Query("select a from Route a where a.lineName = ?1 and a.id != ?2")
    public Route findByLineNameAndId(String lineName, Long id);

}
