package com.leoman.bus.service.impl;

import com.leoman.bus.dao.CarTypeDao;
import com.leoman.bus.entity.CarType;
import com.leoman.bus.service.CarTypeService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8.
 */
@Service
public class CarTypeServiceImpl extends GenericManagerImpl<CarType,CarTypeDao> implements CarTypeService{

    @Autowired
    private CarTypeDao carTypeDao;

    @Override
    public List<CarType> findRentalType() {
        return carTypeDao.findRentalType();
    }
}
