package com.nezha.learn.demo;

import com.nezha.learn.demo.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/11/28 9:57 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */

@RestController
public class RestApi {

    @Autowired
    @Qualifier("userImplA")
    IUser iUser;

    @RequestMapping(value = "/hello")
    public String hello(){
        return "Hello World, demo AutoWired," + System.currentTimeMillis();
    }

    @RequestMapping(value = "/get")
    public User get(){
        return iUser.getUser();
    }
}
