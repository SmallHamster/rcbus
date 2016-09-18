package com.leoman.bus.service;

import com.leoman.bus.entity.CarType;
import com.leoman.common.service.GenericManager;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8.
 */
public interface CarTypeService extends GenericManager<CarType>{

    public List<CarType> findRentalType();

}
