package com.leoman.system.banner.service.Impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.system.banner.dao.BannerDao;
import com.leoman.system.banner.entity.Banner;
import com.leoman.system.banner.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/9/13.
 */
@Service
public class BannerServiceImpl extends GenericManagerImpl<Banner,BannerDao> implements BannerService {

    @Autowired
    private BannerDao bannerDao;

}

