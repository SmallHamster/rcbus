package com.leoman.system.enterprise.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.system.enterprise.entity.Enterprise;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 史龙 on 2016/9/6.
 */
public interface EnterpriseDao extends IBaseJpaRepository<Enterprise> {

    @Query("select a from Enterprise a where a.inviteCode = ?1")
    List<Enterprise> findListByInviteCode(String inviteCode);
}
