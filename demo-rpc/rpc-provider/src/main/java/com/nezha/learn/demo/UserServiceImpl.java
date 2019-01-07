package com.nezha.learn.demo;

/**
 * @Description: 实现接口的类 <br>
 * @Date: 2019/1/4 3:07 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class UserServiceImpl implements IUserService {
    @Override
    public User findById(Long id) {
        User user = new User();
        user.setName("nezha");
        user.setAge(123);
        return user;
    }
}
