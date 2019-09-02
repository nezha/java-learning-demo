package com.nezha.learn.demo.service;


import com.nezha.learn.demo.utils.CuratorZKClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: nezha <br>
 * @Title: LockServiceZKTest <br>
 * @ProjectName: demo-zklock <br>
 * @Description: xxxxx <br>
 * @Date: 2019/9/2 1:29 PM <br>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class LockServiceZKTest {

    @Autowired
    private CuratorZKClient zkClient;

    @Test
    public void creatNodeTest() {
        String path = "/lock/";
        String pathId = zkClient.createEphemeralSequential(path);
        log.info("====path id is:{}", pathId);
        pathId = zkClient.createEphemeralSequential(path);
        log.info("====path id is:{}", pathId);
        pathId = zkClient.createEphemeralSequential(path);
        log.info("====path id is:{}", pathId);
        pathId = zkClient.createEphemeralSequential(path);
        log.info("====path id is:{}", pathId);
        List<String> list = zkClient.getChildren("/lock");
        log.info("Children list is : {}", list);
    }

}