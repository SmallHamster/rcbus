package com.leoman.system.banner.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.system.banner.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * BannerDao
 * Created by 史龙 on 2016/9/13.
 */
public interface BannerDao extends IBaseJpaRepository<Banner>{

    //查询最近的5条
    @Query("SELECT a FROM Banner a WHERE a.position = ?1")
    public Page<Banner> findList(Integer position,Pageable pageable);
 }
