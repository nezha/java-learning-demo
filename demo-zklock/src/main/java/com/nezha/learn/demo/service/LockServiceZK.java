package com.nezha.learn.demo.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/4 2:19 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */

@Service
public class LockServiceZK {
    private final static Logger logger = LoggerFactory.getLogger(LockServiceZK.class);
    private CuratorFramework zk;
    private InterProcessMutex lock;

    @Value("${zookeeper.connect}")
    private String hosts;
    @Value("${zookeeper.retry}")
    private Integer retry = 3;
    @Value("${zookeeper.timeout.connect}")
    private Integer connectTimeout = 30;
    @Value(("${zookeeper.timeout.session}"))
    private Integer sessionTimeout = 300;
    @Value("${zookeeper.root}")
    private String root;

    @PostConstruct
    private void start() throws Exception {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, retry);
        zk = CuratorFrameworkFactory.builder()
                .connectString(hosts)
                .connectionTimeoutMs(connectTimeout)
                .sessionTimeoutMs(sessionTimeout)
                .retryPolicy(retryPolicy)
                .build();
        zk.start();
        lock = new InterProcessMutex(zk, Paths.get(root, "/lock").toString());
    }

    @PreDestroy
    private void stop() throws Exception {
        if(zk != null) {
            zk.close();
        }
    }

    public boolean acquire() throws Exception {
        return lock.acquire(1,TimeUnit.SECONDS);
    }

    public void release() throws Exception {
        lock.release();
    }

}
