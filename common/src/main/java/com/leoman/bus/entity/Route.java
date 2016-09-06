package com.leoman.bus.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.system.enterprise.entity.Enterprise;

import javax.persistence.*;

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

}
