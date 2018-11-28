package com.nezha.learn.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UseRedisDemo {
    private final static Logger logger = LoggerFactory.getLogger(UseRedisDemo.class);

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void say(String name){
        String key = "demo:redis:"+name;
        try {
            redisTemplate.opsForValue().setIfAbsent(key, "Hello World!");
            redisTemplate.expire(key,1000,TimeUnit.SECONDS);
        }catch (Exception e){
            logger.info(e.getLocalizedMessage());
        }
    }
}
