package com.leoman.bus.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.system.enterprise.entity.Enterprise;

import javax.persistence.*;

/**
 *
 * 路线时间实体类
 * Created by Daisy on 2016/9/7.
 */
@Entity
@Table(name = "t_route_time")
public class RouteTime extends BaseEntity{

    @Column(name = "depart_time")
    private String depart_time;//出发时间

    @Column(name = "route_id")
    private Long route_id;//路线id

    public String getDepart_time() {
        return depart_time;
    }

    public void setDepart_time(String depart_time) {
        this.depart_time = depart_time;
    }

    public Long getRoute_id() {
        return route_id;
    }

    public void setRoute_id(Long route_id) {
        this.route_id = route_id;
    }
}
