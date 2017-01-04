package com.leoman.task;

import com.leoman.bus.entity.Bus;
import com.leoman.bus.service.BusService;
import com.leoman.bus.util.GpxUtil;
import com.leoman.bus.util.MathUtil;
import com.leoman.common.log.service.LogService;
import com.leoman.utils.BeanUtils;
import com.leoman.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/4.
 */
public class BusRunnable implements Runnable {

    @Override
    public void run(){
        try {
            Date d1 = new Date();
            //获取所有的车辆信息
            BusService busService = (BusService) BeanUtils.getBean("busService");
            List<Bus> busList = busService.queryAll();
            for (Bus bus:busList) {
                String vid = bus.getUuid();//车辆ID
                String vKey = bus.getVkey();//车辆授权码
                //更新当前车辆信息
                List<Map> locs = GpxUtil.getCurrentLoc(vid,vKey);
                if(locs != null){
                    for (Map loc:locs) {
                        Double curLat = (Double)loc.get("lat");//纬度
                        Double curLng = (Double)loc.get("lng");//经度
                        if(!curLat.equals(bus.getCurLat()) || !curLng.equals(bus.getCurLng())){
                            double[] position = MathUtil.wgs2bd(curLat ,curLng);
                            bus.setCurLat(position[0]);
                            bus.setCurLng(position[1]);
                            busService.save(bus);
                        }
                    }
                }
            }
            Date d2 = new Date();
            System.out.println("当前线程为："+Thread.currentThread().getName()+"，更新所有车的位置所需要的时间："+ (d2.getTime()-d1.getTime()) +"毫秒");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
