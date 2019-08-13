package com.nezha.guava.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.util.StopWatch;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: nezha <br>
 * @Title: CaffeineMain <br>
 * @ProjectName: demo-guava <br>
 * @Description: Caffeine缓存策略 <br>
 * @Date: 2019/8/6 4:38 PM <br>
 */
public class CaffeineMain {

    public static void main(String[] args) {
        cacheWriteTest();
    }

    public static void cacheWriteTest() {
        Cache<Integer, Integer> cache = Caffeine.newBuilder()
                .maximumSize(1000000)
                .recordStats()
                .build();
        StopWatch stopWatch = new StopWatch(UUID.randomUUID().toString());
        stopWatch.start("put operate");
        for (int i = 0; i < 1000000; i++) {
            cache.put(i, i);
        }
        stopWatch.stop();
        stopWatch.start("get operate");
        for (int i = 0; i < 1000000; i++) {
            cache.getIfPresent(ThreadLocalRandom.current().nextInt(1000000));
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(cache.stats());
    }

    public static void method1() {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(3)
                .build();

        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("5", "5");
        cache.put("4", "4");
        System.out.println(cache.asMap());
    }

}
