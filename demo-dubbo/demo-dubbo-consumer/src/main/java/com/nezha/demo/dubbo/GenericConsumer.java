package com.nezha.demo.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/20 9:57 AM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class GenericConsumer {
    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-demo-generic-consumer.xml"});
//        context.start();
//        GenericService genericService = (GenericService) context.getBean("demoService");

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        // 弱类型接口名
        reference.setInterface("com.nezha.demo.dubbo.DemoService");
        reference.setVersion("1.0.0");
        // 声明为泛化接口
        reference.setGeneric(true);

        //应用名称
        ApplicationConfig application = new ApplicationConfig("demo-consumer");
        reference.setApplication(application);

        //初始化dubbo注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress("127.0.0.1:2181");
        reference.setRegistry(registry);
        // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();

        // 基本类型以及Date,List,Map等不需要转换，直接调用
        Object result = genericService.$invoke("sayHello", new String[] {"java.lang.String"}, new Object[] {"nezha-world"});
        System.out.println("================="+result);
    }
}
