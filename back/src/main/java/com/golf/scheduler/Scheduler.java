package com.golf.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Scheduled(fixedRate = 2000)
    public void addHistory(){
        //System.out.println("test   111");
    }

    @Scheduled(fixedRate = 2000)
    public void useBox(){
        //System.out.println("test   222");
    }
}
