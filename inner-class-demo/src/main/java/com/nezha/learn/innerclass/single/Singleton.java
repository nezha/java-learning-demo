package com.nezha.learn.innerclass.single;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: nezha <br>
 * @Title: Singleton <br>
 * @ProjectName: inner-class-demo <br>
 * @Description: xxxxx <br>
 * @Date: 2019/9/10 10:11 PM <br>
 */
@Slf4j
public class Singleton {
    static {
        System.out.println("Out Class Load");
    }
    private static class SingletonHolder{
        static {
            System.out.println("Inner Class Load");
        }
        public static Singleton instance = new Singleton();
    }
    private Singleton(){
        System.out.println("Singleton initialized...");
    }

    public Singleton(String say){
        log.info("Singleton Say:{}",say);
    }
    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }
}
