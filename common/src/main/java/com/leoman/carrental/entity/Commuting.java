package com.leoman.carrental.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 个人通勤
 * Created by 史龙 on 2016/9/12.
 */
@Entity
@Table(name = "t_commuting")
public class Commuting extends BaseEntity{

    //申请人
    @Column(name = "user_name")
    private String userName;

    //联系方式
    @Column(name = "mobile")
    private String mobile;

    //乘车人数
    @Column(name = "num")
    private Integer num;

    //出发点
    @Column(name = "start_point")
    private String startPoint;

    //目的地
    @Column(name = "end_point")
    private String endPoint;

    //出发时间
    @Column(name = "start_date")
    private String startDate;

    //到达时间
    @Column(name = "end_date")
    private String endDate;

    //返程时间
    @Column(name = "return_date")
    private String returnDate;

    //0: 每天 1:工作日 2:周末
    @Column(name = "status")
    private Integer status;

    @Column(name = "remark")
    private String remark;

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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
