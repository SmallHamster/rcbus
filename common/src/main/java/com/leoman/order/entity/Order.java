package com.leoman.order.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.user.entity.UserInfo;

import javax.persistence.*;

/**
 * 订单表
 * Created by 史龙 on 2016/9/7.
 */
@Entity
@Table(name = "t_order")
public class Order extends BaseEntity{

    //订单类型：1-班车，2-租车
    @Column(name = "type")
    private Integer type;

    //订单号
    @Column(name = "order_no")
    private String orderNo;

    //订单状态：0-待审核，1-待付款，2-进行中，3-已完成，4-已取消
    @Column(name = "status")
    private Integer status;

    //用户ID
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    //用户名称
    @Column(name = "user_name")
    private String userName;

    //手机
    @Column(name = "mobile")
    private String mobile;

    //* 1-5星 评价
    //司机服务(*)
    @Column(name = "driver_service")
    private Integer driverService;

    //巴士环境(*)
    @Column(name = "bus_environment")
    private Integer busEnvironment;

    //安全驾驶(*)
    @Column(name = "safe_driving")
    private Integer safeDriving;

    //准时到达(*)
    @Column(name = "arrive_time")
    private Integer arriveTime;

    @Column(name = "is_comment")
    private Integer isComment;

    @Column(name = "comment_time")
    private Long commentTime;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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

    public Integer getDriverService() {
        return driverService;
    }

    public void setDriverService(Integer driverService) {
        this.driverService = driverService;
    }

    public Integer getBusEnvironment() {
        return busEnvironment;
    }

    public void setBusEnvironment(Integer busEnvironment) {
        this.busEnvironment = busEnvironment;
    }

    public Integer getSafeDriving() {
        return safeDriving;
    }

    public void setSafeDriving(Integer safeDriving) {
        this.safeDriving = safeDriving;
    }

    public Integer getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Integer arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Integer getIsComment() {
        return isComment;
    }

    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }

    public Long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Long commentTime) {
        this.commentTime = commentTime;
    }

    public Order(){}

    public Order(Long id) {
        super.setId(id);
    }
}
