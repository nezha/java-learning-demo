package com.nezha.component.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author: nezha <br>
 * @Title: ZKlockTestApplication <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: 启动类 <br>
 * @Date: 2019/9/19 11:03 AM <br>
 */

@SpringBootApplication
@EnableAspectJAutoProxy
public class ZKlockTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZKlockTestApplication.class, args);
    }
}
