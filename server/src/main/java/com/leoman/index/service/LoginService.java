package com.leoman.index.service;

import com.leoman.permissions.admin.entity.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangbin on 2015/3/3.
 */
public interface LoginService {

    public Admin getMember(HttpServletRequest request, String type);

    public Boolean login(HttpServletRequest request, String username, String password, String remark);

    public Boolean login(HttpServletRequest request, Admin member, String remark, String type);

    public Boolean login(HttpServletRequest request, String username, String password, String type, String remark);

    public void logOut(HttpServletRequest request, String type);

    public Boolean loginWeixin(HttpServletRequest request, HttpServletResponse response, String username, String password);

}
