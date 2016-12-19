package com.leoman.task;

import com.leoman.bus.entity.Bus;
import com.leoman.bus.entity.CarType;
import com.leoman.bus.service.BusService;
import com.leoman.bus.util.GpxUtil;
import com.leoman.bus.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Daisy on 2016/9/20.
 */
@Component
public class GpxTask {

    @Autowired
    private BusService busService;

    @Scheduled(cron="0/10 * * * * ? ")
    public void Task(){
        List<Map> groups = GpxUtil.getGroupsBus();
        if(groups != null){
            for (Map group:groups) {
                List<Map> busList = (List<Map>)group.get("vehicles");
                for (Map map:busList) {
                    String vid = String.valueOf(map.get("id"));//车辆ID
                    String vKey = (String) map.get("vKey");//车辆授权码
                    List<Map> locs = GpxUtil.getCurrentLoc(vid,vKey);
                    if(locs != null){
                        for (Map loc:locs) {
                            String uuid = String.valueOf(loc.get("id"));
                            Double curLat = (Double)loc.get("lat");//纬度
                            Double curLng = (Double)loc.get("lng");//经度
                            Bus bus = busService.findByUuid(uuid);
                            if(bus != null){
                                if(curLat != bus.getCurLat() || curLng != bus.getCurLng()){
                                    double[] position = MathUtil.wgs2bd(curLat ,curLng);
                                    bus.setCurLat(position[0]);
                                    bus.setCurLng(position[1]);
                                    busService.save(bus);
                                }
                            }else{
                                bus = new Bus();
                                bus.setUuid(String.valueOf(map.get("id")));//id
                                bus.setCarNo((String) map.get("name"));//车牌号
                                bus.setVkey((String) map.get("vKey"));//车辆授权码
                                bus.setCarType(new CarType(1l));//类型为通勤班车
                                busService.save(bus);
                            }
                        }
                    }
                }
            }
        }
    }

}
