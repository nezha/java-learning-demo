package com.nezha.learn.demo.controller;

import com.nezha.learn.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {
    @Autowired
    private UserService userService;

    @GetMapping(value="/get/{id}")
    public int printNumber(@PathVariable int id) {
        // url的id可通过@PathVariable绑定到参数中
        userService.getNumberSyncRest(id);
        return 200;
    }

    @GetMapping(value="/getNorm/{id}")
    public int printNormalNumber(@PathVariable int id) {
        // url的id可通过@PathVariable绑定到参数中
        userService.getNumberSyncRestNormal(id);
        return 200;
    }
}
