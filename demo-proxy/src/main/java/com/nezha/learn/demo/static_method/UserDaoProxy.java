package com.nezha.learn.demo.static_method;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/2 10:30 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class UserDaoProxy implements IUserDao {
    //接收保存目标对象
    private IUserDao target;
    public UserDaoProxy(IUserDao target){
        this.target=target;
    }
    @Override
    public void save() {
        System.out.println("开始事务...");
        target.save();//执行目标对象的方法
        System.out.println("提交事务...");
    }
}
