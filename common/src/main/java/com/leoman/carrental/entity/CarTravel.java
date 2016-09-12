package com.leoman.carrental.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 租车出游
 * Created by 史龙 on 2016/9/12.
 */
@Entity
@Table(name = "t_car_travel")
public class CarTravel extends BaseEntity{

    //活动名称
    @Column(name = "travel_name")
    private String travelName;

    //申请人
    @Column(name = "user_name")
    private String userName;

    //联系方式
    @Column(name = "mobile")
    private String mobile;

    //出行时间
    @Column(name = "travel_time")
    private Long travelTime;

    //报名人数
    @Column(name = "num")
    private Integer num;

    //备注
    @Column(name = "content")
    private String content;

    public String getTravelName() {
        return travelName;
    }

    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Long travelTime) {
        this.travelTime = travelTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
