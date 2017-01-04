package com.leoman.wechat.bus.controller;

import com.leoman.bus.entity.*;
import com.leoman.bus.service.*;
import com.leoman.bus.util.GpxUtil;
import com.leoman.common.controller.common.CommonController;
import com.leoman.common.core.Result;
import com.leoman.common.service.Query;
import com.leoman.entity.Configue;
import com.leoman.system.banner.entity.Banner;
import com.leoman.system.banner.service.BannerService;
import com.leoman.user.entity.UserInfo;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信端：班车路线基础类
 * Created by Daisy on 2016/9/26.
 */
@Controller
public class RouteBaseController extends CommonController {

    @Autowired
    private RouteStationService routeStationService;

    @Autowired
    private BusService busService;

    @Autowired
    private RouteCollectionService routeCollectionService;

    @Autowired
    private RouteOrderService routeOrderService;

    @Autowired
    private RouteTimeService routeTimeService;

    /**
     * 设置当前班车所在站点
     * @param busList
     * @param stationList
     */
    public void handleBusCurStation(List<Bus> busList, List<RouteStation> stationList){
        for (Bus bus:busList) {
            handleBusCurStation(bus,stationList);
        }
    }

    public void handleBusCurStation(Bus bus, List<RouteStation> stationList){
        if(bus != null){
            Double curLat = bus.getCurLat();//当前纬度
            Double curLng = bus.getCurLng();//当前经度
            if(curLat != null && curLng != null){
                Map map = new HashMap();
                Double minDistance = 0d;
                for (int i=0; i < stationList.size(); i++) {
                    Double rsLat = stationList.get(i).getLat();
                    Double rsLng = stationList.get(i).getLng();
                    if(rsLat != null && rsLng != null){
                        Double distance = GpxUtil.getDistance(curLng,curLat,rsLng,rsLat);
                        if(i==0){
                            minDistance = distance;
                        }else{
                            minDistance = minDistance>distance?distance:minDistance;
                        }
                        map.put(distance,stationList.get(i).getId());
                    }
                }
                //如果最短距离小于2km，则算作在某个站，否则不在任何站
                if(minDistance <= 20000){
                    Long stationId = (Long) map.get(minDistance);
                    RouteStation station = routeStationService.queryByPK(stationId);
                    bus.setStationId(stationId);
                    bus.setStationName(station.getStationName());
                }
            }
        }
    }

    /**
     * 判断用户是否收藏
     * @param route
     * @param userId
     */
    private void handleRouteIsCollect(Route route, Long userId){
        if(route != null){
            RouteCollection rc = routeCollectionService.findOne(route.getId(),userId);
            if(rc != null){
                route.setIsCollect(1);
            }else{
                route.setIsCollect(0);
            }
        }
    }

    /**
     * 设置路线的预定人数
     * @param route
     * @param departTime
     */
    private void handleRouteOrderNum(Route route,String departTime){
        route.setOrderNum(0);
        if(route != null){
            Integer count = routeOrderService.findOrderNum(route.getId(),departTime);
            route.setOrderNum(count);
        }
    }

    /**
     * 处理路线距离用户最近的一班车
     * @param routeList
     * @param userLat
     * @param userLng
     */
    public void handleRoute(List<Route> routeList, Double userLat, Double userLng, Long userId){
        for (Route route:routeList) {
            if (route != null) {
                Long routeId = route.getId();

                //设置用户是否收藏
                handleRouteIsCollect(route, userId);

                //设置路线的最近一班车
                List<Bus> busList = busService.findBusOrderByDistance(routeId, userLat, userLng);
                if (busList != null && busList.size() >= 1) {
                    Bus bus = busList.get(0);
                    List<RouteStation> stationList = routeStationService.findByRouteId(routeId);
                    handleBusCurStation(bus, stationList);//设置当前车在哪个站点
                    route.setBus(busList.get(0));
                }

                //设置路线的预定人数
                List<RouteTime> times = routeTimeService.findByCurrentTime(routeId);
                if (times != null && times.size() > 0) {
                    route.setTempTimes(times);
                    handleRouteOrderNum(route, times.get(0).getDepartTime());
                }
            }
        }
    }

}
