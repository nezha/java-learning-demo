package com.nezha.component.lock;

/**
 * @author: nezha <br>
 * @Title: Lock <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: Lock接口 <br>
 * @Date: 2019/9/18 7:12 PM <br>
 */
public interface Lock {
    boolean acquire();

    boolean release();
}
