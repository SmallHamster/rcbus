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

    //有效期以内的
    @Query("SELECT a FROM Coupon a WHERE a.validDateFrom <= ?1 AND a.validDateTo >= ?1 AND a.id IN (SELECT b.couponId FROM UserCoupon b WHERE b.userId=?2)")
    public List<Coupon> findList(Long data,Long userId);

    //租车订单所用的优惠券
    @Query("SELECT a FROM Coupon a WHERE a.id = (SELECT b.couponId FROM UserCoupon b WHERE b.userId=?1 AND b.rentalId=?2 AND b.isUse='2')")
    public Coupon findOne(Long userId,Long orderId);

}
