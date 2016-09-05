package com.leoman.user.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.user.dao.UserLoginDao;
import com.leoman.user.entity.UserLogin;
import com.leoman.user.service.UserLoginService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
@Service
public class UserLoginServiceImpl extends GenericManagerImpl<UserLogin, UserLoginDao> implements UserLoginService {
}
