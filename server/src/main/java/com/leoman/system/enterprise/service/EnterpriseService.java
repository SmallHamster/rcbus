package com.leoman.system.enterprise.service;

import com.leoman.common.service.GenericManager;
import com.leoman.system.enterprise.entity.Enterprise;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/9/6.
 */
public interface EnterpriseService extends GenericManager<Enterprise> {

    void save(Long id, String name, String userName,Integer type);

    // 刷新邀请码
    String refreshInviteCode(Long id);

    // 根据邀请码加入企业
    Integer joinEnterprise(HttpServletRequest httpRequest, String code);
}
