package com.leoman.system.banner.service;

import com.leoman.common.service.GenericManager;
import com.leoman.system.banner.entity.Banner;

import java.util.List;

/**
 * BannerService
 * Created by 史龙 on 2016/9/13.
 */
public interface BannerService extends GenericManager<Banner> {

    //最近5条
    public List<Banner> findList(Integer position);

}
