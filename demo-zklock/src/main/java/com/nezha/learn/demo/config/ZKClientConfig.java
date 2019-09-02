package com.nezha.learn.demo.config;

import com.nezha.learn.demo.utils.CuratorZKClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: nezha <br>
 * @Title: ZKClientConfig <br>
 * @ProjectName: demo-zklock <br>
 * @Description: xxxxx <br>
 * @Date: 2019/9/2 11:14 AM <br>
 */

@Configuration
public class ZKClientConfig {
    @Value("${zookeeper.connect}")
    private String zkAddress;

    @Bean(value = "zkClient")
    public CuratorZKClient zkClient(){
        return new CuratorZKClient();
    }
}
