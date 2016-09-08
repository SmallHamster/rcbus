package com.leoman.carrental.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 租车报价
 * Created by 史龙 on 2016/9/8.
 */
@Entity
@Table(name = "t_car_rental_offer")
public class CarRentalOffer extends BaseEntity{

    //报价名称
    @Column(name = "name")
    private String name;

    //报价金额
    @Column(name = "amount")
    private Double amount;

    //租车ID
    @Column(name = "rental_id")
    private Long rentalId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }
}
