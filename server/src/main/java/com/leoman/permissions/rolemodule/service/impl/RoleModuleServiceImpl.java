package com.leoman.permissions.rolemodule.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.permissions.rolemodule.dao.RoleModuleDao;
import com.leoman.permissions.rolemodule.entity.RoleModule;
import com.leoman.permissions.rolemodule.service.RoleModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * 角色-模块关联实体
 * Created by yesong on 2016/8/30.
 */
@Service
public class RoleModuleServiceImpl extends GenericManagerImpl<RoleModule, RoleModuleDao> implements RoleModuleService{

    @Autowired
    private RoleModuleDao dao;

    @Override
    public void deleteByRoleId(Long roleId) {
        dao.deleteByRoleId(roleId);
    }
}
