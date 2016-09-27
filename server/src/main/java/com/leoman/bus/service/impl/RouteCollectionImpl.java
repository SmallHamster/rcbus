package com.leoman.bus.service.impl;

import com.leoman.bus.dao.*;
import com.leoman.bus.entity.*;
import com.leoman.bus.service.RouteCollectionService;
import com.leoman.bus.service.RouteService;
import com.leoman.bus.util.MathUtil;
import com.leoman.bussend.dao.BusSendDao;
import com.leoman.bussend.entity.BusSend;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.order.dao.OrderDao;
import com.leoman.order.entity.Order;
import com.leoman.user.entity.UserInfo;
import com.leoman.utils.ClassUtil;
import com.leoman.utils.SeqNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 路线收藏
 * Created by Daisy on 2016/9/21.
 */
@Service
@Transactional(readOnly = true)
public class RouteCollectionImpl extends GenericManagerImpl<RouteCollection, RouteCollectionDao> implements RouteCollectionService {

    @Autowired
    private RouteCollectionDao routeCollectionDao;

    @Override
    public List<Route> findByUser(Long userId) {
        return routeCollectionDao.findByUser(userId);
    }

    /**
     * 收藏
     * @param routeId
     * @param userId
     * @param isCollect
     */
    @Override
    @Transactional
    public void doCollect(Long routeId, Long userId, Boolean isCollect){
        RouteCollection r = routeCollectionDao.findOne(routeId,userId);
        //收藏
        if(isCollect){
            if(r == null){
                RouteCollection rc = new RouteCollection();
                rc.setRoute(new Route(routeId));
                rc.setUserId(userId);
                routeCollectionDao.save(rc);
            }
        }else{
            if(r != null){
                routeCollectionDao.delete(r);
            }
        }
    }

    /**
     * 取消多个收藏
     * @param routeIds
     * @param userId
     */
    @Override
    @Transactional
    public void multiDel(String routeIds, Long userId){
        String [] routeIdArr = routeIds.split("\\,");
        for (String routeId:routeIdArr) {
            RouteCollection rc = routeCollectionDao.findOne(Long.valueOf(routeId), userId);
            if(rc != null){
                routeCollectionDao.delete(rc);
            }
        }
    }

    @Override
    public RouteCollection findOne(Long routeId, Long userId) {
        return routeCollectionDao.findOne(routeId,userId);
    }
}
