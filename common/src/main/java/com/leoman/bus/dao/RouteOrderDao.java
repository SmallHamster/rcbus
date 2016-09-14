package com.leoman.bus.dao;

import com.leoman.bus.entity.RouteOrder;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 班车订单dao
 * Created by Daisy on 2016/9/13.
 */
public interface RouteOrderDao extends IBaseJpaRepository<RouteOrder>{

    @Query("SELECT b FROM RouteOrder b WHERE b.order.id IN (SELECT a.id FROM Order a WHERE a.userInfo.id =?1)")
    public List<RouteOrder> findList(Long id);

}
