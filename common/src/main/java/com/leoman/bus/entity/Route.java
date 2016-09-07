package com.leoman.bus.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.system.enterprise.entity.Enterprise;

import javax.persistence.*;
import java.util.List;

/**
 *
 * 路线实体类
 * Created by Daisy on 2016/9/6.
 */
@Entity
@Table(name = "t_route")
public class Route extends BaseEntity{

    @Column(name = "start_station")
    private String startStation;//起点站

    @Column(name = "end_station")
    private String endStation;//终点站

    @Column(name = "route_name")
    private String routeName;//路线名称

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;//所属企业

    @Column(name = "is_sepecial")
    private Integer isSepecial;//是否为专线：在这里特指永旺专线（1-是，0-否）

    @Column(name = "is_show")
    private Integer isShow;//是否显示（1-是，0-否）

    @OneToMany
    @JoinColumn(name = "route_id")
    private List<RoutetTime> times;//该路线所有的时间

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

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Integer getIsSepecial() {
        return isSepecial;
    }

    public void setIsSepecial(Integer isSepecial) {
        this.isSepecial = isSepecial;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public List<RoutetTime> getTimes() {
        return times;
    }

    public void setTimes(List<RoutetTime> times) {
        this.times = times;
    }
}
