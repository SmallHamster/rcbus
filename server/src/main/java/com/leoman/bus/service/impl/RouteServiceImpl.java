package com.leoman.bus.service.impl;

import com.leoman.bus.dao.RouteDao;
import com.leoman.bus.dao.RouteStationDao;
import com.leoman.bus.dao.RouteTimeDao;
import com.leoman.bus.entity.Bus;
import com.leoman.bus.entity.Route;
import com.leoman.bus.entity.RouteStation;
import com.leoman.bus.entity.RouteTime;
import com.leoman.bus.service.RouteService;
import com.leoman.bussend.dao.BusSendDao;
import com.leoman.bussend.entity.BusSend;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.order.dao.OrderDao;
import com.leoman.order.entity.Order;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 路线
 * Created by Daisy on 2016/9/6.
 */
@Service
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
    public void saveRoute(Route route, String departTimes, String backTimes, String busIds) {

        //如果是专线，则根据返程时间，新增两条路线
        if(route.getEnterprise().getType().equals(1)){

            if(StringUtils.isEmpty(departTimes) || StringUtils.isEmpty(backTimes)){
                GeneralExceptionHandler.handle("请填写发车时间和返程时间");
            }

            //新增路线1
            Route route1 = new Route();
            route1.setStartStation("光谷广场");
            route1.setEndStation("保利心语");
            route1.setIsShow(1);
            route1.setEnterprise(route.getEnterprise());
            route1 = saveRouteAndStation(route1);

            //新增路线1时间点
            String [] departTimeArr = departTimes.split("\\,");
            for (String departTime:departTimeArr) {
                RouteTime routeTime = new RouteTime();
                routeTime.setDepartTime(departTime);//发车时间
                routeTime.setRouteId(route1.getId());//对应路线
                routeTimeDao.save(routeTime);
            }

            //新增路线2
            Route route2 = new Route();
            route2.setStartStation("保利心语");
            route2.setEndStation("光谷广场");
            route2.setIsShow(1);
            route2.setEnterprise(route.getEnterprise());
            route2 = saveRouteAndStation(route2);

            //新增路线2时间点
            String [] backTimeArr = backTimes.split("\\,");
            for (String backTime:backTimeArr) {
                RouteTime routeTime = new RouteTime();
                routeTime.setDepartTime(backTime);//返程时间
                routeTime.setRouteId(route2.getId());//对应路线
                routeTimeDao.save(routeTime);
            }

        }
        //如果是企业，则不只新增一条路线
        else if(route.getEnterprise().getType().equals(0)) {

            if(StringUtils.isEmpty(departTimes)){
                GeneralExceptionHandler.handle("请填写发车时间");
            }

            if(StringUtils.isEmpty(busIds)){
                GeneralExceptionHandler.handle("请派遣车辆");
            }

            //新增路线
            route.setStartStation("光谷广场");
            route.setEndStation("保利心语");
            route.setIsShow(1);
//            routeDao.save(route);
            route = saveRouteAndStation(route);

            //新增路线时间点
            String [] departTimeArr = departTimes.split("\\,");
            for (String departTime:departTimeArr) {
                if(!StringUtils.isEmpty(departTime)){
                    RouteTime routeTime = new RouteTime();
                    routeTime.setDepartTime(departTime);//发车时间
                    routeTime.setRouteId(route.getId());//对应路线
                    routeTimeDao.save(routeTime);
                }
            }

            //新增路线车辆
            String [] busIdArr = busIds.split("\\,");
            for (String busId:busIdArr) {
                if(!StringUtils.isEmpty(busId)){
                    //新增每个时间点对应的车辆
                    BusSend busSend = new BusSend();
                    busSend.setType(1);//类型：路线时间
                    busSend.setContactId(Long.valueOf(route.getId()));//路线车辆id
                    busSend.setBus(new Bus(Long.valueOf(busId)));
                    busSendDao.save(busSend);
                }
            }
        }
    }

    private Route saveRouteAndStation(Route route){
        //新增路线
        routeDao.save(route);

        //路线站点
        RouteStation routeStation = new RouteStation();
        routeStation.setStationName("光谷广场");
        routeStation.setRouteId(route.getId());
        routeStation.setLng(BigDecimal.valueOf(116.331398));//经度
        routeStation.setLat(BigDecimal.valueOf(39.897445));//纬度
        routeStation.setStationOrder(1);
        routeStationDao.save(routeStation);

        RouteStation routeStation2 = new RouteStation();
        routeStation2.setStationName("保利心语");
        routeStation2.setRouteId(route.getId());
        routeStation2.setLng(BigDecimal.valueOf(116.331398));//经度
        routeStation2.setLat(BigDecimal.valueOf(39.897445));//纬度
        routeStation2.setStationOrder(2);
        routeStationDao.save(routeStation);

        return route;
    }

    @Override
    public void saveOrder(Route route, Long timeId){
        Order order = new Order();
        order.setType(1);
        order.setOrderNo(SeqNoUtils.getMallOrderCode(0));
        order.setStatus(3);
        order.setRouteTimeId(timeId);
        orderDao.save(order);
    }

}
