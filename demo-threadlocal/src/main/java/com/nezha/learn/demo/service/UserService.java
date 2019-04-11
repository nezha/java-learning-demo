package com.nezha.learn.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class UserService {
    private static final ThreadLocal<Long> numbers = new ThreadLocal<>();
    private Long normal = new Long(1);
    private ReentrantLock reentrantLock = new ReentrantLock();


    /**
     * 模拟高并发场景下的请求，观察ThreadLocal是否线程安全
     * @param number
     */
    public void getNumberSyncRest(int number) {
        Long id = Thread.currentThread().getId();
        log.info(">>>>>Thread ID:{},input number:{}", id, number);
        numbers.set(id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        log.info(">>>>>Thread ID:{},ThreadLocal content:{},input number:{}", id, numbers.get(), number);
    }

    /**
     * 观察高并发场景下，普通共享变量是否线程安全
     * @param number
     */
    public void getNumberSyncRestNormal(int number) {
        Long id = Thread.currentThread().getId();
        log.info(">>>>>Thread ID:{},input number:{}", id, number);
        normal = id;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {

        }
        log.info(">>>>>Thread ID:{},Normal content:{},input number:{}", id, normal, number);
    }


    /**
     * 异步状态下测试ThreadLocal和普通变量的线程安全情况
     */
    @Async
    public void getNumberAsync() {
        Long id = Thread.currentThread().getId();
        log.info(">>>>Thread ID:{}", id);
        numbers.set(id);
        normal = id;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        log.info(">>>>Thread ID:{},ThreadLocal content:{},normal:{}", id, numbers.get(), normal);
    }

    /**
     * 测试可重入锁的并发安全性
     */
    @Async
    public void getNumberAsyncReeLock() {
        reentrantLock.lock();
        Long id = Thread.currentThread().getId();
        log.info(">>>>>Thread ID:{}", id);
        numbers.set(id);
        normal = id;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        log.info(">>>>>Thread ID:{},ThreadLocal content:{},normal:{}", id, numbers.get(), normal);
        reentrantLock.unlock();
    }

    /**
     * 测试synchronized锁的线程安全性
     */
    @Async
    public void getNumberAsyncSyncLock() {
        synchronized (normal){
            Long id = Thread.currentThread().getId();
            log.info(">>>>>Thread ID:{}", id);
            numbers.set(id);
            normal = id;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            log.info(">>>>>Thread ID:{},ThreadLocal content:{},normal:{}", id, numbers.get(), normal);
        }
    }
}
