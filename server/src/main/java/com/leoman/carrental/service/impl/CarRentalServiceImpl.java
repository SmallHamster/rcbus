package com.leoman.carrental.service.impl;

import com.leoman.bus.entity.RouteOrder;
import com.leoman.bus.service.BusService;
import com.leoman.bus.service.CarTypeService;
import com.leoman.bus.service.RouteOrderService;
import com.leoman.bussend.entity.BusSend;
import com.leoman.bussend.service.BusSendService;
import com.leoman.carrental.dao.CarRentalDao;
import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.entity.CarRentalOffer;
import com.leoman.carrental.service.CarRentalOfferService;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.city.service.CityService;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.order.entity.Order;
import com.leoman.order.service.OrderService;
import com.leoman.user.entity.UserInfo;
import com.leoman.utils.*;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

/**
 *
 * Created by 史龙 on 2016/9/7.
 */
@Service
public class CarRentalServiceImpl extends GenericManagerImpl<CarRental,CarRentalDao> implements CarRentalService {

    @Autowired
    private CarRentalDao carRentalDao;
    @Autowired
    private CarRentalOfferService carRentalOfferService;
    @Autowired
    private BusService busService;
    @Autowired
    private BusSendService busSendService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CarTypeService carTypeService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RouteOrderService routeOrderService;
    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;


    @Override
    public List<Map<String, Object>> pageToExcel(List<CarRental> carRentals) throws ParseException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> params = null;
        List<CarRental> baseList = null;
//        baseList = queryAll();
        Set set = null;
        Iterator iterator = null;
        Map.Entry tempMap = null;
        Double sumTotalAmount = 0.0;
        Double sumIncome = 0.0;
        Double sumRefund = 0.0;

        if(carRentals != null){
            for (CarRental carRental : carRentals) {
                params = new HashMap<String, Object>();
                params.put("orderNo", carRental.getOrder().getOrderNo());
                params.put("startDate", DateUtils.longToString(carRental.getStartDate(),"yyyy-MM-dd HH:mm"));
                params.put("userName", carRental.getOrder().getUserName());
                params.put("mobile", carRental.getOrder().getMobile());
                params.put("totalAmount", carRental.getTotalAmount());
                params.put("income", carRental.getIncome());
                params.put("refund", carRental.getRefund());

                sumTotalAmount += carRental.getTotalAmount();
                sumIncome += carRental.getIncome();
                sumRefund += carRental.getRefund();

                // 去掉null值
                set = params.entrySet();
                iterator = set.iterator();
                while (iterator.hasNext()) {
                    tempMap = (Map.Entry) iterator.next();
                    params.put(tempMap.getKey().toString(), null == tempMap.getValue() ? "" : tempMap.getValue());
                }

                list.add(params);
            }

            //加最后一行
            params = new HashMap<String, Object>();
            params.put("orderNo", "");
            params.put("startDate", "");
            params.put("userName", "");
            params.put("mobile", "");
            params.put("busNum", "");

            DecimalFormat df =new DecimalFormat("#.##");
            params.put("totalAmount", "总和 : " + df.format(sumTotalAmount));
            params.put("income", "总和 : " + df.format(sumIncome));
            params.put("refund", "总和 : " + df.format(sumRefund));

            list.add(params);
        }

