package com.leoman.common.log.service.impl;

import com.leoman.common.log.dao.LogDao;
import com.leoman.common.log.entity.LogEntity;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/5/22.
 */
@Service
public class LogServiceImpl extends GenericManagerImpl<LogEntity,LogDao>{

    @Autowired
    private LogDao dao;
}
