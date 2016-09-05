package com.leoman.user.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.user.entity.UserSign;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserSignDao extends IBaseJpaRepository<UserSign> {

    @Query("select a from UserSign a where a.userInfo.id = ?1")
    public UserSign findOneByUserId(Long userId);
}
