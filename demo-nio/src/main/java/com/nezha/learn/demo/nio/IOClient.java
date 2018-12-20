package com.nezha.learn.demo.nio;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/14 2:37 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class IOClient {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": 你好").getBytes());
                        socket.getOutputStream().flush();
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}
