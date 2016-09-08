package com.leoman.carrental.service.impl;

import com.leoman.carrental.dao.CarRentalOfferDao;
import com.leoman.carrental.entity.CarRentalOffer;
import com.leoman.carrental.service.CarRentalOfferService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/9/8.
 */
@Service
public class CarRentalOfferServiceImpl extends GenericManagerImpl<CarRentalOffer,CarRentalOfferDao> implements CarRentalOfferService {

    @Autowired
    private CarRentalOfferDao carRentalOfferDao;

}
