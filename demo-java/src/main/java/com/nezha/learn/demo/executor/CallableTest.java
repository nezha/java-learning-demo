package com.nezha.learn.demo.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: nezha <br>
 * @Title: CallableTest <br>
 * @ProjectName: demo <br>
 * @Description: xxxxx <br>
 * @Date: 2020/8/18 10:22 PM <br>
 */
public class CallableTest implements Callable {
    @Override
    public Object call() throws Exception {
        String res = "Current Thread is:" + Thread.currentThread().getName() + ",Random number is:" + ThreadLocalRandom.current().nextInt() + "--" + System.currentTimeMillis();
        System.out.println(res);
        return res;
    }
}
