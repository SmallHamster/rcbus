package com.leoman.bus.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.order.entity.Order;

import javax.persistence.*;

/**
 *
 * 班车订单实体类
 * Created by Daisy on 2016/9/13.
 */
@Entity
@Table(name = "t_route_order")
public class RouteOrder extends BaseEntity{

    @Column(name = "start_station")
    private String startStation;//起点站

    @Column(name = "end_station")
    private String endStation;//终点站

    @Column(name = "depart_time")
    private String departTime;//终点站

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;//订单

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;//路线

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
