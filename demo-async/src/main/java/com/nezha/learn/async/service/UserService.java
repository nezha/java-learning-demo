package com.nezha.learn.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Description: xxxxx <br>
 * @Date: 2019/4/7 9:56 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
@Service
public class UserService {

    @Async
    public void sayHello(int number) throws InterruptedException{
        Thread.sleep(2000L);
        System.out.println(">>>>"+number+">>>>");
    }
}
