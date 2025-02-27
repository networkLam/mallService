package com.lam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@ServletComponentScan
@SpringBootApplication
//@ComponentScan(basePackages = "com.lam.security") // 显式扫描包
//@EnableScheduling
public class MallDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallDesignApplication.class, args);
    }

}
