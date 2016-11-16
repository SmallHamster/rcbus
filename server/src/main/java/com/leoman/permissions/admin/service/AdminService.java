package com.leoman.permissions.admin.service;


import com.leoman.common.core.Result;
import com.leoman.permissions.admin.entity.Admin;
import com.leoman.common.service.GenericManager;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2016/3/8.
 */
public interface AdminService extends GenericManager<Admin> {

    public Admin findByUsername(String username);

    public Page<Admin> page(Integer pageNum, Integer pageSize);

    public Result save(Admin admin, Long enterpriseId, Long roleId);

    public Admin findByUsernameAndPass(String mobile, String password);
}
