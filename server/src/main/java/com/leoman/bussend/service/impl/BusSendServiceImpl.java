package com.leoman.bussend.service.impl;

import com.leoman.bussend.dao.BusSendDao;
import com.leoman.bussend.entity.BusSend;
import com.leoman.bussend.service.BusSendService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
@Service
public class BusSendServiceImpl extends GenericManagerImpl<BusSend,BusSendDao> implements BusSendService{

    @Autowired
    private BusSendDao busSendDao;


    @Override
    public List<BusSend> findBus(Long id,Integer type) {
        return busSendDao.findBus(id,type);
    }
}
