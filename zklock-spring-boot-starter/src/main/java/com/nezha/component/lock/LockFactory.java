package com.nezha.component.lock;

import com.nezha.component.model.LockInfo;
import org.apache.curator.framework.CuratorFramework;

import javax.annotation.Resource;

/**
 * @author: nezha <br>
 * @Title: LockFactory <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: 实例化各种锁 <br>
 * @Date: 2019/9/18 7:13 PM <br>
 */
public class LockFactory {

    @Resource(name = "zkClient")
    private CuratorFramework zkClient;

    public Lock getLock(LockInfo lockInfo) {
        switch (lockInfo.getType()) {
            case Reentrant:
                return new ReentrantLock(zkClient, lockInfo);
            default:
                return new ReentrantLock(zkClient, lockInfo);
        }
    }

}
