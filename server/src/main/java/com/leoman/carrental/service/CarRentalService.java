package com.leoman.carrental.service;

import com.leoman.carrental.entity.CarRental;
import com.leoman.common.service.GenericManager;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface CarRentalService extends GenericManager<CarRental> {

    public Integer save(Long id, Long cityId, Integer rwType, String startPoint, String endPoint, String startDate, String endDate, Long carTypeId, Integer totalNumber, Integer busNum, Integer isInvoice, String invoice, String dispatch, String offter_name, String offter_amount);

    public Integer saveDispatch(Long id,String dispatch);

}
