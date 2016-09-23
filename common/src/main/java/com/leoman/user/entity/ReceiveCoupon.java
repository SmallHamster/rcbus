package com.leoman.user.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;

/**
 * 用户优惠券领取表
 * Created by 史龙 on 2016/9/23.
 */
public class ReceiveCoupon extends BaseEntity{

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "rental_id")
    private Long rentalId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }
}
