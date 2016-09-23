package com.leoman.user.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.user.entity.ReceiveCoupon;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * Created by Administrator on 2016/9/23.
 */
public interface ReceiveCouponDao extends IBaseJpaRepository<ReceiveCoupon> {

    @Query("SELECT a FROM ReceiveCoupon a WHERE a.mobile=?1 AND a.rentalId=?2")
    public ReceiveCoupon findOne(String mobile,Long rentalId);

}
