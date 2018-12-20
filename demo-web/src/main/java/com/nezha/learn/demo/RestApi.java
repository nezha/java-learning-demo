package com.nezha.learn.demo;

import com.nezha.learn.demo.service.LockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/11/28 9:57 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */

@RestController
public class RestApi {
    private final static Logger logger = LoggerFactory.getLogger(RestApi.class);

    @Autowired
    private LockService zkLockService;
    @RequestMapping(value = "/lock2")
    public String hello(){
        try {
            zkLockService.acquire();
            logger.info("2拿到zk锁");
        }catch (Exception e){
            logger.info("2,并发竞争锁失败");
        }finally {
            try {
                zkLockService.release();
                logger.info("2,释放锁成功了。。。");
            }catch (Exception e){
                logger.info("2,释放锁失败");
            }
        }
        return "Hello World2";
    }
}