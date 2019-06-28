package com.qf.temptimer.timer;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTimer {

    @Scheduled(cron = "* * * * * ?")
    public void Task(){
        System.out.println(Thread.currentThread().getName()+"任务测试");
    }
}
