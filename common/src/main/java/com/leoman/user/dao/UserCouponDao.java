package com.leoman.user.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.coupon.entity.Coupon;
import com.leoman.user.entity.UserCoupon;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * Created by 史龙 on 2016/9/14.
 */
public interface UserCouponDao extends IBaseJpaRepository<UserCoupon>{

    @Query("SELECT a FROM UserCoupon a WHERE a.userId=?1 AND a.coupon.id=?2")
    public List<UserCoupon> findList(Long userId, Long couponId);

    //用的未使用的
    @Query("SELECT a FROM UserCoupon a WHERE a.userId=?1 AND a.isUse='1'")
    public List<UserCoupon> findList(Long userId);

    //使用的
    @Query("SELECT a FROM UserCoupon a WHERE a.userId=?1 AND a.rentalId=?2 AND a.isUse='2'")
    public UserCoupon findOne(Long userId, Long orderId);

}
