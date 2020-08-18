package com.nezha.component;

import com.nezha.component.config.ZKlockConfig;
import com.nezha.component.core.BusinessKeyProvider;
import com.nezha.component.core.LockInfoProvider;
import com.nezha.component.core.ZKlockAspectHandler;
import com.nezha.component.lock.LockFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * @author: nezha <br>
 * @Title: ZKlockAutoConfiguration <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: zklock的自动装配类 <br>
 * @Date: 2019/9/18 4:16 PM <br>
 */

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = ZKlockConfig.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(ZKlockConfig.class)
@Import({ZKlockAspectHandler.class})
public class ZKlockAutoConfiguration {

    @Resource
    private ZKlockConfig zKlockConfig;

    @Bean(name = "zkClient", destroyMethod = "close")
    @ConditionalOnMissingBean
    CuratorFramework zkClient(){
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, zKlockConfig.getRetry());
        CuratorFramework zk = CuratorFrameworkFactory.builder()
                .connectString(zKlockConfig.getHosts())
                .connectionTimeoutMs(zKlockConfig.getConnectTimeout())
                .sessionTimeoutMs(zKlockConfig.getSessionTimeout())
                .retryPolicy(retryPolicy)
                .build();
        zk.start();
        return zk;
    }

    @Bean
    public LockInfoProvider lockInfoProvider(){
        return new LockInfoProvider();
    }

    @Bean
    public BusinessKeyProvider businessKeyProvider(){
        return new BusinessKeyProvider();
    }

    @Bean
    public LockFactory lockFactory(){
        return new LockFactory();
    }
}
