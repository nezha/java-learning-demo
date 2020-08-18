package com.nezha.component.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: nezha <br>
 * @Title: ZKlockKey <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: 指定key名称 <br>
 * @Date: 2019/9/18 3:41 PM <br>
 */
@Target(value = {ElementType.PARAMETER, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ZKlockKey {
    String value() default "";
}
