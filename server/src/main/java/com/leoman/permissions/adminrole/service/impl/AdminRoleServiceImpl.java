package com.leoman.permissions.adminrole.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.permissions.adminrole.dao.AdminRoleDao;
import com.leoman.permissions.adminrole.entity.AdminRole;
import com.leoman.permissions.adminrole.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by yesong on 2016/8/31.
 */
@Service
public class AdminRoleServiceImpl extends GenericManagerImpl<AdminRole, AdminRoleDao> implements AdminRoleService{

    @Autowired
    private AdminRoleDao dao;

    @Override
    public void deleteByAdminId(Long adminId) {
        dao.deleteByAdminId(adminId);
    }
}
