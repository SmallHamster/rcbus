package com.leoman.system.banner.service.Impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.system.banner.dao.BannerDao;
import com.leoman.system.banner.entity.Banner;
import com.leoman.system.banner.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
@Service
public class BannerServiceImpl extends GenericManagerImpl<Banner,BannerDao> implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    @Override
    public List<Banner> findList(Integer position) {
        Pageable pageable = new PageRequest(0, 5, Sort.Direction.DESC, "id");
        Page<Banner> page = bannerDao.findList(position,pageable);
        List<Banner> banners = page.getContent();
        return banners;
    }
}

