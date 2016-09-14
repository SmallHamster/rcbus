package com.leoman.coupon.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.image.entity.Image;

import javax.persistence.*;

/**
 *
 * 系统配置
 * Created by Daisy on 2016/9/14.
 */
@Entity
@Table(name = "t_system_config")
public class SystemConfig extends BaseEntity{

    @Column(name = "type")
    private Integer type;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
