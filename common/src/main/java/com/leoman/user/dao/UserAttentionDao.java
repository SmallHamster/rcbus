package com.leoman.user.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.user.entity.UserAttention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserAttentionDao extends IBaseJpaRepository<UserAttention> {

    @Query("select a from UserAttention a where a.user.id = ?1 and a.status = 0")
    public List<UserAttention> findListByUserId(Long userId);

    @Query("select a from UserAttention a where a.sourceUser.id = ?1 and a.status = 0")
    public List<UserAttention> iFindFans(Long userId);

    @Query("select a from UserAttention a where a.user.id = ?1 and a.status = 0")
    public Page<UserAttention> pageByUserId(Long userId, Pageable pageable);

    @Query("select a from UserAttention a where a.sourceUser.id = ?1 and a.status = 0")
    public Page<UserAttention> iPageFans(Long userId, Pageable pageable);

    @Query("select a from UserAttention a where a.user.id = ?1 and a.sourceUser.id = ?2")
    public UserAttention findOneInfo(Long userId, Long sourceId);
}
