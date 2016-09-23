package com.leoman.user.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 非用户优惠券表
 * Created by 史龙 on 2016/9/23.
 */
@Entity
@Table(name = "t_not_user_coupon")
public class NotUserCoupon extends BaseEntity{

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "rental_id")
    private Long rentalId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }
}
