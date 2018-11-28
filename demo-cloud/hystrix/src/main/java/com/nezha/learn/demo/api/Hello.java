package com.nezha.learn.demo.api;

import com.nezha.learn.demo.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/11/23 1:49 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
@RestController
@RequestMapping("/Hello")
public class Hello {

    @RequestMapping("/user/{name}")
    public User login(@PathVariable("name") String name){
        User user = new User(name,23);
        return user;
    }
}
