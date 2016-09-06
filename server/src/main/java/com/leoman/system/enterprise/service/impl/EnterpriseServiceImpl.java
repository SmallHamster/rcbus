package com.leoman.system.enterprise.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.system.enterprise.dao.EnterpriseDao;
import com.leoman.system.enterprise.entity.Enterprise;
import com.leoman.system.enterprise.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/9/6.
 */
@Service
public class EnterpriseServiceImpl extends GenericManagerImpl<Enterprise,EnterpriseDao> implements EnterpriseService{

    @Autowired
    private EnterpriseDao enterpriseDao;

}
