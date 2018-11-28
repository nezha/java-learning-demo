package com.nezha.learn.demo;

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
    @RequestMapping(value = "/hello")
    public String hello(){
        return "Hello World";
    }
}
