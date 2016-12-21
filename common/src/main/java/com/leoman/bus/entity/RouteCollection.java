package com.leoman.bus.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.image.entity.Image;
import com.leoman.user.entity.UserInfo;

import javax.persistence.*;

/**
 *
 * 班车收藏实体类
 * Created by Daisy on 2016/9/21.
 */
@Entity
@Table(name = "t_route_collection")
public class RouteCollection extends BaseEntity{

    @Transient
    private Route route;//路线

    @Column(name = "route_id")
    private Long routeId;//路线

    @Column(name = "user_id")
    private Long userId;//用户id

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
