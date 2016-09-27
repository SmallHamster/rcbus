package com.leoman.user.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.user.entity.WeChatUser;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by wangbin on 2015/7/8.
 */
public interface WeChatUserDao extends IBaseJpaRepository<WeChatUser> {

    @Query("select a from WeChatUser a where a.openId = ?1")
    public WeChatUser findByOpenId(String openId);
}
