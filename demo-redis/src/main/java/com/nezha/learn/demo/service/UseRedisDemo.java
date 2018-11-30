package com.nezha.learn.demo.service;

import com.nezha.learn.demo.util.RedisTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UseRedisDemo {
    private final static Logger logger = LoggerFactory.getLogger(UseRedisDemo.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Environment env;

    public void say(String name){
        String key = "demo:redis:"+name;
        try {
            redisTemplate.opsForValue().setIfAbsent(key, "Welcome!");
            redisTemplate.expire(key,1000,TimeUnit.SECONDS);
        }catch (Exception e){
            logger.info(e.getLocalizedMessage());
        }
    }

    public void getLock(String name){
        String key = "demo:"+name;
        boolean lock = RedisTool.getLock(redisTemplate,env,key,name,20L);
        if (lock){
            try{
                logger.info("执行睡眠...");
                Thread.sleep(10000);
            }catch (InterruptedException e){
                logger.info("发生错误{}",e);
            }finally {
                RedisTool.releaseLock(redisTemplate,env,key,name);
            }
        }
    }
}
