package com.nezha.guava.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: nezha <br>
 * @Title: HelloApi <br>
 * @ProjectName: demo-guava <br>
 * @Description: xxxxx <br>
 * @Date: 2019/8/12 8:22 PM <br>
 */
@RestController
@Slf4j
public class HelloApi {

    @Autowired
    private CacheManager cacheManager;

    @Cacheable(value = "user", key = "#name")
    @GetMapping("get/{name}")
    public String getUserByName(@PathVariable(name = "name") String name) throws Exception {
        Thread.sleep(3000);
        log.info("user's name is {}", name);
        return "Hello, " + name;
    }

    @GetMapping("getValue/{name}")
    public String getValueByName(@PathVariable(name = "name") String name) throws Exception {
        String value = cacheManager.getCache("user").get(name).get().toString();
        log.info("user's name is {}", value);
        return "get value is :, " + value;
    }


}
