package com.leoman.task;

import com.leoman.bus.entity.Bus;
import com.leoman.bus.service.BusService;
import com.leoman.bus.util.GpxUtil;
import com.leoman.bus.util.MathUtil;
import com.leoman.utils.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/1/4.
 */
public class BusExecutor {

    final ExecutorService service = Executors.newFixedThreadPool(10);


    public void start(){
        /*ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        long initialDelay1 = 0;
        long period1 = 2;
        // 从现在开始1秒钟之后，每隔1秒钟执行一次job1L
        service.scheduleAtFixedRate(new BusRunnable(), initialDelay1,
                period1, TimeUnit.SECONDS);*/

        /*Date d1 = new Date();
        //获取所有的车辆信息
        final BusService busService = (BusService) BeanUtils.getBean("busService");
        List<Bus> busList = busService.queryAll();
        for (final Bus bus:busList) {
            final String vid = bus.getUuid();//车辆ID
            final String vKey = bus.getVkey();//车辆授权码
            //更新当前车辆信息
            service.execute(new Runnable() {
                @Override
                public void run() {
                    long time = System.currentTimeMillis();
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
                    System.out.println("更新一辆车的位置所需要的时间："+ (System.currentTimeMillis()-time) +"毫秒");
                }
            });

        }
        while(true){
            Date d2 = new Date();
            int threadCount = ((ThreadPoolExecutor)service).getActiveCount();
            if (threadCount!=0){
                System.out.println((d2.getTime()-d1.getTime())+"正在运行的线程数目:"+threadCount);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

}
