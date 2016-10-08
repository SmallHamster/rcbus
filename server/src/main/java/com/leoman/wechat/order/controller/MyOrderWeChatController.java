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
import com.leoman.entity.Constant;
import com.leoman.order.entity.Order;
import com.leoman.order.service.OrderService;
import com.leoman.order.service.impl.OrderServiceImpl;
import com.leoman.user.entity.CouponOrder;
import com.leoman.user.entity.UserCoupon;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.CouponOrderService;
import com.leoman.user.service.UserCouponService;
import com.leoman.utils.DateUtils;
import com.leoman.utils.HttpRequestUtil;
import com.leoman.utils.JsonUtil;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

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
    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;
    @Autowired
    private CouponOrderService couponOrderService;
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
        UserInfo userInfo = new CommonController().getSessionUser(request);
        if(id!=null){
            carRental = carRentalService.queryByPK(id);
            model.addAttribute("CarRental",carRental);
            //分类收费
            model.addAttribute("carRentalOffer",carRentalOfferService.queryByProperty("rentalId",id));
            //优惠券
            List<UserCoupon> userCoupons = userCouponService.findList(userInfo.getId());
            List<UserCoupon> userCoupons1 = new ArrayList<>();
            if(!userCoupons.isEmpty() && userCoupons.size()>0){
                for(UserCoupon userCoupon : userCoupons){
                    //有效期
                    if(userCoupon.getCoupon().getValidDateFrom()<System.currentTimeMillis() && userCoupon.getCoupon().getValidDateTo()>System.currentTimeMillis()){
                        userCoupons1.add(userCoupon);
                    }
                }
            }
            model.addAttribute("userCoupon",userCoupons1);
            //用户使用的优惠券
            model.addAttribute("myCoupon",userCouponService.findOne(userInfo.getId(),id));
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
            // 生成时间戳
            String timestamp = System.currentTimeMillis() + "";
            timestamp = timestamp.substring(0, 10);

            // 生成随机字符串
            String noncestr = String.valueOf(System.currentTimeMillis() / 1000);

            // 生成签名
            String signature = getSignature(request, noncestr, timestamp, "http://27298829.ittun.com/rcbus/wechat/order/myOrder/detail?id=" + id + "&status=" + status);
            System.out.println("signature:==================" + signature);
            model.addAttribute("timestamp", timestamp);
            model.addAttribute("noncestr", noncestr);
            model.addAttribute("signature", signature);

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

            HttpSession session = request.getSession();
            session.setAttribute("couponId",couponId);

            //支付金额
            CarRental carRental = carRentalService.queryByPK(id);
            carRental.setIncome(Double.parseDouble(StringUtils.isNotBlank(price) ? price : "0.0"));
            carRentalService.save(carRental);

// (付款完成)
//            Order order = carRental.getOrder();
//            //改变状态进行中
//            order.setStatus(2);
//            orderService.save(order);

            //改变优惠券状态为已使用
//            List<UserCoupon> userCoupons = userCouponService.findList(userInfo.getId(),couponId);
//            if(!userCoupons.isEmpty() && userCoupons.size()>0){
//                UserCoupon userCoupon = userCoupons.get(0);
//                userCoupon.setIsUse(2);
//                userCoupon.setRentalId(carRental.getId());
//                userCouponService.save(userCoupon);
//            }
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
                carRental.setStartDate(DateUtils.stringToLong(time1,"yyyy-MM-dd HH:mm"));
            }
            if(StringUtils.isNotBlank(time2)){
                carRental.setEndDate(DateUtils.stringToLong(time2,"yyyy-MM-dd HH:mm"));
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
    public Result cancelSave(String unsubscribe,Long id,Double val,HttpServletRequest request){
        try{
            CarRental carRental = carRentalService.queryByPK(id);
            Order order = carRental.getOrder();

            //取消订单
            order.setStatus(4);
            orderService.save(order);
            carRental.setUnsubscribe(StringUtils.isNotBlank(unsubscribe) ? unsubscribe : "");

            //退款金额
            carRental.setRefund(val!=null ? val : 0.0);
            carRentalService.save(carRental);

            //退优惠券
            UserInfo userInfo = new CommonController().getSessionUser(request);
            UserCoupon userCoupon = userCouponService.findOne(userInfo.getId(),id);
            if(userCoupon!=null){
                userCoupon.setIsUse(1);
                userCoupon.setRentalId(0L);
                userCouponService.save(userCoupon);
            }

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

            Coupon _c = new Coupon();
            List<Coupon> coupons =  couponService.queryAll();
            for(Coupon coupon : coupons){
                //2-订单完成
                if(coupon.getGainWay()==2){
                    _c = coupon;
                }
            }

            //新增一条用户优惠券
            UserCoupon userCoupon = new UserCoupon();
            CouponOrder couponOrder = new CouponOrder();

            //快照
            couponOrder.setName(_c.getName());
            couponOrder.setGainWay(_c.getGainWay());
            couponOrder.setCouponWay(_c.getCouponWay());
            couponOrder.setValidDateFrom(_c.getValidDateFrom());
            couponOrder.setValidDateTo(_c.getValidDateTo());
            couponOrder.setDiscountPercent(_c.getDiscountPercent());
            couponOrder.setDiscountTopMoney(_c.getDiscountTopMoney());
            couponOrder.setReduceMoney(_c.getReduceMoney());
            couponOrder.setIsLimit(_c.getIsLimit());
            couponOrder.setLimitMoney(_c.getLimitMoney());
            couponOrderService.save(couponOrder);

            userCoupon.setUserId(order.getUserInfo().getId());

            userCoupon.setCoupon(couponOrder);
            userCoupon.setIsUse(1);
            userCouponService.save(userCoupon);

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


    /**
     * 评价
     * @param id
     * @param driverService
     * @param busEnvironment
     * @param safeDriving
     * @param arriveTime
     * @param type
     * @return
     */
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


    public String getSignature(HttpServletRequest request, String noncestr, String timestamp, String url) {
        try {
            String ticket = (String) request.getSession().getAttribute(Constant.jsApi_ticket);

            System.out.println("ticket：" + ticket);

            if (null != ticket && !ticket.equals("")) {
                return getSignature(ticket, timestamp, noncestr, url);
            }

            String accesstoken = wxMpConfigStorage.getAccessToken();
            String urljson = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accesstoken + "&type=jsapi";

            String result = HttpRequestUtil.sendGet(urljson);

            JSONObject str = JSONObject.fromObject(result);

            String jsApi_ticket = str.get("ticket").toString();

            System.out.println("jsApi_ticket：" + jsApi_ticket);

            request.getSession().setAttribute(Constant.jsApi_ticket, jsApi_ticket);

            System.out.println("accessToken：" + accesstoken);
            System.out.println("urljson：" + urljson);
            System.out.println("jsApi_ticket：" + jsApi_ticket);

            return getSignature(jsApi_ticket, timestamp, noncestr, url);
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }

    // 获得js signature
    public String getSignature(String jsapi_ticket, String timestamp, String nonce, String jsurl) throws IOException {
        /****
         * 对 jsapi_ticket、 timestamp 和 nonce 按字典排序 对所有待签名参数按照字段名的 ASCII
         * 码从小到大排序（字典序）后，使用 URL 键值对的格式（即key1=value1&key2=value2…）拼接成字符串
         * string1。这里需要注意的是所有参数名均为小写字符。 接下来对 string1 作 sha1 加密，字段名和字段值都采用原始值，不进行
         * URL 转义。即 signature=sha1(string1)。
         * **如果没有按照生成的key1=value&key2=value拼接的话会报错
         */
        String[] paramArr = new String[]{"jsapi_ticket=" + jsapi_ticket,
                "timestamp=" + timestamp, "noncestr=" + nonce, "url=" + jsurl};
        Arrays.sort(paramArr);
        // 将排序后的结果拼接成一个字符串
        String content = paramArr[0].concat("&" + paramArr[1]).concat("&" + paramArr[2])
                .concat("&" + paramArr[3]);
        System.out.println("拼接之后的content为:" + content);
        String gensignature = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 对拼接后的字符串进行 sha1 加密
            byte[] digest = md.digest(content.toString().getBytes());
            gensignature = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将 sha1 加密后的字符串与 signature 进行对比
        if (gensignature != null) {
            return gensignature.toLowerCase();// 返回signature
        } else {
            return "false";
        }
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
}
