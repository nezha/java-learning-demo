package com.nezha.learn.demo;

import com.nezha.learn.demo.service.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: nezha <br>
 * @Title: UseRedissonDemo <br>
 * @ProjectName: demo-redis <br>
 * @Description: 使用Redisson的demo <br>
 * @Date: 2019/8/28 3:41 PM <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UseRedissonDemo {

    @Autowired
    private RedissonService redissonService;

    @Test
    public void lockRedissonTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //1、第一个获取锁，执行10秒钟
        for (int i = 0; i < 2; i++) {
            executor.submit(() -> {
                try {
                    boolean locked = redissonService.tryLock(1, 10, TimeUnit.SECONDS);
                    if (locked) {
                        log.info("Success get the lock... {}", Thread.currentThread().getName());
                    } else {
                        log.info("Failed get the lock... {}", Thread.currentThread().getName());
                    }
                    latch.countDown();
                    Thread.sleep(2000);
                    redissonService.tryUnLock();
                } catch (InterruptedException ie) {
                    log.info("InterruptedException...");
                }
                log.info("CountDownLatch Count is {}...", latch.getCount());
            });
        }
        latch.await();
        log.info("Game Over...");
        executor.shutdown();
    }

    @Test
    public void redissonSigleTest() throws InterruptedException {
        boolean locked = redissonService.tryLock(5, 10, TimeUnit.SECONDS);
        if (locked) {
            System.out.println("Success get the lock...");
        } else {
            System.out.println("Failed get the lock...");
        }
        redissonService.tryUnLock();
    }

    @Test
    public void lockTest() {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);
        RLock lock = redisson.getLock("nezhaxiaozi");
        lock.lock(30, TimeUnit.SECONDS);
        System.out.println("get the lock");
        lock.unlock();
        redisson.shutdown();
    }
}
