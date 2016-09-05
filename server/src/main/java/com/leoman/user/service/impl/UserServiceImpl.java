package com.leoman.user.service.impl;

import com.leoman.common.controller.common.CommonController;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    @Override
    public Page<UserInfo> page(final String mobile, final String nickname, final Integer gender, final Integer status,final Integer isCreator, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<UserInfo> page = userInfoDao.findAll(new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (StringUtils.isNotEmpty(mobile)) {
                    Predicate pre = cb.like(root.get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotEmpty(nickname)) {
                    Predicate pre = cb.like(root.get("nickname").as(String.class), "%" + nickname + "%");
                    predicateList.add(pre);
                }
                if (gender != null) {
                    Predicate pre = cb.equal(root.get("gender").as(Integer.class), gender);
                    predicateList.add(pre);
                }
                if (status != null) {
                    Predicate pre = cb.equal(root.get("status").as(Integer.class), status);
                    predicateList.add(pre);
                }
                if (isCreator != null) {
                    Predicate pre = cb.equal(root.get("isCreator").as(Integer.class), isCreator);
                    predicateList.add(pre);
                }
                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }

                if (result != null) {
                    query.where(result);
                }
                return query.getGroupRestriction();
            }

        }, pageRequest);

        return page;
    }


    @Override
    public UserInfo findOneByNickname(String nickname) {
        return userInfoDao.findOneByNickname(nickname);
    }

    @Override
    public List<UserInfo> findListNew() {
        return userInfoDao.findListNew(CommonController.getOldDate());
    }
}
