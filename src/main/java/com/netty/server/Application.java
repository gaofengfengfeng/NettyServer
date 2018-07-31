package com.netty.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: gaofeng
 * @Date: 2018-07-19
 * @Description:
 */
@EnableScheduling
@SpringBootApplication
@MapperScan("com.netty.server.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
