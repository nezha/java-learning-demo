package com.nezha.learn.demo.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/14 10:23 AM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class DateUtil {
    public static void main(String[] args) {
        //1.格式化时间输出
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/hh-mm-ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = formatter.format(localDateTime);
        System.out.println("当前时间是："+time);
        //2.Instant获取当地的时间
        Instant now = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));
        System.out.println("Instant下的now:"+now);
        //3.使用LocalDateTime增加秒、分钟、小时、周等...
        localDateTime = localDateTime.plusMinutes(1);
        System.out.println("LocalDateTime增加一分钟的时间:"+formatter.format(localDateTime));
        //4.根据使用LocalDateTime拿到时间戳
        long timeStamp = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("当前的时间戳是："+timeStamp);

    }
}
