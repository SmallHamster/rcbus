package com.leoman.carrental.service.impl;

import com.leoman.carrental.dao.CommutingDao;
import com.leoman.carrental.entity.Commuting;
import com.leoman.carrental.service.CommutingService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 个人通勤ServiceImpl
 * Created by 史龙 on 2016/9/12.
 */
@Service
public class CommutingServiceImpl extends GenericManagerImpl<Commuting,CommutingDao> implements CommutingService {

    @Autowired
    private CommutingDao commutingDao;


}
