package com.nezha.learn.innerclass.single;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author: nezha <br>
 * @Title: SingletonTest <br>
 * @ProjectName: inner-class-demo <br>
 * @Description: xxxxx <br>
 * @Date: 2019/9/10 10:16 PM <br>
 */
@Slf4j
public class SingletonTest {

    @Test
    public void testSingle(){
        //1. 没有调用静态内部类的时候，静态内部类不会加载到内存中。
        Singleton singleton = new Singleton("nezha");

        //2.通过静态内部类来实例化外部类，是线程安全的
        Singleton singleton1 = Singleton.getInstance();

        //3.普通类的构造方法是在初始化对象的时候执行的,而访问静态变量的时候是不需要的.
        System.out.println(Button.number);
        Button button = new Button();

    }
}
