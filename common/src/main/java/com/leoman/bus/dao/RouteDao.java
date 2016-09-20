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

    @Query("select a from Route a where a.enterprise.type = ?1 and a.startStation= ?2 and a.endStation = ?3")
    public List<Route> findByEnterpriseType(Integer type, String startStation, String endStation);

}
