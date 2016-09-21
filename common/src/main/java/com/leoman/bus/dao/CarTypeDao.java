package com.leoman.bus.dao;

import com.leoman.bus.entity.CarType;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 用车类型
 * Created by Daisy on 2016/9/8.
 */
public interface CarTypeDao extends IBaseJpaRepository<CarType>{

    @Query("SELECT a FROM CarType a WHERE a.id <> '1'")
    public List<CarType> findRentalType();

}
