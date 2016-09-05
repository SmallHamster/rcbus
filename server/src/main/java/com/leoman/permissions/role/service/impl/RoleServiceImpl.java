package com.leoman.permissions.role.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.permissions.role.dao.RoleDao;
import com.leoman.permissions.role.entity.Role;
import com.leoman.permissions.role.service.RoleService;
import com.leoman.permissions.rolemodule.entity.RoleModule;
import com.leoman.permissions.rolemodule.service.RoleModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yesong on 2016/8/26.
 */
@Service
public class RoleServiceImpl extends GenericManagerImpl<Role,RoleDao> implements RoleService{

    @Autowired
    private RoleDao dao;

    @Autowired
    private RoleModuleService roleModuleService;

    @Transactional
    @Override
    public void saveRole(Role role, String[] moduleIds) {
        roleModuleService.deleteByRoleId(role.getId());
        this.save(role);
        Long roleId = role.getId();

        RoleModule roleModule = null;
        for (String moduleId : moduleIds) {
            roleModule = new RoleModule();
            roleModule.setModuleId(Long.parseLong(moduleId));
            roleModule.setRoleId(roleId);
            roleModuleService.save(roleModule);
        }
    }
}
