package com.leoman.carrental.service.impl;

import com.leoman.carrental.dao.CarTravelDao;
import com.leoman.carrental.entity.CarTravel;
import com.leoman.carrental.service.CarTravelService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Administrator on 2016/9/12.
 */
@Service
public class CarTravelServiceImpl extends GenericManagerImpl<CarTravel,CarTravelDao> implements CarTravelService {

    @Autowired
    private CarTravelDao carTravelDao;

}
