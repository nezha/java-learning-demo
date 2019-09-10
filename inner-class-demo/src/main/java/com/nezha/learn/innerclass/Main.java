package com.nezha.learn.innerclass;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: nezha <br>
 * @Title: Main <br>
 * @ProjectName: inner-class-demo <br>
 * @Description: xxxxx <br>
 * @Date: 2019/9/9 10:13 PM <br>
 */
public class Main {
    @Test
    public void testInnerClass(){
        //非静态内部类 创建方式1
        ClassOuter.InnerClass innerClass = new ClassOuter().new InnerClass();
        //非静态内部类 创建方式2
        ClassOuter outer = new ClassOuter();
        ClassOuter.InnerClass inner = outer.new InnerClass();
        //静态内部类的创建方式
        ClassOuter.StaticInnerClass staticInnerClass = new ClassOuter.StaticInnerClass();
        //非静态内部类修改外部类非静态属性
        inner.add();
        System.out.println(outer.getNoStaticInt());
        outer.testFunctionClass("Hello");
    }
}

