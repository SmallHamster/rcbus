package com.leoman.bus.service;


import com.leoman.bus.entity.Bus;
import com.leoman.common.core.Result;
import com.leoman.common.service.GenericManager;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 班车
 * Created by Daisy on 2016/9/6.
 */
public interface BusService extends GenericManager<Bus> {

    public Bus findByUuid(String uuid);

    public List<Bus> findBusOrderByDistance(Long routeId, Double userLat, Double userLng);

    public Result saveBus(Bus bus);

    public List<Bus> findByCarNo(String carNo);

}
