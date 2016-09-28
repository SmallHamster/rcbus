package com.leoman.order.service;

import com.leoman.common.service.GenericManager;
import com.leoman.order.entity.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface OrderService extends GenericManager<Order>{

    public Map<String, Object> findPage(HttpServletRequest request, String routeName, Long enterpriseId, String startDate, String endDate, Integer draw, Integer pagenum, Integer length);
}
