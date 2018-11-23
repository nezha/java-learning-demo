package com.nezha.learn.demo;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nezha.learn.demo.service.UseRedisDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoRedisApplicationTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UseRedisDemo useRedisDemo;

    @Test
    public void test() throws Exception {
        useRedisDemo.addUser();
    }

    @Test
    public void jacksonTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User user = new User("张熠", 26);
        String json=mapper.writeValueAsString(user); //将对象转换成json
        logger.info("序列化的内容是：{}",json);
        System.out.println(json);

    }
}
