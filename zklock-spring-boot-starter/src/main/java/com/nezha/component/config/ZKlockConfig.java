package com.nezha.component.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: nezha <br>
 * @Title: ZKlockConfig <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: 配置类 <br>
 * @Date: 2019/9/18 4:01 PM <br>
 */
@Data
@ConfigurationProperties(prefix = ZKlockConfig.PREFIX)
public class ZKlockConfig {
    /**
     * PREFIX：属性配置的时候的特定前缀。
     */
    public static final String PREFIX = "spring.zklock";
    //Zookeeper need this fields
    /**
     * hosts:zookeeper地址
     */
    private String hosts = "127.0.0.1:2181";
    /**
     * retry:重连次数默认5次
     */
    private Integer retry = 5;
    /**
     * connectTimeout:连接超时时间，默认30毫秒
     */
    private Integer connectTimeout = 30;
    /**
     * sessionTimeout:会话保持时长，默认300毫秒
     */
    private Integer sessionTimeout = 300;

    /**
     * waitTime:默认等待时间，1秒的等待时间
     */
    private long waitTime = 1;
    /**
     * leaseTime:默认释放时间，2秒的释放时间
     */
    private long leaseTime = 2;
}
