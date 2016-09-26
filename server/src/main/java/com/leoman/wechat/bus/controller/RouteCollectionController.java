package com.leoman.wechat.bus.controller;

import com.leoman.bus.entity.Route;
import com.leoman.bus.service.RouteCollectionService;
import com.leoman.common.controller.common.CommonController;
import com.leoman.common.core.Result;
import com.leoman.entity.Configue;
import com.leoman.system.banner.entity.Banner;
import com.leoman.user.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 微信端：路线收藏
 * Created by Daisy on 2016/9/23.
 */
@Controller
@RequestMapping(value = "/wechat/route/collect")
public class RouteCollectionController extends RouteBaseController {

    @Autowired
    private RouteCollectionService routeCollectionService;

    /**
     * 班车路线页面
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request,
                        HttpServletResponse response,
                        Model model) {

        return "wechat/personal_collection";
    }

    /**
     * 获取收藏列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Result list(HttpServletRequest request,
                          HttpServletResponse response,
                       Double userLat,
                       Double userLng) {
        Result result = Result.success();
        try {
            UserInfo user = getSessionUser(request);
            if(user != null){
                List<Route> routeList = routeCollectionService.findByUser(user.getId());

                super.handleRoute(routeList, userLat, userLng, 0l);

                result = new Result().success(createMap("routeList",routeList));
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = Result.failure();
        }
        return result;
    }

    /**
     * 收藏
     * @param request
     * @param response
     * @param routeId
     * @param isCollect
     * @return
     */
    @RequestMapping(value = "/oper")
    @ResponseBody
    public Result collect(HttpServletRequest request,
                        HttpServletResponse response,
                        Long routeId,
                          Boolean isCollect) {
        try {
            UserInfo user = super.getSessionUser(request);
            routeCollectionService.doCollect(routeId,user.getId(),isCollect);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 删除多个收藏
     * @param request
     * @param response
     * @param routeIds
     * @return
     */
    @RequestMapping(value = "/multiDel")
    @ResponseBody
    public Result multiDel(HttpServletRequest request,
                          HttpServletResponse response,
                          String routeIds) {
        try {
            UserInfo user = getSessionUser(request);
            routeCollectionService.multiDel(routeIds,user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

}
