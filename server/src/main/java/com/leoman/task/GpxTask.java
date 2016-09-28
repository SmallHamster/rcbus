package com.leoman.task;

import com.leoman.bus.entity.Bus;
import com.leoman.bus.service.BusService;
import com.leoman.bus.util.GpxUtil;
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
            System.out.println("===================定时任务开启=====================");
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
                            Double curLatXZ = (Double) loc.get("lat_xz");//纬度修正值
                            Double curLngXZ = (Double)loc.get("lng_xz");//经度修正值
                            System.out.println("--------- busID = "+uuid+", current lat = "+curLat+", current lng = "+curLng+" ---------");
                            Bus bus = busService.findByUuid(uuid);
                            if(bus != null){
                                bus.setCurLat(curLat);
                                bus.setCurLng(curLng);
                                busService.save(bus);
                            }
                        }
                    }
                }
            }
            System.out.println("===================定时任务结束=====================");
        }
    }

}
