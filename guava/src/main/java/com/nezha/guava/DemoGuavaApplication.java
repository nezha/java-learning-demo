package com.nezha.guava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Author: nezha
 * @Email: nezhaxiaozi@outlook.com
 */

@EnableCaching
@SpringBootApplication
public class DemoGuavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoGuavaApplication.class, args);
    }

}
