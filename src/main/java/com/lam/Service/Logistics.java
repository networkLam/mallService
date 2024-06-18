package com.lam.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Logistics {

    @Scheduled(fixedRate = 150000)
    public void print(){
        System.out.println("printing");


    }
}
