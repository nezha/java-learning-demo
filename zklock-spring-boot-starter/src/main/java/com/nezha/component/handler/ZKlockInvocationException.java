package com.nezha.component.handler;

/**
 * @author: nezha <br>
 * @Title: ZKlockInvocationException <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: 执行错误 <br>
 * @Date: 2019/9/18 7:38 PM <br>
 */
public class ZKlockInvocationException extends RuntimeException{
    public ZKlockInvocationException() {
    }

    public ZKlockInvocationException(String message) {
        super(message);
    }

    public ZKlockInvocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
