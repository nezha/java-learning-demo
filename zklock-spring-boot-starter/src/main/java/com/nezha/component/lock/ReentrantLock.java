package com.nezha.component.lock;

import com.nezha.component.model.LockInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * @author: nezha <br>
 * @Title: ReentrantLock <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: 实现可重入锁 <br>
 * @Date: 2019/9/18 7:13 PM <br>
 */
public class ReentrantLock implements Lock {

    private InterProcessMutex lock;

    private final LockInfo lockInfo;

    private CuratorFramework zkClient;

    public ReentrantLock(CuratorFramework zkClient, LockInfo lockInfo) {
        this.zkClient = zkClient;
        this.lockInfo = lockInfo;
    }

    @Override
    public boolean acquire() {
        try {
            lock = new InterProcessMutex(zkClient, lockInfo.getName());
            return lock.acquire(lockInfo.getWaitTime(), TimeUnit.SECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean release() {
        if (lock.isAcquiredInThisProcess()) {
            try {
                lock.release();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
