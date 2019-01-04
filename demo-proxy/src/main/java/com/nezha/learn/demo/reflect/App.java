package com.nezha.learn.demo.reflect;


/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/2 10:32 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class App {
    public static void main(String[] args) {
        // 目标对象
        IUserDao target = new UserDao();
        // 【原始的类型 class UserDao】
        System.out.println(target.getClass());

        // 给目标对象，创建代理对象
        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstanceLambda();
        // class $Proxy0   内存中动态生成的代理对象
        System.out.println(proxy.getClass());

        // 执行方法   【代理对象】
        proxy.save("nezha",25);
    }
}
