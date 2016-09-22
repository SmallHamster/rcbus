package com.leoman.user.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.exception.GeneralException;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.dao.UserLoginDao;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserLogin;
import com.leoman.user.service.UserLoginService;
import com.leoman.utils.CookiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
@Service
public class UserLoginServiceImpl extends GenericManagerImpl<UserLogin, UserLoginDao> implements UserLoginService {

    @Autowired
    private UserLoginDao userLoginDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo login(String username, String password){
        UserLogin userLogin = userLoginDao.findByUsername(username);
        if(userLogin == null){
            GeneralExceptionHandler.handle("该用户不存在");
        }

        UserLogin login = userLoginDao.findByUsernameAndPass(username,password);
        if(login == null){
            GeneralExceptionHandler.handle("用户名或密码错误");
        }

        UserInfo user = userInfoDao.findByMobile(username);
        if(user == null){
            GeneralExceptionHandler.handle("找不到该用户");
        }
        return user;
    }

    @Override
    public UserLogin findByUsername(String username) {
        return userLoginDao.findByUsername(username);
    }

}
