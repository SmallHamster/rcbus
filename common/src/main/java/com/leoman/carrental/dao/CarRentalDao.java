package com.leoman.carrental.dao;

import com.leoman.carrental.entity.CarRental;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface CarRentalDao extends IBaseJpaRepository<CarRental>{

    @Query("SELECT b FROM CarRental b WHERE b.order.id IN (SELECT a.id FROM Order a WHERE a.userInfo.id =?1)")
    public List<CarRental> findList(Long id);

}
