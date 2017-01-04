package com.leoman.task;

import com.leoman.bus.entity.Bus;
import com.leoman.bus.entity.CarType;
import com.leoman.bus.service.BusService;
import com.leoman.bus.util.GpxUtil;
import com.leoman.bus.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Daisy on 2016/9/20.
 */
@Component
public class GpxTask {

    @Autowired
    private BusService busService;

    /**
     * 每天早上6:00获取所有车辆信息
     */
    @Scheduled(cron="0 0 6 * * ?")
    public void getBus(){
        List<Map> groups = GpxUtil.getGroupsBus();
        if(groups != null){
            for (Map group:groups) {
                List<Map> busList = (List<Map>)group.get("vehicles");
                for (Map map:busList) {
                    String vid = String.valueOf(map.get("id"));//车辆ID
                    String vKey = (String) map.get("vKey");//车辆授权码
                    String name = (String) map.get("name");//车牌号

                    Bus bus;

                    List<Bus> bList = busService.findByCarNo(name);
                    //若该车牌号不存在，则新增该车辆信息
                    if(bList == null || bList.size() == 0){
                        bus = new Bus();
                        bus.setUuid(vid);//id
                        bus.setCarNo(name);//车牌号
                        bus.setVkey(vKey);//车辆授权码
                        bus.setCarType(new CarType(1l));//类型为通勤班车
                        busService.save(bus);
                    }else{
                        for (int i=0; i<bList.size(); i++) {
                            //如果该车牌号有多辆车，则其他的都删除，只保留一辆
                            if(i > 0){
                                busService.delete(bList.get(i));
                            }
                        }
                        //若车辆的vid和vkey修改了，则更新该车辆信息
                        bus = bList.get(0);
                        if(!vid.equals(bus.getUuid()) || !vKey.equals(bus.getVkey())){
                            bus.setUuid(vid);
                            bus.setVkey(vKey);
                            busService.save(bus);
                        }
                    }
                }
            }
        }
    }

    /**
     * 每隔3s获取车的位置
     */
/*    @Scheduled(cron="0/3 * * * * ? ")
    public void getLoc(){
        Date d1 = new Date();
        //获取所有的车辆信息
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
        System.out.println("更新所有车的位置所需要的时间："+ (d2.getTime()-d1.getTime()) +"毫秒");
    }*/

}
