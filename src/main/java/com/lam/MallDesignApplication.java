package com.lam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class MallDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallDesignApplication.class, args);
    }

}
