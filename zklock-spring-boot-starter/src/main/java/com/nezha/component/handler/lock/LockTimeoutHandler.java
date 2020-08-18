package com.nezha.component.handler.lock;

import com.nezha.component.lock.Lock;
import com.nezha.component.model.LockInfo;
import org.aspectj.lang.JoinPoint;

/**
 * @author: nezha <br>
 * @Title: LockTimeoutHandler <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: 获取锁超时的处理逻辑接口 <br>
 * @Date: 2019/9/18 7:44 PM <br>
 */
public interface LockTimeoutHandler {
    void handle(LockInfo lockInfo, Lock lock, JoinPoint joinPoint);
}
