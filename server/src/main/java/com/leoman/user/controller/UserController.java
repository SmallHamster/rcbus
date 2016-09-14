package com.leoman.user.controller;

import com.leoman.bus.entity.RouteOrder;
import com.leoman.bus.service.RouteOrderService;
import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.order.entity.Order;
import com.leoman.order.service.OrderService;
import com.leoman.pay.util.MD5Util;
import com.leoman.system.enterprise.entity.Enterprise;
import com.leoman.system.enterprise.service.EnterpriseService;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserLogin;
import com.leoman.user.service.UserLoginService;
import com.leoman.user.service.UserService;
import com.leoman.user.service.impl.UserServiceImpl;
import com.leoman.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 会员
 * Created by 史龙 on 2016/6/14 0014.
 */
@Controller
@RequestMapping(value = "admin/user")
public class UserController extends GenericEntityController<UserInfo, UserInfo, UserServiceImpl> {

    @Autowired
    private UserService userService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CarRentalService carRentalService;
    @Autowired
    private RouteOrderService routeOrderService;

    @RequestMapping(value = "/index")
    public String index(Model model){
        model.addAttribute("enterprise",enterpriseService.queryAll());
        return "user/list";
    }

    /**
     * 列表
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(Integer draw, Integer start, Integer length,UserInfo userInfo,Long enterpriseId) {
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(UserInfo.class, userService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        query.like("mobile",userInfo.getMobile());
        query.eq("enterprise.id",enterpriseId);
        query.eq("type",userInfo.getType());
        Page<UserInfo> page = userService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    /**
     * 跳转新增页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Long id, Model model){
//        if(id != null){
//            model.addAttribute("user",userService.queryByPK(id));
//        }
        model.addAttribute("enterprise",enterpriseService.queryAll());
        return "user/add";
    }


    /**
     * 保存
     * @param userInfo
     * @param id
     * @param enterpriseId
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(UserInfo userInfo, Long id, @RequestParam(value = "enterpriseId",required = false) Long enterpriseId) {
        return userService.save(userInfo,id,enterpriseId);
    }

    /**
     * 详情页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail")
    public String detail(Long id,Model model){
        UserInfo userInfo = userService.queryByPK(id);
        model.addAttribute("userInfo",userInfo);

        List<CarRental> carRentals = carRentalService.findList(id);
        model.addAttribute("carRentals",carRentals);

        List<RouteOrder> routeOrders = routeOrderService.findList(id);
        model.addAttribute("routeOrders",routeOrders);

        return "user/detail";
    }

    /**
     * 删除
     * @param id
     * @param ids
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Integer del(Long id,String ids) {
        return userService.del(id,ids);
    }

    @RequestMapping(value = "/departure",method = RequestMethod.POST)
    @ResponseBody
    public Integer departure(Long id){
        if(id==null) return 1;

        try{

            UserInfo userInfo = userService.queryByPK(id);
            if(userInfo.getType()==1){
                userInfo.setType(2);
                userInfo.setEnterprise(null);
                userService.save(userInfo);
            }else {
                return 2;
            }

        }catch (Exception e){
            e.printStackTrace();
            return 1;
        }
        return 0;

    }



}
