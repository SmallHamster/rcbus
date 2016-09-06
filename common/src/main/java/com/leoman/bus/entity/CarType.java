package com.leoman.bus.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
