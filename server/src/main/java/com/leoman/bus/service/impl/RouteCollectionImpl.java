package com.leoman.bus.service.impl;

import com.leoman.bus.dao.RouteCollectionDao;
import com.leoman.bus.entity.Route;
import com.leoman.bus.entity.RouteCollection;
import com.leoman.bus.service.RouteCollectionService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
