package com.leoman.image.dao;

import com.leoman.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by 涂奕恒 on 2014/12/4 16:16.
 */
public interface ImageDao extends JpaRepository<Image, Integer> {

    @Query("select a from Image a where a.id = ?1")
    public Image findOneInfo(Integer id);
}