package com.leoman.city.service.impl;

import com.leoman.city.dao.CityDao;
import com.leoman.city.entity.City;
import com.leoman.city.service.CityService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
@Service
public class CityServiceImpl  extends GenericManagerImpl<City, CityDao> implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    public List<City> findById(Long provinceId) {
        return cityDao.findById(provinceId);
    }
}
