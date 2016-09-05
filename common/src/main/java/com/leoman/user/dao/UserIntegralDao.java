package com.leoman.user.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.user.entity.UserIntegral;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserIntegralDao extends IBaseJpaRepository<UserIntegral> {

    @Query("select a from UserIntegral a where a.userInfo.id = ?1 order by a.id desc")
    public Page<UserIntegral> pageByUserId(Long userId, Pageable pageable);

    @Query("select a from UserIntegral a where a.userInfo.id = ?1 and a.type = ?2 order by a.id desc")
    public Page<UserIntegral> pageByParams(Long userId, Integer type, Pageable pageable);
}
