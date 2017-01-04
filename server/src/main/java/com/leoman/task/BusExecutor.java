package com.leoman.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/1/4.
 */
public class BusExecutor {

    public void start(){
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        long initialDelay1 = 0;
        long period1 = 2;
        // 从现在开始1秒钟之后，每隔1秒钟执行一次job1L
        service.scheduleAtFixedRate(new BusRunnable(), initialDelay1,
                period1, TimeUnit.SECONDS);
    }

}
