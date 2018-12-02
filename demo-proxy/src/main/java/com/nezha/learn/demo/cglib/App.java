package com.nezha.learn.demo.cglib;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/2 10:58 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class App {

    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDao();
        //代理对象
        UserDao proxy = (UserDao)new ProxyFactory(target).getProxyInstance();
        //执行代理对象的方法
        proxy.save();
    }
}
