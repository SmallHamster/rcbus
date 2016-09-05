package com.leoman.permissions.adminrole.service;

import com.leoman.common.service.GenericManager;
import com.leoman.permissions.adminrole.entity.AdminRole;

/**
 * Created by yesong on 2016/8/31.
 */
public interface AdminRoleService extends GenericManager<AdminRole>{

    public void deleteByAdminId(Long adminId);
}
