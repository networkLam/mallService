package com.lam.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Logistics {
//多线程
//    @Scheduled(fixedRate = 150000)
    public void print(){
        System.out.println("printing");


    }
}
