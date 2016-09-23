package com.leoman.bus.entity;

import com.leoman.bussend.entity.BusSend;
import com.leoman.entity.BaseEntity;
import com.leoman.system.enterprise.entity.Enterprise;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 *
 * 路线实体类
 * Created by Daisy on 2016/9/6.
 */
@Entity
@Table(name = "t_route")
public class Route extends BaseEntity{

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    @Where(clause = "type = '1'")
    private Set<BusSend> busSends;

    @Column(name = "start_station")
    private String startStation;//起点站

    @Column(name = "end_station")
    private String endStation;//终点站

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;//所属企业

    @Column(name = "is_show")
    private Integer isShow;//是否显示（1-是，0-否）

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    private List<RouteTime> times;//该路线所有的时间

    @Transient
    private Integer isCollect;//是否收藏（1-是，0-否）

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

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public List<RouteTime> getTimes() {
        return times;
    }

    public void setTimes(List<RouteTime> times) {
        this.times = times;
    }

    public Route(){}

    public Route(Long id) {
        super.setId(id);
    }

    public Set<BusSend> getBusSends() {
        return busSends;
    }

    public void setBusSends(Set<BusSend> busSends) {
        this.busSends = busSends;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }
}
