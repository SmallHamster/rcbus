package com.leoman.user.service;

import com.leoman.common.service.GenericManager;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserLogin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
public interface UserLoginService extends GenericManager<UserLogin>{

    public UserInfo login(String username, String password);

    public UserLogin findByUsername(String username);

    public Boolean loginWeixin(HttpServletRequest request, HttpServletResponse response, String username, String password);
}
