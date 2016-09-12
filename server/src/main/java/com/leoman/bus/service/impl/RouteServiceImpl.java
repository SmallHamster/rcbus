package com.leoman.bus.service.impl;

import com.leoman.bus.dao.RouteDao;
import com.leoman.bus.dao.RouteStationDao;
import com.leoman.bus.dao.RouteTimeDao;
import com.leoman.bus.entity.Bus;
import com.leoman.bus.entity.Route;
import com.leoman.bus.entity.RouteStation;
import com.leoman.bus.entity.RouteTime;
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

        Long routeId = route.getId();
        //新增
        if(routeId == null){
            //新增路线
            routeDao.save(route);

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
            for (int i =0; i < list.size(); i++) {
                Map map = list.get(i);
                Object lat = map.get("lat");
                Object lng = map.get("lon");
                RouteStation routeStation = new RouteStation();

                double[] position = MathUtil.wgs2bd(Double.valueOf(map.get("lat").toString()) ,Double.valueOf(map.get("lon").toString()));
                routeStation.setLat(position[0]);
                routeStation.setLng(position[1]);
                routeStation.setStationName((String) map.get("name"));
                routeStation.setStationOrder(i);
                routeStation.setRouteId(route.getId());
                routeStationDao.save(routeStation);
            }

            //如果有往返
            if(isRoundTrip == 1){

                Route backRoute = new Route();
                ClassUtil.copyProperties(backRoute,route);

                //返程时间点
                String [] backTimeArr = backTimes.split("\\,");
                for (String backTime:backTimeArr) {
                    RouteTime routeTime = new RouteTime();
                    routeTime.setDepartTime(backTime);//返程时间
                    routeTime.setRouteId(backRoute.getId());//对应路线
                    routeTimeDao.save(routeTime);
                }

                //路线站点
                for (int i = list.size()-1; i >= 0; i--) {
                    Map map = list.get(i);
                    Object lat = map.get("lat");
                    Object lng = map.get("lon");
                    RouteStation routeStation = new RouteStation();

                    double[] position = MathUtil.wgs2bd((Double) map.get("lat"),(Double)map.get("lon"));
                    routeStation.setLat(position[0]);
                    routeStation.setLng(position[1]);
                    routeStation.setStationName((String) map.get("name"));
                    routeStation.setStationOrder(i);
                    routeStation.setRouteId(route.getId());
                    routeStationDao.save(routeStation);
                }
            }


        }


    }

/*    private Route saveRouteAndStationAndTime(Route route, Integer isRoundTrip, List<Map> list){
        //新增路线
        routeDao.save(route);

        if(isRoundTrip == 1){

        }
        //路线站点
        for (Map map:list) {
            Object lat = map.get("lat");
            Object lng = map.get("lon");
            RouteStation routeStation = new RouteStation();

            double[] position = MathUtil.wgs2bd((Double) map.get("lat"),(Double)map.get("lon"));
            routeStation.setLat(position[0]);
            routeStation.setLng(position[1]);
            routeStation.setStationName((String) map.get("name"));
            routeStation.setStationOrder((Integer) map.get("order"));
            routeStation.setRouteId(route.getId());
        }

        return route;
    }*/

    @Override
    public void saveOrder(Route route, Long timeId, UserInfo user){
        Order order = new Order();
        order.setType(1);
        order.setOrderNo(SeqNoUtils.getMallOrderCode(0));
        order.setStatus(3);
        order.setRouteTimeId(timeId);
        order.setUserInfo(user);
        order.setMobile(user.getMobile());
        orderDao.save(order);
    }

}
