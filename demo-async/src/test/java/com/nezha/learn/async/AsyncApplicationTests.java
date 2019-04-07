package com.nezha.learn.async;

import com.nezha.learn.async.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
public class AsyncApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    public void testAsync() throws InterruptedException{
        for (int i = 0; i < 20; i++) {
            userService.sayHello(i);
            Thread.sleep(1500);
        }
    }
}
