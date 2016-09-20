package com.leoman.task;

import com.leoman.bus.entity.Bus;
import com.leoman.bus.service.BusService;
import com.leoman.bus.util.GpxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Daisy on 2016/9/20.
 */
@Component
@Lazy(value = false)
public class GpxTask {

    @Autowired
    private BusService busService;

    @Scheduled(cron="0/30 * * * * ? ")
    public void Task(){
        System.out.println("===================定时任务开启=====================");
        List<Map> groups = GpxUtil.getGroupsBus();
        for (Map group:groups) {
            List<Map> busList = (List<Map>)group.get("vehicles");
            for (Map map:busList) {
                String vid = String.valueOf(map.get("id"));//车辆ID
                String vKey = (String) map.get("vKey");//车辆授权码
                List<Map> locs = GpxUtil.getCurrentLoc(vid,vKey);
                for (Map loc:locs) {
                    String uuid = String.valueOf(loc.get("id"));
                    Double curLat = Double.valueOf((String) loc.get("lat"));//纬度
                    Double curLng = Double.valueOf((String) loc.get("lng"));//经度
                    Double curLatXZ = Double.valueOf((String) loc.get("lat_xz"));//纬度修正值
                    Double curLngXZ = Double.valueOf((String) loc.get("lng_xz"));//经度修正值
                    System.out.println("---------current lat----------"+curLat);
                    System.out.println("---------current lng----------"+curLng);
                    System.out.println("---------current lat xz----------"+curLatXZ);
                    System.out.println("---------current lng xz----------"+curLngXZ);
                    Bus bus = busService.findByUuid(uuid);
                    if(bus != null){
                        bus.setCurLat(curLat);
                        bus.setCurLng(curLng);
                        busService.save(bus);
                    }
                }
            }
        }
        System.out.println("===================定时任务结束=====================");
    }

}
