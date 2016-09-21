package com.leoman.wechat.bus.controller;

import com.leoman.bus.entity.Bus;
import com.leoman.bus.entity.Route;
import com.leoman.bus.entity.RouteStation;
import com.leoman.bus.entity.RouteTime;
import com.leoman.bus.service.*;
import com.leoman.bus.service.impl.RouteServiceImpl;
import com.leoman.bus.util.GpxUtil;
import com.leoman.bussend.entity.BusSend;
import com.leoman.bussend.service.BusSendService;
import com.leoman.common.controller.common.CommonController;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.service.Query;
import com.leoman.coupon.entity.SystemConfig;
import com.leoman.entity.Configue;
import com.leoman.entity.Constant;
import com.leoman.system.banner.entity.Banner;
import com.leoman.system.banner.service.BannerService;
import com.leoman.user.entity.UserInfo;
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
 * 微信端：班车路线
 * Created by Daisy on 2016/9/18.
 */
@Controller
@RequestMapping(value = "/wechat/route")
public class RouteWeChatController extends CommonController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteTimeService routeTimeService;

    @Autowired
    private RouteStationService routeStationService;

    @Autowired
    private BusSendService busSendService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private BusService busService;

    @Autowired
    private RouteCollectionService routeCollectionService;

    /**
     * 班车路线页面
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request,
                        HttpServletResponse response,
                        Model model,
                        Integer type) {
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
                       Route route, Integer type) {
        try {
            Query query = Query.forClass(Route.class, routeService);
            query.like("startStation",route.getStartStation());
            query.like("endStation",route.getEndStation());
            query.eq("enterprise.type",type);

            UserInfo user = getSessionUser(request);
            System.out.println("--------login user mobile--------"+user.getMobile());
            List<Route> list = routeService.queryAll(query);
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
                        Long routeId) {
        try {
            List<RouteStation> stationList = routeStationService.findByRouteId(routeId);
            List<RouteTime> timeList = routeTimeService.findByRouteId(routeId);
            List<BusSend> bsList = busSendService.findBus(routeId,1);//1--表示班车

            //设置当前班车所在站点
            handleBusCurStation(bsList,stationList);

            Map map = new HashMap();
            map.put("stationList",stationList);
            map.put("timeList",timeList);
            map.put("bsList",bsList);

            WebUtil.printJson(response,new Result().success(createMap("map", map)));
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 设置当前班车所在站点
     * @param bsList
     * @param stationList
     */
    private void handleBusCurStation(List<BusSend> bsList, List<RouteStation> stationList){
        for (BusSend bs:bsList) {
            Bus bus = bs.getBus();
            if(bus != null){
                Double curLat = bus.getCurLat();//当前纬度
                Double curLng = bus.getCurLng();//当前经度
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
                Long stationId = (Long) map.get(minDistance);
                bus.setStationId(stationId);
                busService.save(bus);
            }
        }
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
     * 收藏
     * @param request
     * @param response
     * @param routeId
     * @param isCollect
     * @return
     */
    @RequestMapping(value = "/collect")
    @ResponseBody
    public Result collect(HttpServletRequest request,
                        HttpServletResponse response,
                        Long routeId,
                          Boolean isCollect) {
        try {
            UserInfo user = super.getSessionUser(request);
            routeCollectionService.doCollect(routeId,user.getUserId(),isCollect);
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

}
