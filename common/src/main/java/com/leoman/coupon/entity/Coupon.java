package com.leoman.coupon.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 *
 * 优惠券类
 * Created by Daisy on 2016/9/13.
 */
@Entity
@Table(name = "t_coupon")
public class Coupon extends BaseEntity{

    @Column(name = "name")
    private String name;//礼券名称

    @Column(name = "gain_way")
    private Integer gainWay;//获取方式：1-好友分享，2-订单完成后，3-注册后

    @Column(name = "coupon_way")
    private Integer couponWay;//优惠方式：1-折扣，2-减免金额

    @Column(name = "valid_start_date")
    private Long validStartDate;//有效期开始

    @Column(name = "valid_end_date")
    private Long validEndDate;//有效期结束

    @Column(name = "discount_percent")
    private BigDecimal discountPercent;//折扣百分比

    @Column(name = "discount_top_money")
    private BigDecimal discountTopMoney;//最高立减金额

    @Column(name = "reduce_money")
    private BigDecimal reduceMoney;//减免金额、


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGainWay() {
        return gainWay;
    }

    public void setGainWay(Integer gainWay) {
        this.gainWay = gainWay;
    }

    public Integer getCouponWay() {
        return couponWay;
    }

    public void setCouponWay(Integer couponWay) {
        this.couponWay = couponWay;
    }

    public Long getValidStartDate() {
        return validStartDate;
    }

    public void setValidStartDate(Long validStartDate) {
        this.validStartDate = validStartDate;
    }

    public Long getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(Long validEndDate) {
        this.validEndDate = validEndDate;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getDiscountTopMoney() {
        return discountTopMoney;
    }

    public void setDiscountTopMoney(BigDecimal discountTopMoney) {
        this.discountTopMoney = discountTopMoney;
    }

    public BigDecimal getReduceMoney() {
        return reduceMoney;
    }

    public void setReduceMoney(BigDecimal reduceMoney) {
        this.reduceMoney = reduceMoney;
    }
}
