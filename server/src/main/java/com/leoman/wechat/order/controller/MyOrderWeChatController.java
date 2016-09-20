package com.leoman.wechat.order.controller;

import com.leoman.bus.entity.RouteOrder;
import com.leoman.bus.service.RouteOrderService;
import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.service.CarRentalOfferService;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.common.controller.common.CommonController;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.coupon.entity.Coupon;
import com.leoman.coupon.service.CouponService;
import com.leoman.order.entity.Order;
import com.leoman.order.service.OrderService;
import com.leoman.order.service.impl.OrderServiceImpl;
import com.leoman.user.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private CarRentalOfferService carRentalOfferService;
    @Autowired
    private CouponService couponService;


    @RequestMapping(value = "/myOrder/index")
    public String index(Model model, HttpServletRequest request){
        //租车
        model.addAttribute("CarRentalList",carRentalService.findList(new CommonController().getSessionUser(request).getId()));
        return "wechat/my_order";
    }


    @RequestMapping(value = "/detail")
    public String detail(Model model,Long id,Integer status,HttpServletRequest request){
        CarRental carRental = new CarRental();
        //租车
        if(id!=null){
            model.addAttribute("CarRental",carRentalService.queryByPK(id));
        }
        if(status==0){
            return "wechat/orderdetail/order_detail_status0";
        }else if(status==1){
            //分类收费
            model.addAttribute("carRentalOffer",carRentalOfferService.queryByProperty("rentalId",id));
            //优惠券
            model.addAttribute("coupon",couponService.findList(new CommonController().getSessionUser(request).getId()));
            return "wechat/orderdetail/order_detail_status1";
        }else if(status==2){
            //分类收费
            model.addAttribute("carRentalOffer",carRentalOfferService.queryByProperty("rentalId",id));
            //优惠券
            model.addAttribute("coupon",couponService.findOne((new CommonController().getSessionUser(request).getId()),id));
            return "wechat/orderdetail/order_detail_status2";
        }else {
            return null;
        }

    }

    @RequestMapping(value = "/cancel")
    public Result cancel(Long id){
        try{
            CarRental carRental = carRentalService.queryByPK(id);
            Order order = carRental.getOrder();
            //取消订单
            order.setStatus(4);
            orderService.save(order);
        }catch (Exception e){
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }
}