        return list;
    }

    /**
     * 派遣和订单金额保存
     * @param dispatch
     * @param offter_name
     * @param offter_amount
     */
    public void DOsave(Long id,String dispatch,String offter_name,String offter_amount,CarRental carRental){
        String[] names = JsonUtil.json2Obj(offter_name, String[].class);
        String[] amounts = JsonUtil.json2Obj(offter_amount, String[].class);
        Long[] busIds = JsonUtil.json2Obj(dispatch, Long[].class);

        if(names.length!=0 && StringUtils.isNotBlank(names[0])){

            List<CarRentalOffer> carRentalOfferList = carRentalOfferService.queryByProperty("rentalId",id);
            for(CarRentalOffer c : carRentalOfferList){
                carRentalOfferService.delete(c);
            }

            for(int i=0;i<names.length;i++){
                CarRentalOffer carRentalOffer = new CarRentalOffer();
                carRentalOffer.setName(names[i]);
                carRentalOffer.setAmount(Double.parseDouble(amounts[i]));
                carRentalOffer.setRentalId(id);
                carRentalOfferService.save(carRentalOffer);
            }

            //一旦保存了金额,状态就变为待付款
            Order order = orderService.queryByPK(carRental.getOrder().getId());
            order.setStatus(1);
            orderService.save(order);

            //若订单状态变为待付款，则提示用户尽快付款
            String openId = carRental.getOrder().getUserInfo().getWeChatUser().getOpenId();
            sendTextMessageToUser("你的订单已审核，请尽快付款",openId);
        }

        if(busIds.length>0){

            List<BusSend> busSendList = busSendService.queryByProperty("contactId",id);
            for(BusSend b : busSendList){
                busSendService.delete(b);
            }

            for (Long busId : busIds){
                BusSend busSend = new BusSend();
                busSend.setContactId(id);
                busSend.setBus(busService.queryByPK(busId));
                //租车
                busSend.setType(2);
                busSendService.save(busSend);
            }

        }

    }


    @Override
    @Transactional
    public void save(Long id, Long cityId, Integer rwType, String startPoint, String endPoint, String startDate, String endDate, Long carTypeId, Integer totalNumber, Integer busNum, Integer isInvoice, String invoice, String dispatch, String offter_name, String offter_amount) throws ParseException {
            if(id!=null){
                CarRental carRental =  queryByPK(id);
                carRental.setCity(cityService.queryByPK(cityId));
                carRental.setRentalWay(rwType);
                carRental.setStartPoint(startPoint);
                carRental.setEndPoint(endPoint);
                carRental.setStartDate(DateUtils.stringToLong(startDate,"yyyy-MM-dd HH:mm"));
                if(rwType==2){
                    carRental.setEndDate(DateUtils.stringToLong(endDate,"yyyy-MM-dd HH:mm"));
                }
                carRental.setCarType(carTypeService.queryByPK(carTypeId));
                carRental.setTotalNumber(totalNumber);
                carRental.setBusNum(busNum);
                carRental.setIsInvoice(isInvoice);
                if(isInvoice==1){
                    carRental.setInvoice(invoice);
                }else {
                    carRental.setInvoice("");
                }

                this.DOsave(id,dispatch,offter_name,offter_amount,carRental);

                save(carRental);

            }
    }

    @Override
    @Transactional
    public Integer saveDispatch(Long id, String dispatch, String offter_name, String offter_amount) {
        try{
            if(id!=null){
                CarRental carRental =  queryByPK(id);
                this.DOsave(id,dispatch,offter_name,offter_amount,carRental);
                String openId = carRental.getOrder().getUserInfo().getWeChatUser().getOpenId();
                sendTextMessageToUser("您的订单状态已经改变,请注意查看",openId);
            }
        }catch (Exception e){
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public Integer del(Long id) {
        if (id==null){
            return 1;
        }
        try {
            delete(queryByPK(id));

            //删除派遣车辆
            List<BusSend> busSendList = busSendService.queryByProperty("contactId",id);
            if(!busSendList.isEmpty()){
                for (BusSend busSend : busSendList){
                    busSendService.delete(busSend);
                }
            }

            //删除租车订单报价
            List<CarRentalOffer> carRentalOfferList = carRentalOfferService.queryByProperty("rentalId",id);
            if(!carRentalOfferList.isEmpty()){
                for (CarRentalOffer carRentalOffer : carRentalOfferList){
                    carRentalOfferService.delete(carRentalOffer);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public List<CarRental> findList(Long id) {
        return carRentalDao.findList(id);
    }

    @Override
    public CarRental findOne(Long orderId) {
        return carRentalDao.findOne(orderId);
    }

    /**
     * 微信保存
     * @param userInfo
     * @param city
     * @param from
     * @param to
     * @param stype
     * @param time1
     * @param time2
     * @param number
     * @param amount
     * @param ticket
     * @param title
     * @param linkman
     * @param mobile
     * @param carTypeId
     * @param id
     * @throws ParseException
     */
    @Override
    @Transactional
    public void save(UserInfo userInfo,String city,String from, String to, Integer stype, String time1, String time2, Integer number, Integer amount, Integer ticket, String title, String linkman, String mobile, Long carTypeId,Long id) throws ParseException {
        CarRental carRental;
        Order order;

        if(id!=null){
            carRental = queryByPK(id);
            order = carRental.getOrder();
        }else {
            carRental = new CarRental();
            //新增一条订单
            order = new Order();
            order.setType(2);
            order.setStatus(0);
            //登录时存Session 再取
            order.setUserInfo(userInfo);

            order.setOrderNo(SeqNoUtils.getMallOrderCode(0));
            order.setIsComment(0);
        }

        //新增一条租车信息
        carRental.setCity(cityService.queryByProperty("name",city).get(0));
        carRental.setCarType(carTypeService.queryByPK(carTypeId));
        carRental.setStartPoint(from);
        carRental.setEndPoint(to);
        carRental.setRentalWay(stype);
        carRental.setStartDate(DateUtils.stringToLong(time1,"yyyy-MM-dd HH:mm"));

        if(stype!=0 && time2!=null){
            carRental.setEndDate(DateUtils.stringToLong(time2,"yyyy-MM-dd HH:mm"));
        }

        carRental.setBusNum(number);
        carRental.setTotalNumber(amount);

        if(ticket!=null){
            carRental.setIsInvoice(ticket);
        }else {
            carRental.setIsInvoice(0);
        }

        if(ticket!=null && ticket==1 && StringUtils.isNotBlank(title)){
            carRental.setInvoice(title);
        }else {
            carRental.setInvoice("");
        }

        carRental.setIncome(0.0);
        carRental.setRefund(0.0);
        carRental.setUnsubscribe("");
        carRental.setIsDel(0);

        order.setUserName(linkman);
        order.setMobile(mobile);
        orderService.save(order);

        //关联订单
        carRental.setOrder(order);
        //新增的订单未改签过
        carRental.setIsRewrite(0);
        save(carRental);
    }

    //逻辑删除订单
    @Override
    @Transactional
    public void del(String ro_ids,String cr_ids) {
        if(StringUtils.isBlank(ro_ids) && StringUtils.isBlank(cr_ids)){
            return;
        }
        //删除班车订单
        if(StringUtils.isNotBlank(ro_ids)){
            Long[] ro_ss = JsonUtil.json2Obj(ro_ids,Long[].class);
            for (Long _id : ro_ss) {
                RouteOrder routeOrder = routeOrderService.queryByPK(_id);
                routeOrder.setIsDel(1);
                routeOrderService.save(routeOrder);
            }
        }

        //删除租车订单
        if(StringUtils.isNotBlank(cr_ids)) {
            Long[] cr_ss = JsonUtil.json2Obj(cr_ids, Long[].class);
            for (Long _id : cr_ss) {
                CarRental carRental = queryByPK(_id);
                carRental.setIsDel(1);
                save(carRental);
            }
        }

    }


    //物理删除
    @Transactional
    public void del1(String ro_ids,String cr_ids) {
        if(StringUtils.isBlank(ro_ids) && StringUtils.isBlank(cr_ids)){
            return;
        }

        //删除班车订单
        if(StringUtils.isNotBlank(ro_ids)){
            Long[] ro_ss = JsonUtil.json2Obj(ro_ids,Long[].class);
            for (Long _id : ro_ss) {
                //删除班车订单
                RouteOrder routeOrder = routeOrderService.queryByPK(_id);
                Order order = routeOrder.getOrder();
                routeOrderService.delete(routeOrder);
                orderService.delete(order);

            }
        }

        //删除租车订单
        if(StringUtils.isNotBlank(cr_ids)){
            Long[] cr_ss = JsonUtil.json2Obj(cr_ids,Long[].class);
            for (Long _id : cr_ss) {
                //删除租单
                CarRental carRental = queryByPK(_id);
                Long carRentalId = carRental.getId();
                //删除订单
                Order order = carRental.getOrder();

                if(order.getStatus()!=0){
                    //删除订单价钱
                    List<CarRentalOffer> carRentalOfferList = carRentalOfferService.queryByProperty("rentalId",_id);
                    for(CarRentalOffer carRentalOffer : carRentalOfferList){
                        carRentalOfferService.delete(carRentalOffer);
                    }
                    //删除订单车辆
                    List<BusSend> busSendLis = busSendService.findBus(_id,2);
                    for(BusSend busSend : busSendLis){
                        busSendService.delete(busSend);
                    }
                }
                carRental.setBusSends(null);
                carRental.setCarRentalOffers(null);
                delete(carRental);
                orderService.delete(order);

            }
        }

    }


    public void sendTextMessageToUser(String content,String toUser){

        String json = "{\"touser\": \""+toUser+"\",\"msgtype\": \"text\", \"text\": {\"content\": \""+content+"\"}}";

        //获取access_token

        String accesstoken = wxMpConfigStorage.getAccessToken();

        //获取请求路径

        String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accesstoken;

        System.out.println("json:"+json);

        try {

            connectWeiXinInterface(action,json);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static JSONObject connectWeiXinInterface(String reqPath, String json) {
        URL url;
        String result = "";
        JSONObject jsonObject = null;
        try {
            url = new URL(reqPath);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoInput(true);
            http.setDoOutput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(json.getBytes("UTF-8"));// 传入参数
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] b = new byte[size];
            is.read(b);
            result = new String(b, "UTF-8");
//            log.info("请求返回结果:" + result);
            System.out.print("请求返回结果:" + result);
            jsonObject = JSONObject.fromObject(result);
            os.flush();
            os.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
