package com.nezha.learn.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: nezha <br>
 * @Title: RedissonConfig <br>
 * @ProjectName: demo-redis <br>
 * @Description: xxxxx <br>
 * @Date: 2019/8/28 11:31 PM <br>
 */

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
