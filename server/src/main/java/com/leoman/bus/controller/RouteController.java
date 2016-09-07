package com.leoman.route.controller;

import com.leoman.bus.entity.Route;
import com.leoman.bus.service.RouteService;
import com.leoman.bus.service.impl.RouteServiceImpl;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.entity.Configue;
import com.leoman.system.enterprise.entity.Enterprise;
import com.leoman.system.enterprise.service.EnterpriseService;
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

    /**
     * 列表页面
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        return "route/route_list";
    }

    /**
     * 获取列表
     * @param username
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(String username, Integer draw, Integer start, Integer length) {
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(Route.class, routeService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
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
            model.addAttribute("route", route);
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
    public Result save(Route route) {
        try {
            routeService.save(route);
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
            model.addAttribute("route", routeService.queryByPK(id));
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

}
