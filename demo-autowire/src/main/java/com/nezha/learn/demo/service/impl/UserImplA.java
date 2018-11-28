package com.nezha.learn.demo.service.impl;

import com.nezha.learn.demo.User;
import com.nezha.learn.demo.service.IUser;
import org.springframework.stereotype.Service;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/11/28 9:18 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
@Service
public class UserImplA implements IUser {
    public User getUser() {
        return new User("AAAA",11);
    }
}
