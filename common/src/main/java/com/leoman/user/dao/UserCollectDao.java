package com.leoman.user.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.user.entity.UserCollect;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserCollectDao extends IBaseJpaRepository<UserCollect> {

    @Query("select a from UserCollect a where a.userInfo.id = ?1 and a.sourceId = ?2 and a.type = ?3")
    public UserCollect findOneByParams(Long userId, Long sourceId, Integer type);

    @Query("select a from UserCollect a where a.userInfo.id = ?1")
    public List<UserCollect> findListByUserId(Long userId);
}
