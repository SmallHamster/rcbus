package com.leoman.user.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.user.entity.UserCoupon;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * Created by 史龙 on 2016/9/14.
 */
public interface UserCouponDao extends IBaseJpaRepository<UserCoupon>{

    @Query("SELECT a FROM UserCoupon a WHERE a.userId=?1 AND a.couponId=?2")
    public List<UserCoupon> findList(Long userId, Long couponId);

}
