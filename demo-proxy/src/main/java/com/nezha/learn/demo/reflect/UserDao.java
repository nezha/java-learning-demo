package com.nezha.learn.demo.reflect;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/2 10:30 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class UserDao implements IUserDao {
    @Override
    public void save() {
        System.out.println("----已经保存数据!----");
    }
}
