package com.leoman.user.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户拥有的优惠券
 * Created by Administrator on 2016/9/14.
 */
@Entity
@Table(name = "t_user_coupon")
public class UserCoupon extends BaseEntity{

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "coupon_id")
    private Long couponId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }
}
