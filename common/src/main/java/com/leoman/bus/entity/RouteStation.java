package com.leoman.bus.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 *
 * 路线站点实体类
 * Created by Daisy on 2016/9/9.
 */
@Entity
@Table(name = "t_route_station")
public class RouteStation extends BaseEntity{

    @Column(name = "station_name")
    private String stationName;//站点名称

    @Column(name = "lng")
    private BigDecimal lng;//经度

    @Column(name = "lat")
    private BigDecimal lat;//纬度

    @Column(name = "station_order")
    private Integer stationOrder;//站点顺序

    @Column(name = "route_id")
    private Long routeId;//站点id

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public Integer getStationOrder() {
        return stationOrder;
    }

    public void setStationOrder(Integer stationOrder) {
        this.stationOrder = stationOrder;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
