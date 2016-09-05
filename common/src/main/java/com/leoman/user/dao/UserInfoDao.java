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

    @Query("select a from UserInfo a where a.userLogin.id = ?1")
    public UserInfo findOneByUserId(Long userId);

    @Query("select a from UserInfo a where a.weixin = ?1")
    public UserInfo findOneByWeixin(String weixin);

    @Query("select a from UserInfo a where a.weibo = ?1")
    public UserInfo findOneByWeibo(String weibo);

    @Query("select a from UserInfo a where a.mobile = ?1")
    public UserInfo findOneByMobile(String mobile);

    @Query("select a from UserInfo a where a.nickname = ?1")
    public UserInfo findOneByNickname(String nickname);

    @Query("select a from UserInfo a where a.createDate > ?1")
    public List<UserInfo> findListNew(Long oldDate);
}
