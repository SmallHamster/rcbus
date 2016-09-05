package com.leoman.user.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.user.entity.UserLogin;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserLoginDao extends IBaseJpaRepository<UserLogin>{

    @Query("select userLogin from UserLogin userLogin where userLogin.username = ?1")
    public UserLogin findByUsername(String username);
}
