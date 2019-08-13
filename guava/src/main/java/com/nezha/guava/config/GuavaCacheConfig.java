package com.nezha.guava.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.concurrent.TimeUnit;

/**
 * @author: nezha <br>
 * @Title: GuavaCacheConfig <br>
 * @ProjectName: demo-guava <br>
 * @Description: guava 缓存配置<br>
 * @Date: 2019/8/12 5:43 PM <br>
 */
@Configuration
@EnableCaching
public class GuavaCacheConfig {

    @Bean
    public Caffeine<Object, Object> caffeine() {
        return Caffeine.newBuilder()
                .recordStats()
                .maximumSize(5)
                .expireAfterAccess(5, TimeUnit.SECONDS);
    }


    @DependsOn({"caffeine"})
    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
