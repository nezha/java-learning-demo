package com.nezha.learn.demo.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: nezha <br>
 * @Title: RedissonService <br>
 * @ProjectName: demo-redis <br>
 * @Description: xxxxx <br>
 * @Date: 2019/8/28 11:36 PM <br>
 */

@Service
public class RedissonService {

    @Autowired
    private RedissonClient redissonClient;

    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        RLock lock = redissonClient.getLock("nezhaxiaozi");
        return lock.tryLock(leaseTime, unit);
    }

    public void tryUnLock() throws InterruptedException {
        RLock lock = redissonClient.getLock("nezhaxiaozi");
        if (lock.isLocked()) {
            lock.unlock();
        }
    }

}
