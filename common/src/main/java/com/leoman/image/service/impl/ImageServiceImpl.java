package com.leoman.image.service.impl;

import com.leoman.image.dao.ImageDao;
import com.leoman.image.entity.Image;
import com.leoman.image.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by wangbin on 14-10-16.
 */
@Service
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    public Image getById(Integer id) {
        return imageDao.findOne(id);
    }

    @Override
    @Transactional
    public Image deleteById(Integer id) {
        Image image = getById(id);
        imageDao.delete(image);
        return image;
    }

    @Override
    @Transactional
    public Image create(Image image) {
        image.setCreateDate(new Date());
        return imageDao.save(image);
    }
}
