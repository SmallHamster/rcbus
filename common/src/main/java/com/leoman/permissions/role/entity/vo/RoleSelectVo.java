package com.leoman.permissions.role.entity.vo;

import com.leoman.permissions.role.entity.Role;

import java.util.List;

/**
 * 所有角色，拥有角色VO
 * Created by yesong on 2016/8/31.
 */
public class RoleSelectVo {

    private List<Role> allRoleList;

    private List<Long> hasRoleList;

    public RoleSelectVo(List<Role> allRoleList,List<Long> hasRoleList) {
        this.allRoleList = allRoleList;
        this.hasRoleList = hasRoleList;
    }

    public List<Role> getAllRoleList() {
        return allRoleList;
    }

    public void setAllRoleList(List<Role> allRoleList) {
        this.allRoleList = allRoleList;
    }

    public List<Long> getHasRoleList() {
        return hasRoleList;
    }

    public void setHasRoleList(List<Long> hasRoleList) {
        this.hasRoleList = hasRoleList;
    }
}
