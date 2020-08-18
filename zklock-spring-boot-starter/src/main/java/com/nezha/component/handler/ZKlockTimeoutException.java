package com.nezha.component.handler;

/**
 * @author: nezha <br>
 * @Title: ZKlockTimeoutException <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: xxxxx <br>
 * @Date: 2019/9/18 7:37 PM <br>
 */
public class ZKlockTimeoutException extends RuntimeException {
    public ZKlockTimeoutException() {
    }

    public ZKlockTimeoutException(String message) {
        super(message);
    }

    public ZKlockTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
