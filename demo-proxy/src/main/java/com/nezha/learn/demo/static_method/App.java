package com.nezha.learn.demo.static_method;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/2 10:32 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class App {
    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDao();

        //代理对象,把目标对象传给代理对象,建立代理关系
        UserDaoProxy proxy = new UserDaoProxy(target);

        proxy.save();//执行的是代理的方法
    }
}
