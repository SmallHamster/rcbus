package com.leoman.system.enterprise.service;

import com.leoman.common.core.Result;
import com.leoman.common.service.GenericManager;
import com.leoman.system.enterprise.entity.Enterprise;

/**
 * Created by Administrator on 2016/9/6.
 */
public interface EnterpriseService extends GenericManager<Enterprise> {

    public void save(Long id, String name, String userName,Integer type);

}
