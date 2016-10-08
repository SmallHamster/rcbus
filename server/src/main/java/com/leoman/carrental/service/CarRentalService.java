package com.leoman.carrental.service;

import com.leoman.carrental.entity.CarRental;
import com.leoman.common.core.Result;
import com.leoman.common.service.GenericManager;
import com.leoman.user.entity.UserInfo;
import org.springframework.web.multipart.MultipartRequest;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface CarRentalService extends GenericManager<CarRental> {

    public List<Map<String, Object>> pageToExcel(List<CarRental> carRentals) throws ParseException;

    public void save(Long id, Long cityId, Integer rwType, String startPoint, String endPoint, String startDate, String endDate, Long carTypeId, Integer totalNumber, Integer busNum, Integer isInvoice, String invoice, String dispatch, String offter_name, String offter_amount) throws ParseException;

    public Integer saveDispatch(Long id,String dispatch);

    public Integer del(Long id);

    public List<CarRental> findList(Long id);

    public CarRental findOne(Long orderId);

    //微信新增
    public void save(UserInfo userInfo,String city, String from, String to, Integer stype, String time1, String time2, Integer number, Integer amount, Integer ticket, String title, String linkman, String mobile, Long carTypeId, Long id) throws ParseException;

    //微信删除
    public void del(String ro_ids,String cr_ids);
}
