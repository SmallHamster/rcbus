package com.leoman.carrental.dao;

import com.leoman.carrental.entity.CarRental;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface CarRentalDao extends IBaseJpaRepository<CarRental>{

    //未被用户删除的租车订单
    @Query("SELECT b FROM CarRental b WHERE b.order.id IN (SELECT a.id FROM Order a WHERE a.userInfo.id =?1) AND b.isDel = '0'")
    public List<CarRental> findList(Long id);


    //根据订单id查询租单
    @Query("SELECT a FROM CarRental a WHERE a.order.id = ?1")
    public CarRental findOne(Long orderId);


}
