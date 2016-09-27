package com.leoman.order.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.order.entity.Order;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface OrderDao extends IBaseJpaRepository<Order>{

    @Query("SELECT a FROM Order a WHERE a.orderNo = ?1")
    public Order findOne(String orderNo);

}
