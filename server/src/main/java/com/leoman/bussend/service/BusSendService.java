package com.leoman.bussend.service;

import com.leoman.bussend.entity.BusSend;
import com.leoman.common.service.GenericManager;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface BusSendService extends GenericManager<BusSend> {

    public List<BusSend> findBus(Long id,Integer type);

    public List<Long> findIds(String carNo,String driverName);

}
