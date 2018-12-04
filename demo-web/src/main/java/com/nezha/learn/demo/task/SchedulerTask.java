package com.nezha.learn.demo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/11/29 11:06 AM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
@Component
public class SchedulerTask {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(SchedulerTask.class);
//    @Scheduled(fixedRate = 6000)
    public void reportCurrentTime() {
        logger.info("现在时间：" + dateFormat.format(new Date()));
    }
}
