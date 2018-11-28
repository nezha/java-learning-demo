package com.nezha.learn.demo.service.impl;

import com.nezha.learn.demo.User;
import com.nezha.learn.demo.service.IUser;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/11/28 9:18 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
@Service
@Primary
public class UserImplB implements IUser {
    public User getUser() {
        return new User("BBBB",11);
    }
}
