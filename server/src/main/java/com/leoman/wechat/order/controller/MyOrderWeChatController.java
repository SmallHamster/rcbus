package com.leoman.wechat.order.controller;

import com.leoman.bus.entity.RouteOrder;
import com.leoman.bus.service.RouteOrderService;
import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.order.entity.Order;
import com.leoman.order.service.OrderService;
import com.leoman.order.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 我的订单
 * Created by 史龙 on 2016/9/19.
 */
@RequestMapping(value = "/wechat/order")
@Controller
public class MyOrderWeChatController extends GenericEntityController<Order,Order,OrderServiceImpl> {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CarRentalService carRentalService;
    @Autowired
    private RouteOrderService routeOrderService;


    @RequestMapping(value = "/myOrder/index")
    public String index(Model model){
        //租车
        model.addAttribute("CarRentalList",carRentalService.findList(1L));
        return "wechat/my_order";
    }


    @RequestMapping(value = "/detail")
    public String detail(Model model,Long id,Integer status){
        //租车
        if(id!=null){
            model.addAttribute("CarRental",carRentalService.queryByPK(id));
        }
        return "wechat/orderdetail/order_detail_status0";
    }

}
