package com.leoman.user.service;

import com.leoman.common.service.GenericManager;
import com.leoman.user.entity.UserInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public interface UserService extends GenericManager<UserInfo> {

    public Page<UserInfo> page(String mobile, String nickname, Integer gender, Integer status, Integer isCreator, Integer pageNum, Integer pageSize);

    public UserInfo findOneByNickname(String nickname);

    // 查询新增会员列表
    public List<UserInfo> findListNew();
}
