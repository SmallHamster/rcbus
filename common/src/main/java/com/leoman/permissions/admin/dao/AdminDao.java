package com.leoman.permissions.admin.dao;

import com.leoman.permissions.admin.entity.Admin;
import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.user.entity.UserLogin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface AdminDao extends IBaseJpaRepository<Admin> {

    @Query("select a from Admin a where a.username = ?1")
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    public Admin findByUsername(String username);

    @Query("select a from Admin a where a.mobile = ?1 and a.password = ?2")
    public Admin findByUsernameAndPass(String mobile, String password);
}
