package com.leoman.bus.service.impl;


import com.leoman.bus.dao.EnterpriseApplyDao;
import com.leoman.bus.entity.EnterpriseApply;
import com.leoman.bus.service.EnterpriseApplyService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业报名信息
 * Created by Daisy on 2016/9/14.
 */
@Service
public class EnterpriseApplyServiceImpl extends GenericManagerImpl<EnterpriseApply, EnterpriseApplyDao> implements EnterpriseApplyService {

    @Autowired
    private EnterpriseApplyDao enterpriseApplyDao;

}
