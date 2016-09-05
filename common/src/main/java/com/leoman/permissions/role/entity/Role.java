package com.leoman.permissions.role.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 角色实体类
 * Created by yesong on 2016/8/26.
 */
@Entity
@Table(name = "t_role")
public class Role extends BaseEntity{

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
