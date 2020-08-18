package com.nezha.component;

import com.nezha.component.config.ZKlockConfig;
import com.nezha.component.core.BusinessKeyProvider;
import com.nezha.component.core.LockInfoProvider;
import com.nezha.component.core.ZKlockAspectHandler;
import com.nezha.component.lock.LockFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: nezha <br>
 * @Title: ZKlockConfiguration <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: 适用于内部低版本spring mvc项目配置 <br>
 * @Date: 2019/9/19 10:54 AM <br>
 */

@Configuration
@Import({ZKlockAspectHandler.class})
public class ZKlockConfiguration {
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
    @Bean
    public ZKlockConfig klockConfig(){
        return new ZKlockConfig();
    }
}
