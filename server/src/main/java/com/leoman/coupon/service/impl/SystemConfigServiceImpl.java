package com.leoman.coupon.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.coupon.dao.SystemConfigDao;
import com.leoman.coupon.entity.SystemConfig;
import com.leoman.coupon.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 优惠券图片
 * Created by Daisy on 2016/9/14.
 */
@Service
public class SystemConfigServiceImpl extends GenericManagerImpl<SystemConfig,SystemConfigDao> implements SystemConfigService{

    @Autowired
    private SystemConfigDao systemConfigDao;

}
