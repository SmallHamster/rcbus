package com.leoman.coupon.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 优惠券
 * Created by Daisy on 2016/9/13.
 */
public interface CouponDao extends IBaseJpaRepository<Coupon>{

}
