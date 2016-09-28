package com.leoman.order.service.impl;

import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.order.dao.OrderDao;
import com.leoman.order.entity.Order;
import com.leoman.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/7.
 */
@Service
public class OrderServiceImpl extends GenericManagerImpl<Order,OrderDao> implements OrderService{

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order findOne(String orderNo) {
        return orderDao.findOne(orderNo);
    }

    @Override
    public Map<String, Object> findPage(HttpServletRequest request, String routeName, Long enterpriseId, String startDate, String endDate, Integer draw, Integer pagenum, Integer length){
//        int pagenum = getPageNum(start, length);
        StringBuffer sql = new StringBuffer("SELECT \n" +
                "  CONCAT(FROM_UNIXTIME(ro.`create_date` / 1000,'%Y-%m-%d'),' ',ro.`depart_time`) AS rideTime,\n" +//0
                "  ro.`start_station` AS startStation,\n" +//1
                "  ro.`end_station` AS endStation,\n" +//2
                "  ro.`depart_time` AS departTime,\n" +//3
                "  e.`name` AS enterpriseName,\n" +//4
                "  COUNT(o.`user_id`) AS peopleNum, \n" +//5
                "  ro.`route_id` AS routeId\n" +//6
                "FROM\n" +
                "  t_route_order ro \n" +
                "  LEFT JOIN t_order o \n" +
                "    ON o.`id` = ro.`order_id` \n" +
                "  LEFT JOIN t_route r \n" +
                "    ON r.`id` = ro.`route_id` \n" +
                "  LEFT JOIN t_enterprise e \n" +
                "    ON e.`id` = r.`enterprise_id` \n" +
                "  where 1=1 ");
        if(!StringUtils.isEmpty(routeName)){
            sql.append(" AND (ro.`start_station` LIKE '%"+routeName+"%' OR ro.`end_station` LIKE '%"+routeName+"%') ");
        }
        if(enterpriseId != null){
            sql.append(" and e.`id` = '"+enterpriseId+"'");
        }
        if(!StringUtils.isEmpty(startDate)){
            sql.append(" AND FROM_UNIXTIME(ro.`create_date` / 1000,'%Y-%m-%d') >= DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
        }
        if(!StringUtils.isEmpty(endDate)){
            sql.append(" AND FROM_UNIXTIME(ro.`create_date` / 1000,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
        }
        sql.append(" GROUP BY CONCAT(FROM_UNIXTIME(ro.`create_date` / 1000,'%Y-%m-%d'),' ',ro.`depart_time`) ");
        Page page = queryPageBySql(sql.toString(), pagenum, length);
        return DataTableFactory.fitting(draw, page);
    }

}
