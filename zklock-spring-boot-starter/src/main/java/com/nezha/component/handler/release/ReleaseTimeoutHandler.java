package com.nezha.component.handler.release;

import com.nezha.component.model.LockInfo;

/**
 * @author: nezha <br>
 * @Title: ReleaseTimeoutHandler <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: 释放锁超时的处理逻辑接口 <br>
 * @Date: 2019/9/18 7:48 PM <br>
 */
public interface ReleaseTimeoutHandler {
    void handle(LockInfo lockInfo);
}
