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
    private String departTime;//出发时间

    @Column(name = "route_id")
    private Long routeId;//路线id

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
