package com.leoman.wechat.bus.controller;

import com.leoman.bus.entity.Route;
import com.leoman.bus.entity.RouteTime;
import com.leoman.bus.service.EnterpriseApplyService;
import com.leoman.bus.service.RouteService;
import com.leoman.bus.service.RouteTimeService;
import com.leoman.bus.service.impl.RouteServiceImpl;
import com.leoman.common.controller.common.CommonController;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.system.banner.service.BannerService;
import com.leoman.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    private BannerService bannerService;

    /**
     * 班车路线页面
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
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
                       Route route) {
        try {
            List<Route> list = routeService.queryAll();
            WebUtil.printJson(response,new Result().success(createMap("list", list)));
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
    public String bus(Model model) {
        return "wechat/route_detail";
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

}
