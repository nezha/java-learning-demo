package com.nezha.guava.service;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: nezha <br>
 * @Title: RatelimiterMain <br>
 * @ProjectName: demo-guava <br>
 * @Description: 测试guava的限流工具 <br>
 * @Date: 2019/8/12 10:34 PM <br>
 */
@Slf4j
public class RatelimiterMain {

    public static void main(String[] args) throws InterruptedException{
        limiterMethod3();
    }

    public static void limiterMethod1() throws InterruptedException{
        RateLimiter limiter1 = RateLimiter.create(1);
        for (int i = 0; i < 10; i++) {
            boolean ownLock = limiter1.tryAcquire();
            log.info("Bursty-TryAcquire, TimeStamp:{} ,get the lock:{}", System.currentTimeMillis(), ownLock);
        }

        RateLimiter limiter2 = RateLimiter.create(1, 1, TimeUnit.SECONDS);
        for (int i = 0; i < 10; i++) {
            boolean ownLock = limiter2.tryAcquire();
            log.info("WarmingUp-TryAcquire, TimeStamp:{},get the lock:{}", System.currentTimeMillis(), ownLock);
        }
    }

    public static void limiterMethod2() throws InterruptedException{
        RateLimiter limiter1 = RateLimiter.create(1);
        TimeUnit.SECONDS.sleep(2);
        StopWatch stopWatch = new StopWatch();
        for (int i = 0; i < 10; i++) {
            stopWatch.start();
            limiter1.acquire(2);
            stopWatch.stop();
            log.info("Bursty-Acquire, cost time:{}", stopWatch.getLastTaskTimeMillis());
        }

        RateLimiter limiter2 = RateLimiter.create(2, 2, TimeUnit.SECONDS);
        for (int i = 0; i < 10; i++) {
            stopWatch.start();
            limiter2.acquire();
            stopWatch.stop();
            log.info("WarmingUp-Acquire, cost time:{}", stopWatch.getLastTaskTimeMillis());
        }
    }

    public static void limiterMethod3() throws InterruptedException{
        RateLimiter limiter1 = RateLimiter.create(1);
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            service.submit(() -> {
                latch.countDown();
                System.out.println("time "+System.currentTimeMillis()+"ms :"+limiter1.acquire());
            });
            System.out.println("countdown:" + latch.getCount());
        }
        latch.await();
        System.out.println("countdown over");
        service.shutdown();
    }


}
