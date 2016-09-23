package com.leoman.user.service;

import com.leoman.common.core.Result;
import com.leoman.common.service.GenericManager;
import com.leoman.user.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public interface UserService extends GenericManager<UserInfo> {

    public UserInfo findByMobile(String mobile);

    public void save(UserInfo userInfo, Long id, Long enterpriseId,String password);

    public Integer del(Long id,String ids);

    public Integer readExcelInfo(MultipartRequest multipartRequest);

    public void saveUser(String mobile,String password, String ip);

}
