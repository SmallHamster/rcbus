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

    @Column(name = "valid_date_from")
    private Long validDateFrom;//有效期开始

    @Column(name = "valid_date_to")
    private Long validDateTo;//有效期结束

    @Column(name = "discount_percent")
    private BigDecimal discountPercent;//折扣百分比

    @Column(name = "discount_top_money")
    private BigDecimal discountTopMoney;//最高立减金额

    @Column(name = "reduce_money")
    private BigDecimal reduceMoney;//减免金额

    @Column(name = "is_limit")
    private Integer isLimit;//是否有消费限制（1-是，0-否）

    @Column(name = "limit_money")
    private BigDecimal limitMoney;//限制金额（即消费满多少才可以享受折扣和减免）

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

    public Long getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(Long validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    public Long getValidDateTo() {
        return validDateTo;
    }

    public void setValidDateTo(Long validDateTo) {
        this.validDateTo = validDateTo;
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

    public Integer getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(Integer isLimit) {
        this.isLimit = isLimit;
    }

    public BigDecimal getLimitMoney() {
        return limitMoney;
    }

    public void setLimitMoney(BigDecimal limitMoney) {
        this.limitMoney = limitMoney;
    }
}
