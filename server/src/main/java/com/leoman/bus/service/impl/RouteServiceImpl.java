package com.leoman.bus.service.impl;

import com.leoman.bus.dao.RouteDao;
import com.leoman.bus.dao.RouteOrderDao;
import com.leoman.bus.dao.RouteStationDao;
import com.leoman.bus.dao.RouteTimeDao;
import com.leoman.bus.entity.Route;
import com.leoman.bus.entity.RouteOrder;
import com.leoman.bus.entity.RouteStation;
import com.leoman.bus.entity.RouteTime;
import com.leoman.bus.service.RouteService;
import com.leoman.bus.util.MathUtil;
import com.leoman.bussend.dao.BusSendDao;
import com.leoman.bussend.entity.BusSend;
import com.leoman.common.core.ErrorType;
import com.leoman.common.core.Result;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.order.dao.OrderDao;
import com.leoman.order.entity.Order;
import com.leoman.system.enterprise.dao.EnterpriseDao;
import com.leoman.system.enterprise.entity.Enterprise;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.entity.UserInfo;
import com.leoman.utils.ClassUtil;
import com.leoman.utils.SeqNoUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private RouteOrderDao routeOrderDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private EnterpriseDao enterpriseDao;

    /**
     * 保存路线
     *
     * @param route
     * @param departTimes
     * @param backTimes
     * @param busIds
     */
    @Override
    @Transactional
    public Result saveRoute(Route route, String departTimes, String backTimes, String busIds, Integer isRoundTrip, List<Map> list) {
        Result result = Result.success();

        if (StringUtils.isEmpty(departTimes)) {
            return new Result().failure(ErrorType.ERROR_CODE_00029);//发车时间不能为空
        }

        if (StringUtils.isEmpty(busIds)) {
            return new Result().failure(ErrorType.ERROR_CODE_00030);//派遣班车不能为空
        }

        List<Route> routeList = routeDao.findByLineName(route.getLineName());
        if (routeList != null && routeList.size() > 2) {
            return Result.failure(ErrorType.ERROR_CODE_00034);//该所属路线已存在1或2个，请选择其他所属路线
        }

        Long routeId = route.getId();
        //新增
        if (routeId == null) {
            if (list == null) {
                return new Result().failure(ErrorType.ERROR_CODE_00031);//路线不能为空
            }
            //新增路线
            route.setIsShow(1);
            routeDao.save(route);
            routeId = route.getId();
        }
        //编辑
        else {
            Route orgRoute = routeDao.findOne(routeId);
            ClassUtil.copyProperties(orgRoute, route);
            route = orgRoute;
            routeDao.save(route);

            //删除已有的发车时间
            List<RouteTime> timeList = routeTimeDao.findByRouteId(routeId);
            for (RouteTime rt : timeList) {
                routeTimeDao.delete(rt);
            }

            //如果重新上传了路线文件，则覆盖
            if (list != null) {
                //删除已有的路线站点
                List<RouteStation> stationList = routeStationDao.findByRouteId(routeId);
                for (RouteStation rs : stationList) {
                    routeStationDao.delete(rs);
                }
            }

            //删除已有的路线班车
            List<BusSend> sendList = busSendDao.findBus(routeId, 1);
            for (BusSend bs : sendList) {
                busSendDao.delete(bs);
            }

            routeId = route.getId();
        }

        //保存路线相关
        saveRouteOther(routeId, departTimes, list, busIds);

        //如果有往返
        if (isRoundTrip != null && isRoundTrip == 1) {

            if (StringUtils.isEmpty(backTimes)) {
                return new Result().failure(ErrorType.ERROR_CODE_00033);//返程时间不能为空
            }

            Route backRoute = new Route();
            ClassUtil.copyProperties(backRoute, route);
            backRoute.setId(null);
            routeDao.save(backRoute);

            //使list倒序，新增一条往返的路线
            Collections.reverse(list);
            saveRouteOther(backRoute.getId(), backTimes, list, busIds);
        }

        return result;
    }

    /**
     * 保存路线相关
     *
     * @param routeId
     * @param departTimes
     * @param list
     * @param busIds
     */
    private void saveRouteOther(Long routeId, String departTimes, List<Map> list, String busIds) {
        //路线时间点
        String[] departTimeArr = departTimes.split("\\,");
        for (String departTime : departTimeArr) {
            if (!StringUtils.isEmpty(departTime)) {
                RouteTime routeTime = new RouteTime();
                routeTime.setDepartTime(departTime);//发车时间
                routeTime.setRouteId(routeId);//对应路线
                routeTimeDao.save(routeTime);
            }
        }

        //路线站点
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);
                RouteStation routeStation = new RouteStation();
                double[] position = MathUtil.wgs2bd(Double.valueOf(map.get("lat").toString()), Double.valueOf(map.get("lon").toString()));
                routeStation.setLat(position[0]);
                routeStation.setLng(position[1]);
                routeStation.setStationName((String) map.get("name"));
                routeStation.setStationOrder(i);
                routeStation.setRouteId(routeId);

                Route r = routeDao.findOne(routeId);
                if (i == 0) {
                    r.setStartStation(routeStation.getStationName());
                } else if (i == (list.size() - 1)) {
                    r.setEndStation(routeStation.getStationName());
                }
                routeDao.save(r);

                routeStationDao.save(routeStation);
            }
        }

        //路线发车
        String[] busIdArr = busIds.split("\\,");
        for (String busId : busIdArr) {
            if (!StringUtils.isEmpty(busId)) {
                BusSend bs = new BusSend();
                bs.setBusId(Long.valueOf(busId));
                bs.setContactId(routeId);
                bs.setType(1);//班车
                busSendDao.save(bs);
            }
        }
    }

    /**
     * 删除路线
     *
     * @param routeId
     */
    @Override
    @Transactional
    public void deleteRoute(Long routeId) {

        //删除已有的发车时间
        List<RouteTime> timeList = routeTimeDao.findByRouteId(routeId);
        for (RouteTime rt : timeList) {
            if (rt != null) {
                routeTimeDao.delete(rt);
            }
        }

        //删除已有的路线站点
        List<RouteStation> stationList = routeStationDao.findByRouteId(routeId);
        for (RouteStation rs : stationList) {
            if (rs != null) {
                routeStationDao.delete(rs);
            }
        }

        //删除已有的路线班车
        List<BusSend> sendList = busSendDao.findBus(routeId, 1);
        for (BusSend bs : sendList) {
            if (bs != null) {
                busSendDao.delete(bs);
            }
        }

        //删除路线
        Route route = routeDao.findOne(routeId);
        if (route != null) {
            routeDao.delete(route);
        }
    }

    /**
     * 下订单
     *
     * @param routeId
     * @param departTime
     * @param user
     */
    @Override
    @Transactional
    public Result saveOrder(Long routeId, String departTime, UserInfo user) {
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
        routeOrder.setRouteId(routeId);
        routeOrder.setOrder(new Order(order.getId()));
        routeOrder.setDepartTime(departTime);
        routeOrder.setIsDel(0);
        if (route.getEnterpriseId() != null) {
            Enterprise enterprise = enterpriseDao.findOne(route.getEnterpriseId());
            routeOrder.setEnterpriseType(enterprise.getType());
        }
        routeOrderDao.save(routeOrder);

        return Result.success(routeOrder);
    }

    /**
     * 获取路线列表，若收藏，则放在前面
     *
     * @param startStation
     * @param endStation
     * @param type
     * @param userId
     * @return
     */
    @Override
    public List<Route> findList(String startStation, String endStation, Integer type, Long userId) {
        StringBuffer sql = new StringBuffer("SELECT \n" +
                "  r.* \n" +
                "FROM\n" +
                "  t_route r \n" +
                "  LEFT JOIN t_enterprise e \n" +
                "    ON e.`id` = r.`enterprise_id` \n" +
                "  LEFT JOIN t_route_collection rc \n" +
                "    ON rc.`route_id` = r.`id` \n" +
                "    AND rc.`user_id` = " + userId + " \n" +
                "WHERE e.`type` = " + type);
        if (StringUtils.isBlank(startStation) && StringUtils.isBlank(endStation)) {
            System.out.println();
        } else if (StringUtils.isNotBlank(startStation) && StringUtils.isNotBlank(endStation)) {
            sql.append("  AND r.id in (SELECT s.route_id FROM t_route_station s WHERE s.station_name LIKE '%" + startStation + "%' OR r.id in (SELECT s.route_id FROM t_route_station s WHERE s.station_name LIKE '%" + endStation + "%'))  ");
        } else {
            if (StringUtils.isNotBlank(startStation)) {
                sql.append("  AND r.id in (SELECT s.route_id FROM t_route_station s WHERE s.station_name LIKE '%" + startStation + "%')  ");
            }
            if (StringUtils.isNotBlank(endStation)) {
                sql.append("  AND r.id in (SELECT s.route_id FROM t_route_station s WHERE s.station_name LIKE '%" + endStation + "%')  ");
            }
        }
        //如果是通勤班车，且用户为员工，则只查看该员工所在企业的班车
        UserInfo user = userInfoDao.findOne(userId);
        if (type == 0 && user.getEnterprise() != null) {
            sql.append("  AND e.`id` = " + user.getEnterprise().getId());
        }
        sql.append(" ORDER BY IF(rc.`id` IS NULL, 0, 1) DESC, r.`line_name` ASC ");
        System.out.println("sql：" + sql);
        List<Route> list = queryBySql(sql.toString(), Route.class);
        return list;
    }

    /**
     * 获取当前线路的返程路线
     *
     * @param routeId
     * @return
     */
    @Override
    public Result findBackRoute(Long routeId) {
        Route route = routeDao.findOne(routeId);
        if (route == null) {
            return Result.failure();
        }
        Route backRoute = routeDao.findByLineNameAndId(route.getLineName(), route.getId());
        if (backRoute == null) {
            return Result.failure(ErrorType.ERROR_CODE_00035);//该路线没有返程路线
        }
        return Result.success(backRoute);
    }

}
