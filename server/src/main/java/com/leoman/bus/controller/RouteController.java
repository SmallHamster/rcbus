package com.leoman.bus.controller;

import com.leoman.bus.entity.Route;
import com.leoman.bus.service.RouteService;
import com.leoman.bus.service.impl.RouteServiceImpl;
import com.leoman.bussend.entity.BusSend;
import com.leoman.bussend.service.BusSendService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.order.entity.Order;
import com.leoman.order.service.OrderService;
import com.leoman.system.enterprise.entity.Enterprise;
import com.leoman.system.enterprise.service.EnterpriseService;
import com.leoman.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 路线
 * Created by Daisy on 2016/9/7.
 */
@Controller
@RequestMapping(value = "/admin/route")
public class RouteController extends GenericEntityController<Route, Route, RouteServiceImpl> {

    @Autowired
    private RouteService routeService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private BusSendService busSendService;

    @Autowired
    private OrderService orderService;

    /**
     * 列表页面
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        //获取所有企业
        List<Enterprise> enterpriseList = enterpriseService.queryAll();

        model.addAttribute("enterpriseList", enterpriseList);
        return "route/route_list";
    }

    /**
     * 获取列表
     * @param route
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(Route route,Long enterpriseId, Integer draw, Integer start, Integer length) {
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(Route.class, routeService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        query.like("startStation",route.getStartStation());
        query.like("endStation",route.getEndStation());
        if(enterpriseId != null && enterpriseId != 0){
            Enterprise enterprise = new Enterprise();
            enterprise.setId(enterpriseId);
            query.eq("enterprise",enterprise);
        }
        Page<Route> page = routeService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    /**
     * 添加车辆
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Long id, Model model) {
        if (id != null) {
            Route route = routeService.queryByPK(id);

            model.addAttribute("route", route);//路线
            model.addAttribute("timeJson", JsonUtil.obj2Json(route.getTimes()));//路线时间

            StringBuffer busIds = new StringBuffer();
            List<BusSend> bsList = busSendService.findBus(route.getId(),1);
            for (BusSend bs:bsList) {
                Long busId = bs.getBus().getId();
                busIds.append(busId+",");
            }
            model.addAttribute("busIds",busIds.toString().substring(0,busIds.toString().length()-1));

        }
        //获取所有企业
        List<Enterprise> enterpriseList = enterpriseService.queryAll();
        model.addAttribute("enterpriseList", enterpriseList);
        return "route/route_add";
    }

    /**
     * 保存
     * @param route
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Route route, String departTimes, String backTimes, String busIds) {
        try {
            routeService.saveRoute(route,departTimes,backTimes,busIds);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String info(Long id, Model model) {
        if (id != null) {
            Route route = routeService.queryByPK(id);
            model.addAttribute("route", route);//路线
            model.addAttribute("timeJson", JsonUtil.obj2Json(route.getTimes()));//路线时间
        }
        return "route/route_info";
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(String ids) {
        try {
            String [] idArr = ids.split("\\,");
            for (String id:idArr) {
                Integer routeId = Integer.valueOf(id);
                routeService.deleteByPK(routeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 派遣
     * @param ids
     * @return
     */
    @RequestMapping(value = "/dispatch", method = RequestMethod.POST)
    @ResponseBody
    public Result dispatch(String ids) {
        try {
            String [] idArr = ids.split("\\,");
            for (String id:idArr) {
                Integer routeId = Integer.valueOf(id);
                routeService.deleteByPK(routeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    @RequestMapping(value = "/updateIsShow", method = RequestMethod.POST)
    @ResponseBody
    public Result updateIsShow(Route route) {
        try {
            routeService.update(route);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    @RequestMapping(value = "/order/index")
    public String orderIndex(Model model) {
        //获取所有企业
        List<Enterprise> enterpriseList = enterpriseService.queryAll();

        model.addAttribute("enterpriseList", enterpriseList);
        return "route/route_order_list";
    }

    @RequestMapping(value = "/order/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(Route route, Integer draw, Integer start, Integer length) {
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(Route.class, routeService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        query.eq("type",1);//班车订单
//        query.like("startStation",route.getStartStation());
//        query.like("endStation",route.getEndStation());
        Page<Order> page = orderService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    @ResponseBody
    public Result saveOrder(Route route, Long timeId) {
        try {
            routeService.saveOrder(route, timeId);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

}
