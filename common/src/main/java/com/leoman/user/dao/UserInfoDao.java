package com.leoman.user.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.user.entity.UserInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserInfoDao extends IBaseJpaRepository<UserInfo> {

    @Query("select a from UserInfo a where a.mobile = ?1")
    public UserInfo findByMobile(String mobile);

}
