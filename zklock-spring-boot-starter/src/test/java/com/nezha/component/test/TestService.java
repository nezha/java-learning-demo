package com.nezha.component.test;

import com.nezha.component.annotation.ZKlock;
import com.nezha.component.model.LockTimeoutStrategy;
import org.springframework.stereotype.Service;

/**
 * @author: nezha <br>
 * @Title: TestService <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: 测试类 <br>
 * @Date: 2019/9/19 11:21 AM <br>
 */
@Service
public class TestService {
    @ZKlock(waitTime = 1, name = "nezha", keys = {"#param"}, lockTimeoutStrategy = LockTimeoutStrategy.FAIL_FAST, customLockTimeoutStrategy = "lockFailure")
    public String getValue(String param) throws Exception {
        if ("sleep".equals(param)) {//线程休眠或者断点阻塞，达到一直占用锁的测试效果
            Thread.sleep(1000 * 3);
        }
        return "success";
    }

    private String lockFailure(String param) {
        return "===Failure===";
    }
}
