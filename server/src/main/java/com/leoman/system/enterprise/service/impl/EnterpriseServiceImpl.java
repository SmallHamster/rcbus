package com.leoman.system.enterprise.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.entity.Constant;
import com.leoman.pay.util.MD5Util;
import com.leoman.permissions.admin.entity.Admin;
import com.leoman.permissions.admin.service.AdminService;
import com.leoman.permissions.adminrole.entity.AdminRole;
import com.leoman.permissions.adminrole.service.AdminRoleService;
import com.leoman.system.enterprise.dao.EnterpriseDao;
import com.leoman.system.enterprise.entity.Enterprise;
import com.leoman.system.enterprise.service.EnterpriseService;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserService;
import com.leoman.utils.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/9/6.
 */
@Service
public class EnterpriseServiceImpl extends GenericManagerImpl<Enterprise, EnterpriseDao> implements EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void save(Long id, String name, String userName, Integer type) {
        Enterprise enterprise = null;
        if (id != null) {
            enterprise = queryByPK(id);
        }
        if (enterprise != null) {
            enterprise.setName(name);
            save(enterprise);
        } else {
            //新增企业
            enterprise = new Enterprise();
            enterprise.setName(name);
            enterprise.setType(type);
            save(enterprise);
            //如果新增的是企业
            if (type == 0) {
                //新增一个企业管理员
                Admin admin = new Admin();
                admin.setEnterprise(enterprise);
                admin.setUsername(userName);
                admin.setPassword(MD5Util.MD5Encode("888888", "UTF-8"));
                admin.setMobile("");
                adminService.save(admin);
                //设置权限
                AdminRole adminRole = new AdminRole();
                adminRole.setAdminId(admin.getId());
                adminRole.setRoleId(12L);
                adminRoleService.save(adminRole);
                //新增一条用户信息
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(admin.getId());
                userInfo.setMobile(admin.getMobile());
//                userInfo.setPassword(admin.getPassword());
                userInfo.setEnterprise(admin.getEnterprise());
                //企业管理
                userInfo.setType(0);
                userService.save(userInfo);
            }
        }
    }

    @Override
    public String refreshInviteCode(Long id) {
        Enterprise enterprise = enterpriseDao.findOne(id);

        if (null != enterprise) {
            String newInviteCode = RandomUtil.getCode();

            while (enterpriseDao.findListByInviteCode(newInviteCode).size() > 0) {
                newInviteCode = RandomUtil.getCode();
            }

            enterprise.setInviteCode(newInviteCode);
            enterpriseDao.save(enterprise);

            return newInviteCode;
        }

        return "";
    }

    @Override
    public Integer joinEnterprise(HttpServletRequest httpRequest, String code) {
        if (StringUtils.isBlank(code)) {
            return 0;
        }

        try {
            List<Enterprise> list = enterpriseDao.findListByInviteCode(code);

            if (null == list || list.size() == 0) {
                return 0;
            }

            UserInfo userInfo = (UserInfo) httpRequest.getSession().getAttribute(Constant.SESSION_MEMBER_USER);
            userInfo.setEnterprise(list.get(0));
            userInfo.setType(1);
            userService.save(userInfo);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
