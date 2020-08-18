## 基于zookeeper的分布式锁的starter

编写Starter非常简单，与编写一个普通的Spring Boot应用没有太大区别，总结如下：

```text
1.新建Maven项目，在项目的POM文件中定义使用的依赖；
2.新建配置类，写好配置项和默认的配置值，指明配置项前缀；
3.新建自动装配类，使用@Configuration和@Bean来进行自动装配；
4.新建spring.factories文件，指定Starter的自动装配类；
```




## 参考文献

从零开始开发一个Spring Boot Starter： https://www.jianshu.com/p/bbf439c8a203


## 感谢：

https://github.com/kekingcn/spring-boot-klock-starter
代码是基于Klock的基础上改为zookeeper分布式锁的