package com.leoman.bus.service;


import com.leoman.bus.entity.Bus;
import com.leoman.common.service.GenericManager;
import org.springframework.data.domain.Page;

/**
 * 班车
 * Created by Daisy on 2016/9/6.
 */
public interface BusService extends GenericManager<Bus> {

    public Page<Bus> page(Integer pageNum, Integer pageSize);

}
