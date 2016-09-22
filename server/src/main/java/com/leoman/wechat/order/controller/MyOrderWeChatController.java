package com.leoman.wechat.order.controller;

import com.leoman.bus.entity.RouteOrder;
import com.leoman.bus.service.RouteOrderService;
import com.leoman.bussend.entity.BusSend;
import com.leoman.bussend.service.BusSendService;
import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.entity.CarRentalOffer;
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
import com.leoman.user.entity.UserCoupon;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserCouponService;
import com.leoman.utils.DateUtils;
import com.leoman.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private BusSendService busSendService;


    /**
     * 我的订单
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/myOrder/index")
    public String myOrderIndex(Model model, HttpServletRequest request) throws ParseException {
        UserInfo userInfo = new CommonController().getSessionUser(request);
        List<RouteOrder> routeOrders = routeOrderService.findList(userInfo.getId());
        List<RouteOrder> routeOrderList = new ArrayList<>();
        model.addAttribute("carRentalList",carRentalService.findList(userInfo.getId()));
        for(RouteOrder routeOrder : routeOrders){
            String time = DateUtils.longToString(routeOrder.getOrder().getCreateDate(),"yyyy-MM-dd")+" "+routeOrder.getDepartTime();
            if(System.currentTimeMillis()<DateUtils.stringToLong(time,"yyyy-MM-dd HH:mm")){
                routeOrderList.add(routeOrder);
            }
        }
        model.addAttribute("routeOrderList",routeOrderList);

        return "wechat/my_order";
    }

    /**
     * 我的行程
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/myTrip/index")
    public String myTripIndex(Model model, HttpServletRequest request) throws ParseException {
        UserInfo userInfo = new CommonController().getSessionUser(request);
        List<RouteOrder> routeOrders = routeOrderService.findList(userInfo.getId());
        List<RouteOrder> routeOrderList = new ArrayList<>();
        model.addAttribute("carRentalList",carRentalService.findList(userInfo.getId()));
        for(RouteOrder routeOrder : routeOrders){
            String time = DateUtils.longToString(routeOrder.getOrder().getCreateDate(),"yyyy-MM-dd")+" "+routeOrder.getDepartTime();
            if(System.currentTimeMillis()>=DateUtils.stringToLong(time,"yyyy-MM-dd HH:mm")){
                routeOrderList.add(routeOrder);
            }
        }
        model.addAttribute("routeOrderList",routeOrderList);
        return "wechat/my_trip";
    }


    /**
     * 租车订单详情
     * @param model
     * @param id
     * @param status
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/myOrder/detail")
    public String myOrderDetail(Model model,Long id,Integer status,HttpServletRequest request) throws ParseException {
        CarRental carRental = new CarRental();
        if(id!=null){
            carRental = carRentalService.queryByPK(id);
            model.addAttribute("CarRental",carRental);
            //分类收费
            model.addAttribute("carRentalOffer",carRentalOfferService.queryByProperty("rentalId",id));
            //优惠券
            model.addAttribute("coupon",couponService.findList(new CommonController().getSessionUser(request).getId()));
            //用户使用的优惠券
            model.addAttribute("myCoupon",couponService.findOne((new CommonController().getSessionUser(request).getId()),id));
            //用户的租车
            model.addAttribute("busSend",busSendService.findBus(id,2));

            //当前时间
            model.addAttribute("toDayDate",System.currentTimeMillis());

        }

        if(status==0){
            //审核中
            return "wechat/orderdetail/order_detail_status0";
        }else if(status==1){
            //待付款
            return "wechat/orderdetail/order_detail_status1";
        }else if(status==2){
            if(!busSendService.findBus(id,2).isEmpty() && busSendService.findBus(id,2).size()>0){
                model.addAttribute("modelNo",busSendService.findBus(id,2).get(0).getBus().getModelNo());
            }
            //进行中
            return "wechat/orderdetail/order_detail_status2";
        }else if(status==3){
            //已结束
            return "wechat/orderdetail/order_detail_status3";
        }else {
            //已取消
            return "wechat/orderdetail/order_detail_status4";
        }

    }

    /**
     * 班车详情
     * @param model
     * @param id
     * @param status
     * @param request
     * @return
     */
    @RequestMapping(value = "/myRoute/detail")
    public String myRouteDetail(Model model,Long id,Integer status,HttpServletRequest request){
        RouteOrder routeOrder = new RouteOrder();
        if(id!=null){
            routeOrder = routeOrderService.queryByPK(id);
        }
        model.addAttribute("routeOrder",routeOrder);
        if(status==0){
            //未发车
            return "wechat/orderdetail/route_detail_status0";
        }else {
            //已结束
            return "wechat/orderdetail/route_detail_status1";
        }

    }


    /**
     * 改签or退订页面
     * @param id
     * @param model
     * @param type
     * @param val
     * @return
     */
    @RequestMapping(value = "/rewrite")
    public String rewrite(Long id,Model model,Integer type,Double val) {
        model.addAttribute("val",val);
        if(id!=null){

            CarRental carRental = carRentalService.queryByPK(id);
            model.addAttribute("CarRental",carRental);

            //用户的租车
            List<BusSend> busSendList = busSendService.findBus(id,2);
            model.addAttribute("busSend",busSendList);
            if(!busSendList.isEmpty() && busSendList.size()>0){
                model.addAttribute("modelNo",busSendList.get(0).getBus().getModelNo());
            }

            Integer index ;
            //时间判断
            //2天前
            Long twoDay = carRental.getStartDate() - (60*60*24*2*1000);
            //1天前
            Long oneDay = carRental.getStartDate() - (60*60*24*1000);
            //5小时前
            Long fiveHours = carRental.getStartDate() - (60*60*5*1000);
            //当前时间
            Long toDayDate = System.currentTimeMillis();

            if(toDayDate <= twoDay){
                index = 1;
            }else if(toDayDate <= oneDay){
                index = 2;
            }else if(toDayDate <= fiveHours){
                index = 3;
            }else {
                index = 4;
            }
            model.addAttribute("index",index);
        }
        if(type==1){
            return "wechat/order_rewrite";
        }else {
            return "wechat/order_cancel";
        }
    }

    /**
     * 付款
     * @param id
     * @param price
     * @param couponId
     * @return
     */
    @RequestMapping(value = "/pay/save")
    @ResponseBody
    public Result pay(Long id,String price,Long couponId,HttpServletRequest request){
        UserInfo userInfo = new CommonController().getSessionUser(request);
        try{
            CarRental carRental = carRentalService.queryByPK(id);
            Order order = carRental.getOrder();
            //改变状态进行中
            order.setStatus(2);
            orderService.save(order);
            carRental.setIncome(Double.parseDouble(StringUtils.isNotBlank(price) ? price : "0.0"));
            carRentalService.save(carRental);
            //改变优惠券状态为已使用
            List<UserCoupon> userCoupons = userCouponService.findList(userInfo.getId(),couponId);
            if(!userCoupons.isEmpty() && userCoupons.size()>0){
                UserCoupon userCoupon = userCoupons.get(0);
                userCoupon.setIsUse(2);
                userCoupon.setRentalId(carRental.getId());
                userCouponService.save(userCoupon);
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }

    /**
     * 确定改签
     * @param time1
     * @param time2
     * @param id
     * @return
     */
    @RequestMapping(value = "/rewrite/save")
    @ResponseBody
    public Result rewriteSave(String time1,String time2,Long id){
        try{
            CarRental carRental = carRentalService.queryByPK(id);
            if(StringUtils.isNotBlank(time1)){
                carRental.setStartDate(DateUtils.stringToLong(time1,"yyyy-MM-dd hh:mm"));
            }
            if(StringUtils.isNotBlank(time2)){
                carRental.setEndDate(DateUtils.stringToLong(time2,"yyyy-MM-dd hh:mm"));
            }
            //已经改签过了
            carRental.setIsRewrite(1);
            carRentalService.save(carRental);
        }catch (Exception e){
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 确定退订
     * @param unsubscribe
     * @param id
     * @return
     */
    @RequestMapping(value = "/cancel/save")
    @ResponseBody
    public Result cancelSave(String unsubscribe,Long id,Double val){
        try{
            CarRental carRental = carRentalService.queryByPK(id);
            Order order = carRental.getOrder();
            //取消订单
            order.setStatus(4);
            orderService.save(order);
            carRental.setUnsubscribe(StringUtils.isNotBlank(unsubscribe) ? unsubscribe : "");
            //剩余收到金额
            carRental.setIncome(carRental.getIncome() - (val!=null ? val : 0.0));
            //退款金额
            carRental.setRefund(val!=null ? val : 0.0);
            carRentalService.save(carRental);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }

    /**
     * 确认完成
     * @param id
     * @return
     */
    @RequestMapping(value = "/complete/save")
    @ResponseBody
    public Result completeSave(Long id){
        try{
            CarRental carRental = carRentalService.queryByPK(id);
            Order order = carRental.getOrder();
            //完成订单
            order.setStatus(3);
            orderService.save(order);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }


    /**
     * 删除订单
     * @param ro_ids
     * @param cr_ids
     * @return
     */
    @RequestMapping(value = "/del")
    @ResponseBody
    public Result del(String ro_ids,String cr_ids){
        try {
            carRentalService.del(ro_ids,cr_ids);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }


    @RequestMapping(value = "/evaluation")
    @ResponseBody
    public Result evaluation(Long id,Integer driverService,Integer busEnvironment,Integer safeDriving,Integer arriveTime,Integer type){
        Order order = null;
        try{
            if(type==1){
                CarRental carRental = carRentalService.queryByPK(id);
                order = carRental.getOrder();
            }else {
                RouteOrder routeOrder = routeOrderService.queryByPK(id);
                order = routeOrder.getOrder();
            }
            if(order!=null){
                order.setDriverService(driverService);
                order.setBusEnvironment(busEnvironment);
                order.setSafeDriving(safeDriving);
                order.setArriveTime(arriveTime);
                //是否评价 1:是
                order.setIsComment(1);
                //评价时间
                order.setCommentTime(System.currentTimeMillis());
                orderService.save(order);
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure();
        }
        return  Result.success();
    }
}
