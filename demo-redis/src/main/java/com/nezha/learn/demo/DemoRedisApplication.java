package com.nezha.learn.demo;

import com.nezha.learn.demo.service.UseRedisDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoRedisApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DemoRedisApplication.class, args);
        UseRedisDemo useRedisDemo = ctx.getBean(UseRedisDemo.class);
        useRedisDemo.getLock("nezha");
    }
}
