package com.leoman.carrental.service.impl;

import com.leoman.bus.service.BusService;
import com.leoman.bus.service.CarTypeService;
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
import com.leoman.utils.DateUtils;
import com.leoman.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartRequest;

import javax.persistence.Transient;
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


    @Override
    public List<Map<String, Object>> pageToExcel(List carRentals) throws ParseException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> params = null;
        List<CarRental> baseList = new ArrayList<>();
        baseList.addAll(carRentals);
//        baseList = queryAll();
        Set set = null;
        Iterator iterator = null;
        Map.Entry tempMap = null;

        if(baseList != null){
            for (CarRental carRental : baseList) {
                params = new HashMap<String, Object>();

                if(carRental.getOrder()!=null){
                    params.put("orderNo", carRental.getOrder().getOrderNo());
                    params.put("startDate", DateUtils.longToString(carRental.getStartDate(),"yyyy-MM-dd hh:mm"));
                    params.put("userName", carRental.getOrder().getUserName());
                    params.put("mobile", carRental.getOrder().getMobile());
                    params.put("busNum", carRental.getBusNum());

                }

                params.put("totalAmount", carRental.getTotalAmount() );
                params.put("income", carRental.getIncome());
                params.put("refund", carRental.getRefund());

                // 去掉null值
                set = params.entrySet();
                iterator = set.iterator();
                while (iterator.hasNext()) {
                    tempMap = (Map.Entry) iterator.next();
                    params.put(tempMap.getKey().toString(), null == tempMap.getValue() ? "" : tempMap.getValue());
                }

                list.add(params);
            }
        }

        return list;
    }

    @Override
    public Integer readExcelInfo(MultipartRequest multipartRequest, Integer type) {
        return null;
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

        if(names!=null && StringUtils.isNotBlank(names[0])){

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

            //一旦保存了金额,状态就变为代付款
            Order order = orderService.queryByPK(carRental.getOrder().getId());
            order.setStatus(1);
            orderService.save(order);
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
    public Integer save(Long id, Long cityId, Integer rwType, String startPoint, String endPoint, String startDate, String endDate, Long carTypeId, Integer totalNumber, Integer busNum, Integer isInvoice, String invoice, String dispatch, String offter_name, String offter_amount) {
        try{
            if(id!=null){
                CarRental carRental =  queryByPK(id);
                carRental.setCity(cityService.queryByPK(cityId));
                carRental.setRentalWay(rwType);
                carRental.setStartPoint(startPoint);
                carRental.setEndPoint(endPoint);
                carRental.setStartDate(DateUtils.stringToLong(startDate,"yyyy-MM-dd hh:mm"));
                if(rwType==2){
                    carRental.setEndDate(DateUtils.stringToLong(endDate,"yyyy-MM-dd hh:mm"));
                }
                carRental.setCarType(carTypeService.queryByPK(carTypeId));
                carRental.setTotalNumber(totalNumber);
                carRental.setBusNum(busNum);
                carRental.setIsInvoice(isInvoice);
                if(isInvoice==1){
                    carRental.setInvoice(invoice);
                }

                this.DOsave(id,dispatch,offter_name,offter_amount,carRental);

                save(carRental);

            }
        }catch (Exception e){
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public Integer saveDispatch(Long id, String dispatch) {
        try{
            if(id!=null){
                CarRental carRental =  queryByPK(id);
                this.DOsave(id,dispatch,null,null,carRental);
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
            for (BusSend busSend : busSendList){
                busSendService.delete(busSend);
            }

            //删除租车订单报价
            List<CarRentalOffer> carRentalOfferList = carRentalOfferService.queryByProperty("rentalId",id);
            for (CarRentalOffer carRentalOffer : carRentalOfferList){
                carRentalOfferService.delete(carRentalOffer);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
}
