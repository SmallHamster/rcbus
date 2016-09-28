package com.leoman.bus.service.impl;

import com.leoman.bus.dao.BusDao;
import com.leoman.bus.entity.Bus;
import com.leoman.bus.service.BusService;
import com.leoman.bussend.entity.BusSend;
import com.leoman.common.service.GenericManager;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daisy on 2016/9/6.
 */
@Service
public class BusServiceImpl extends GenericManagerImpl<Bus, BusDao> implements BusService {

    @Autowired
    private BusDao busDao;

    @Override
    public Bus findByUuid(String uuid) {
        return busDao.findByUuid(uuid);
    }

    /**
     * 根据距离对车辆进行排序
     * @return
     */
    public List<Bus> findBusOrderByDistance(Long routeId, Double userLat, Double userLng) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n" +
                "  b.*\n" +
                " FROM\n" +
                "  t_bus_send bs \n" +
                "  LEFT JOIN t_bus b \n" +
                "    ON b.`id` = bs.`bus_id` \n" +
                " WHERE bs.`type` = 1 \n" +
                "  AND bs.`contact_id` = "+routeId+" \n" );

        if(userLat != null && userLng != null){
            sql.append(" ORDER BY ROUND(6378.138*2*ASIN(SQRT(POW(SIN((30.475322*PI()/180-b.`cur_lat`*PI()/180)/2),2)+COS(30.475322*PI()/180)*COS(b.`cur_lat`*PI()/180)*POW(SIN((114.330368*PI()/180-b.`cur_lng`*PI()/180)/2),2)))*1000) ");
        }

        List<Bus> busList = queryBySql(sql.toString(),Bus.class);
        return busList;
    }
}
