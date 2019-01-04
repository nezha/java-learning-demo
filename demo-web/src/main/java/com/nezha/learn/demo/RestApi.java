package com.nezha.learn.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping(value = "/test")
public class RestApi {

    private static final Logger logger = LoggerFactory.getLogger(RestApi.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    @RequestMapping(value = "/add")
    public String addInfo(){
        atomicInteger.incrementAndGet();
        logger.info("This bbb's count is:{}",atomicInteger);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/hh-mm-ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = formatter.format(localDateTime);
        String key = "demo:bbb";
        try {
            redisTemplate.opsForValue().set(key, atomicInteger.toString());
            redisTemplate.expire(key,10*60,TimeUnit.SECONDS);
        }catch (Exception e){
        }
        return time;
    }
}

