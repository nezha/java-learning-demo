package com.nezha.learn.demo.aop;

import com.nezha.learn.demo.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/5 3:14 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
@Aspect
@Component
@Slf4j
public class HelloAspect {

    @Autowired
    private ApplicationContext applicationContext;

    @Pointcut("execution(* com.nezha.learn.demo.api.Hello.login(..))")
//    @Pointcut("@annotation(org.springframework.stereotype.Service)")
    private void pointCutLogin(){ }


    @Before(value = "pointCutLogin()")
    public void before(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.info("before开始执行--->>>",className,"/",methodName,",参数是：",String.valueOf(args[0]));
    }

    @After(value = "pointCutLogin()")
    public void after(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.info("after开始执行--->>>"+className+"/"+methodName+",参数是："+String.valueOf(args[0]));
    }

    @Around(value = "pointCutLogin()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        Object[] args = proceedingJoinPoint.getArgs();
        String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        log.info("Around开始执行--->>>"+className+"/"+methodName+",参数是："+String.valueOf(args[0]));
        Object[] input = {"nezha"};
        Object proceed = null;
        try {
            //这里是执行方法的
            proceed = proceedingJoinPoint.proceed(input);
        }catch (Throwable t){
            t.printStackTrace();
        }
        log.info("===获得的proceed结果是："+(User)proceed);
        //！！！这边如果不把proceed返回出去，拦截的方法也不会进行结果返回的！
        return proceed;

    }
}
