package com.leoman.bus.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.image.entity.Image;

import javax.persistence.*;

/**
 *
 * 用车类型实体类
 * Created by Daisy on 2016/9/6.
 */
@Entity
@Table(name = "t_car_type")
public class CarType extends BaseEntity{

    @Column(name = "name")
    private String name;//类型名称

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;//图片

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
