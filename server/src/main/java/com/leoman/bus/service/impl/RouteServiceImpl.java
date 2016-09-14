package com.leoman.bus.service.impl;

import com.leoman.bus.dao.RouteDao;
import com.leoman.bus.dao.RouteOrderDao;
import com.leoman.bus.dao.RouteStationDao;
import com.leoman.bus.dao.RouteTimeDao;
import com.leoman.bus.entity.*;
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
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jca.cci.core.InteractionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 路线
 * Created by Daisy on 2016/9/6.
 */
@Service
@Transactional(readOnly = true)
public class RouteServiceImpl extends GenericManagerImpl<Route, RouteDao> implements RouteService {

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private RouteTimeDao routeTimeDao;//路线时间

    @Autowired
    private BusSendDao busSendDao;//发车关系

    @Autowired
    private RouteStationDao routeStationDao;//路线站点

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RouteOrderDao routeOrderDao;

    @Override
    public Page<Route> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");
        Page<Route> page = routeDao.findAll(new Specification<Route>() {
            @Override
            public Predicate toPredicate(Root<Route> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

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

    /**
     * 保存路线
     * @param route
     * @param departTimes
     * @param backTimes
     * @param busIds
     */
    @Override
    @Transactional
    public void saveRoute(Route route, String departTimes, String backTimes, String busIds, Integer isRoundTrip,List<Map> list) {

        if(StringUtils.isEmpty(departTimes)){
            GeneralExceptionHandler.handle("发车时间不能为空");
        }

        if(StringUtils.isEmpty(busIds)){
            GeneralExceptionHandler.handle("派遣班车不能为空");
        }

        Long routeId = route.getId();
        //新增
        if(routeId == null){
            //新增路线
            route.setIsShow(1);

            //如果有往返
            if(isRoundTrip == 1){

                Route backRoute = new Route();
                ClassUtil.copyProperties(backRoute,route);
                routeDao.save(backRoute);

                //返程时间点
                String [] backTimeArr = backTimes.split("\\,");
                for (String backTime:backTimeArr) {
                    if(!StringUtils.isEmpty(backTime)){
                        RouteTime routeTime = new RouteTime();
                        routeTime.setDepartTime(backTime);//返程时间
                        routeTime.setRouteId(backRoute.getId());//对应路线
                        routeTimeDao.save(routeTime);
                    }
                }

                //路线站点
                for (int i = list.size()-1; i >= 0; i--) {
                    Map map = list.get(i);
                    RouteStation routeStation = new RouteStation();

                    double[] position = MathUtil.wgs2bd(Double.valueOf(map.get("lat").toString()) ,Double.valueOf(map.get("lon").toString()));
                    routeStation.setLat(position[0]);
                    routeStation.setLng(position[1]);
                    routeStation.setStationName((String) map.get("name"));
                    routeStation.setStationOrder(i);
                    routeStation.setRouteId(backRoute.getId());

                    Route r = routeDao.findOne(backRoute.getId());
                    if(i == (list.size()-1)){
                        r.setStartStation(routeStation.getStationName());
                    } else if(i == 0){
                        r.setEndStation(routeStation.getStationName());
                    }
                    routeDao.save(r);

                    routeStationDao.save(routeStation);
                }

                //路线发车
                String [] busIdArr = busIds.split("\\,");
                for (String busId:busIdArr) {
                    if(!StringUtils.isEmpty(busId)){
                        BusSend bs = new BusSend();
                        bs.setBus(new Bus(Long.valueOf(busId)));
                        bs.setContactId(backRoute.getId());
                        bs.setType(1);//班车
                        busSendDao.save(bs);
                    }
                }
            }

            routeDao.save(route);
        }
        //编辑
        else {
            Route orgRoute = routeDao.findOne(routeId);
            orgRoute.setEnterprise(route.getEnterprise());
            ClassUtil.copyProperties(route,orgRoute);
            routeDao.save(route);

            //删除已有的发车时间
            List<RouteTime> timeList = routeTimeDao.findByRouteId(routeId);
            for (RouteTime rt:timeList) {
                routeTimeDao.delete(rt.getId());
            }

            //如果重新上传了路线文件，则覆盖
            if(list != null){
                //删除已有的路线站点
                List<RouteStation> stationList = routeStationDao.findByRouteId(routeId);
                for (RouteStation rs:stationList) {
                    routeStationDao.delete(rs.getId());
                }

            }

            //删除已有的路线班车
            List<BusSend> sendList = busSendDao.findBus(routeId,1);
            for (BusSend bs:sendList) {
                busSendDao.delete(bs.getId());
            }
        }

        //路线时间点
        String [] departTimeArr = departTimes.split("\\,");
        for (String departTime:departTimeArr) {
            if(!StringUtils.isEmpty(departTime)){
                RouteTime routeTime = new RouteTime();
                routeTime.setDepartTime(departTime);//发车时间
                routeTime.setRouteId(route.getId());//对应路线
                routeTimeDao.save(routeTime);
            }
        }

        //路线站点
        if(list != null){
            for (int i =0; i < list.size(); i++) {
                Map map = list.get(i);
                RouteStation routeStation = new RouteStation();
                double[] position = MathUtil.wgs2bd(Double.valueOf(map.get("lat").toString()) ,Double.valueOf(map.get("lon").toString()));
                routeStation.setLat(position[0]);
                routeStation.setLng(position[1]);
                routeStation.setStationName((String) map.get("name"));
                routeStation.setStationOrder(i);
                routeStation.setRouteId(route.getId());

                Route r = routeDao.findOne(route.getId());
                if(i == 0){
                    r.setStartStation(routeStation.getStationName());
                } else if(i == (list.size() -1)){
                    r.setEndStation(routeStation.getStationName());
                }
                routeDao.save(r);

                routeStationDao.save(routeStation);
            }
        }

        //路线发车
        String [] busIdArr = busIds.split("\\,");
        for (String busId:busIdArr) {
            if(!StringUtils.isEmpty(busId)){
                BusSend bs = new BusSend();
                bs.setBus(new Bus(Long.valueOf(busId)));
                bs.setContactId(route.getId());
                bs.setType(1);//班车
                busSendDao.save(bs);
            }
        }
    }

    @Override
    @Transactional
    public void deleteRoute(Long routeId){
        //删除路线
        routeDao.delete(routeId);

        //删除已有的发车时间
        List<RouteTime> timeList = routeTimeDao.findByRouteId(routeId);
        for (RouteTime rt:timeList) {
            routeTimeDao.delete(rt.getId());
        }

        //删除已有的路线站点
        List<RouteStation> stationList = routeStationDao.findByRouteId(routeId);
        for (RouteStation rs:stationList) {
            routeStationDao.delete(rs.getId());
        }

        //删除已有的路线班车
        List<BusSend> sendList = busSendDao.findBus(routeId,1);
        for (BusSend bs:sendList) {
            busSendDao.delete(bs.getId());
        }
    }


    @Override
    @Transactional
    public void saveOrder(Long routeId, String departTime, UserInfo user){
        //保存订单
        Order order = new Order();
        order.setType(1);
        order.setOrderNo(SeqNoUtils.getMallOrderCode(0));
        order.setStatus(3);
        order.setUserInfo(user);
        order.setMobile(user.getMobile());
        orderDao.save(order);

        Route route = routeDao.findOne(routeId);

        //保存班车订单
        RouteOrder routeOrder = new RouteOrder();
        routeOrder.setStartStation(route.getStartStation());
        routeOrder.setEndStation(route.getEndStation());
        routeOrder.setRoute(new Route(routeId));
        routeOrder.setOrder(new Order(order.getId()));
        routeOrder.setDepartTime(departTime);
        routeOrderDao.save(routeOrder);
    }

}
