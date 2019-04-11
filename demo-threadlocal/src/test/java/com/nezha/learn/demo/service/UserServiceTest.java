package com.nezha.learn.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@EnableAsync
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private Random random = new Random();

    @Rule
    public ContiPerfRule contiPerfRule = new ContiPerfRule();

    @Test
    public void getNumber() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            userService.getNumberAsync();
            log.info("第{}个任务", i);
        }
        Thread.sleep(10000);
    }

    @Test
    public void getNumberAsyncSyncLock() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            userService.getNumberAsyncSyncLock();
            log.info("第{}个任务", i);
        }
        Thread.sleep(10000);
    }

    @Test
    public void getNumberAsyncReeLock() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            userService.getNumberAsyncReeLock();
            log.info("第{}个任务", i);
        }
        Thread.sleep(10000);
    }

    @Test
    //10个线程 执行10次
    @PerfTest(invocations = 10, threads = 10)
    public void getNumberSyncRest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/get/" + random.nextInt(20)));
    }

    @Test
    //10个线程 执行10次
    @PerfTest(invocations = 10, threads = 10)
    public void getNumberSyncRestNormal() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getNorm/" + random.nextInt(20)));
    }
}