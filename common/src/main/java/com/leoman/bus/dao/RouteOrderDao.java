package com.leoman.bus.dao;

import com.leoman.bus.entity.RouteOrder;
import com.leoman.carrental.entity.CarRental;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 班车订单dao
 * Created by Daisy on 2016/9/13.
 */
public interface RouteOrderDao extends IBaseJpaRepository<RouteOrder>{

    @Query("SELECT b FROM RouteOrder b WHERE b.order.id IN (SELECT a.id FROM Order a WHERE a.userInfo.id =?1) AND b.isDel = '0' ")
    public List<RouteOrder> findList(Long id);

    @Query("select count(a.id) from RouteOrder a where a.route.id = ?1 and a.departTime = ?2")
    public Integer findOrderNum(Long routeId, String departTime);

    //根据订单id查询
    @Query("SELECT a FROM RouteOrder a WHERE a.order.id = ?1")
    public RouteOrder findOne(Long orderId);

}
