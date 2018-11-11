package com.nezha.learn.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UseRedisDemo {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void addUser(){
        try {
            redisTemplate.opsForValue().set("demo::redis::test","Hello World2!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
