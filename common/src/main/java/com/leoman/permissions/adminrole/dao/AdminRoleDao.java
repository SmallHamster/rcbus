package com.leoman.permissions.adminrole.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.permissions.adminrole.entity.AdminRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yesomng on 2016/8/31.
 */
public interface AdminRoleDao extends IBaseJpaRepository<AdminRole>{

    @Transactional
    @Modifying
    @Query("delete from AdminRole ar where ar.adminId = ?1")
    public void deleteByAdminId(Long adminId);
}
