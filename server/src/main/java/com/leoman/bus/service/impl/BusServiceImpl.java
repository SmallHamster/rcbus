package com.leoman.bus.service.impl;

import com.leoman.bus.dao.BusDao;
import com.leoman.bus.entity.Bus;
import com.leoman.bus.service.BusService;
import com.leoman.common.core.ErrorType;
import com.leoman.common.core.Result;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.image.dao.ImageDao;
import com.leoman.image.entity.Image;
import com.leoman.utils.ClassUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Result saveBus(Bus bus) {
        Long busId = bus.getId();
        if(busId == null){
            List<Bus> busList = busDao.findByCarNo(bus.getCarNo());
            if(busList != null && busList.size() > 0){
                return new Result().failure(ErrorType.ERROR_CODE_00032);//车牌已存在
            }
        }else{
            List<Bus> busList = busDao.findByCarNoAndId(bus.getCarNo(), busId);
            if(busList != null && busList.size() > 0){
                return new Result().failure(ErrorType.ERROR_CODE_00032);//车牌已存在
            }
            Bus org = busDao.findOne(busId);
            ClassUtil.copyProperties(org, bus);
            bus = org;
        }

        busDao.save(bus);
        return new Result().success();
    }

    @Override
    public List<Bus> findByCarNo(String carNo) {
        return busDao.findByCarNo(carNo);
    }

}
