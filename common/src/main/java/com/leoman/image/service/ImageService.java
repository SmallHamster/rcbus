package com.leoman.image.service;


import com.leoman.image.entity.Image;

/**
 * Created by wangbin on 2014/12/9.
 */
public interface ImageService {

    public Image getById(Integer id);

    public Image deleteById(Integer id);

    public Image create(Image image);
}
