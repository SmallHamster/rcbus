package com.leoman.user.service;

import com.leoman.common.core.Result;
import com.leoman.common.service.GenericManager;
import com.leoman.user.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public interface UserService extends GenericManager<UserInfo> {

//
//    public UserInfo findOneByNickname(String nickname);
//
//    // 查询新增会员列表
//    public List<UserInfo> findListNew();

    public Result save(UserInfo userInfo, Long id, Long enterpriseId);

    public Integer del(Long id,String ids);

}
