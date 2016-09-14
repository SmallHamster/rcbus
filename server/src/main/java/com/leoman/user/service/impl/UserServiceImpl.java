package com.leoman.user.service.impl;

import com.leoman.common.controller.common.CommonController;
import com.leoman.common.core.Result;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.pay.util.MD5Util;
import com.leoman.permissions.admin.service.AdminService;
import com.leoman.system.enterprise.entity.Enterprise;
import com.leoman.system.enterprise.service.EnterpriseService;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserLogin;
import com.leoman.user.entity.vo.UserExportVo;
import com.leoman.user.service.UserLoginService;
import com.leoman.user.service.UserService;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.ReadExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Service
public class UserServiceImpl extends GenericManagerImpl<UserInfo, UserInfoDao> implements UserService {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private EnterpriseService enterpriseService;

    @Override
    @Transient
    public Result save(UserInfo userInfo, Long id, Long enterpriseId) {
        Enterprise enterprise = enterpriseService.queryByPK(enterpriseId);
        UserLogin userLogin  = new UserLogin();
        try {
            //新增一条用户登录
            userLogin.setUsername(userInfo.getMobile());
            userLogin.setPassword(MD5Util.MD5Encode(userInfo.getPassword(),"UTF-8"));
            userLoginService.save(userLogin);

            //新增一条企业会员
            userInfo.setEnterprise(enterprise);
            userInfo.setUserId(userLogin.getId());
            userInfo.setPassword(MD5Util.MD5Encode(userInfo.getPassword(),"UTF-8"));
            userInfo.setType(1);
            save(userInfo);

        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Integer del(Long id, String ids) {
        if (id==null && StringUtils.isBlank(ids)){
            return 1;
        }
        UserInfo userInfo = null;
        try {
            if(id!=null){
                userInfo = queryByPK(id);
                delete(userInfo);
                //删除登录表信息
                if(userInfo.getType()==0){
                    adminService.delete(adminService.queryByPK(userInfo.getUserId()));
                }else {
                    userLoginService.delete(userLoginService.queryByPK(userInfo.getUserId()));
                }
            }else {
                Long[] ss = JsonUtil.json2Obj(ids,Long[].class);
                for (Long _id : ss) {
                    userInfo = queryByPK(_id);
                    delete(queryByPK(_id));
                    //删除登录表信息
                    if(userInfo.getType()==0){
                        adminService.delete(adminService.queryByPK(userInfo.getUserId()));
                    }else {
                        userLoginService.delete(userLoginService.queryByPK(userInfo.getUserId()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer readExcelInfo(MultipartRequest multipartRequest) {

        UserInfo userInfo = null;
        try{
            MultipartFile multipartFile = multipartRequest.getFile("feedback");
            List<UserExportVo> list = ReadExcelUtil.readExcel(multipartFile);
            if (null == list || list.size() == 0) {
                return 0;
            }

            for (UserExportVo userExportVo : list)  {
                //新建一条用户信息 存号码 固定密码 员工身份
                userInfo = new UserInfo();
                userInfo.setMobile(userExportVo.getMobile());
                userInfo.setPassword(MD5Util.MD5Encode("888888","UTF-8"));
                userInfo.setType(1);

                //判断账号是否存在
                List<UserLogin> userLogins = userLoginService.queryByProperty("username",userExportVo.getMobile());
                if(!userLogins.isEmpty() && userLogins.size()>0){
                    return 2;
                }

                //新增一条用户登录信息 存号码 固定密码
                UserLogin userLogin = new UserLogin();
                userLogin.setUsername(userExportVo.getMobile());
                userLogin.setPassword(MD5Util.MD5Encode("888888","UTF-8"));
                userLoginService.save(userLogin);

                //设置关联ID
                userInfo.setUserId(userLogin.getId());

                //判断公司是否存在
                List<Enterprise> enterprises = enterpriseService.queryByProperty("name",userExportVo.getEnterprise().trim());
                if(!enterprises.isEmpty() && enterprises.size()>0){
                    //公司存在 存公司ID
                    userInfo.setEnterprise(enterprises.get(0));
                }else {
                    //公司不存在 新增公司 存公司ID
                    Enterprise enterprise = new Enterprise();
                    enterprise.setName(userExportVo.getEnterprise().trim());
                    enterprise.setType(0);
                    enterpriseService.save(enterprise);

                    userInfo.setEnterprise(enterprise);
                }

                save(userInfo);

            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

//    @Override
//    public UserInfo findOneByNickname(String nickname) {
//        return userInfoDao.findOneByNickname(nickname);
//    }
//
//    @Override
//    public List<UserInfo> findListNew() {
//        return userInfoDao.findListNew(CommonController.getOldDate());
//    }
}
