package com.nezha.learn.demo.api;

import com.nezha.learn.demo.service.LockServiceZK;
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
    private LockServiceZK zkLockService;

    @RequestMapping(value = "/lock1")
    public String hello(){
        try {
            boolean getLock = zkLockService.acquire();
            if (getLock){
                //Do something
                logger.info("{},拿到zk锁并且睡眠了20秒",this.getClass().getName());
            }else {
                logger.info("获取锁失败");
            }

        }catch (Exception e){
            logger.info("1,并发竞争锁失败");
        }finally {
            try {
                zkLockService.release();
                logger.info("1,释放锁成功了。。。");
            }catch (Exception e){
                logger.info("1,释放锁失败");
            }
        }
        return "Hello World";
    }
}
