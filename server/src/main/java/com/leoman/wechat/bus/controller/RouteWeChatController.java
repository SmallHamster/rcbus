package com.leoman.wechat.bus.controller;

import com.leoman.bus.entity.Bus;
import com.leoman.bus.entity.Route;
import com.leoman.bus.entity.RouteStation;
import com.leoman.bus.entity.RouteTime;
import com.leoman.bus.service.*;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信端：班车路线
 * Created by Daisy on 2016/9/18.
 */
@Controller
@RequestMapping(value = "/wechat/route")
public class RouteWeChatController extends RouteBaseController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteTimeService routeTimeService;

    @Autowired
    private RouteStationService routeStationService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private BusService busService;

    /**
     * 班车路线页面
     */
    @RequestMapping(value = "/index/{type}")
    public String index(HttpServletRequest request,
                        HttpServletResponse response,
                        Model model,
                        @PathVariable("type") Integer type) {
        List<Banner> bannerList = bannerService.findList(type);
        for (Banner banner:bannerList) {
            if(banner.getImage() != null){
                banner.getImage().setPath(Configue.getUploadUrl()+banner.getImage().getPath());
            }
        }
        model.addAttribute("bannerList", bannerList);
        model.addAttribute("type",type);
        return "wechat/route_index";
    }

    /**
     * 获取所有路线
     * @param
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Result list(HttpServletRequest request,
                       HttpServletResponse response,
                       Route route,
                       Integer type,
                       Double userLat,
                       Double userLng) {
        try {
            Query query = Query.forClass(Route.class, routeService);
            query.like("startStation",route.getStartStation());
            query.like("endStation",route.getEndStation());
            query.eq("enterprise.type",type);

            List<Route> list = routeService.queryAll(query);

            UserInfo user = getSessionUser(request);
            Date d1 =  new Date();
            System.out.println("-------- 开始处理："+d1);
            //处理路线的最近车辆信息，收藏状态
            handleRoute(list, userLat, userLng, user.getId());
            Date d2 =  new Date();
            System.out.println("-------- 结束处理："+new Date());
            System.out.println("-------- 处理时间："+(d2.getTime() - d1.getTime()));

            WebUtil.printJson(response,new Result().success(createMap("list",list)));
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 获取该路线的信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail")
    public String bus(Model model,Long routeId) {
        model.addAttribute("routeId",routeId);
        return "wechat/route_detail";
    }

    /**
     * 获取详情页的其他相关数据，如：派遣车辆，路线站点，时间点
     * @param request
     * @param response
     * @param routeId
     * @return
     */
    @RequestMapping(value = "/other", method = RequestMethod.POST)
    @ResponseBody
    public Result stations(HttpServletRequest request,
                        HttpServletResponse response,
                        Long routeId,
                           Double userLat,
                           Double userLng) {
        try {
            List<RouteStation> stationList = routeStationService.findByRouteId(routeId);
            List<RouteTime> timeList = routeTimeService.findByRouteId(routeId);
            List<Bus> busList =  busService.findBusOrderByDistance(routeId,userLat,userLng);

            //设置当前班车所在站点
            super.handleBusCurStation(busList,stationList);

            Map map = new HashMap();
            map.put("stationList",stationList);
            map.put("timeList",timeList);
            map.put("busList",busList);

            WebUtil.printJson(response,new Result().success(createMap("map", map)));
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 获取路线的所有班车时间
     * @param request
     * @param response
     * @param routeId
     * @return
     */
    @RequestMapping(value = "/times", method = RequestMethod.POST)
    @ResponseBody
    public Result times(HttpServletRequest request,
                       HttpServletResponse response,
                       Long routeId) {
        try {
            List<RouteTime> timeList = routeTimeService.findByRouteId(routeId);
            WebUtil.printJson(response,new Result().success(createMap("timeList", timeList)));
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 跳转至订单页
     * @param model
     * @param routeId
     * @return
     */
    @RequestMapping(value = "/toOrder")
    public String toOrder(Model model,Long routeId) {
        model.addAttribute("routeId",routeId);
        return "wechat/route_order";
    }

    /**
     * 保存订单
     * @param request
     * @param response
     * @param routeId
     * @param departTime
     * @return
     */
    @RequestMapping(value = "/saveOrder")
    @ResponseBody
    public Result saveOrder(HttpServletRequest request,
                          HttpServletResponse response,
                          Long routeId,
                          String departTime) {
        try {
            UserInfo user = super.getSessionUser(request);
            routeService.saveOrder(routeId,departTime,user);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 跳转至百度地图页面
     * @param model
     * @param routeId
     * @return
     */
    @RequestMapping(value = "/toPosition")
    public String toPosition(Model model,Long routeId) {
        List<Bus> busList =  busService.findBusOrderByDistance(routeId,null,null);
        model.addAttribute("busList", JsonUtil.obj2Json(busList));
        model.addAttribute("routeId",routeId);
        return "wechat/bus_position";
    }

    /**
     * 位置页面获取路线和车辆当前位置
     * @param request
     * @param response
     * @param routeId
     * @return
     */
    @RequestMapping(value = "/findBus", method = RequestMethod.POST)
    @ResponseBody
    public Result findBus(HttpServletRequest request,
                           HttpServletResponse response,
                           Long routeId) {
        try {
            List<RouteStation> stationList = routeStationService.findByRouteId(routeId);
            List<Bus> busList =  busService.findBusOrderByDistance(routeId,null,null);
            Map map = new HashMap();
            map.put("stationList",stationList);
            map.put("busList",busList);

            WebUtil.printJson(response,new Result().success(createMap("map", map)));
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

}
