package com.leoman.carrental.service.impl;

import com.leoman.carrental.dao.CarRentalDao;
import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/9/7.
 */
@Service
public class CarRentalServiceImpl extends GenericManagerImpl<CarRental,CarRentalDao> implements CarRentalService {

    @Autowired
    private CarRentalDao carRentalDao;

}
