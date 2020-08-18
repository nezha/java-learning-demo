package com.nezha.component.core;

import com.nezha.component.annotation.ZKlock;
import com.nezha.component.handler.ZKlockInvocationException;
import com.nezha.component.lock.Lock;
import com.nezha.component.lock.LockFactory;
import com.nezha.component.model.LockInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: nezha <br>
 * @Title: ZKlockAspectHandler <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: 给添加@ZKLock切面加锁处理 <br>
 * @Date: 2019/9/18 7:54 PM <br>
 */
@Aspect
@Component
@Order(0)
@Slf4j
public class ZKlockAspectHandler {

    @Autowired
    private LockFactory lockFactory;

    @Autowired
    private LockInfoProvider lockInfoProvider;

    private ThreadLocal<LockRes> currentThreadLock = new ThreadLocal<>();

    /**
     * 上下文拦截
     * @param joinPoint
     * @return
     */
    @Around(value = "@annotation(zklock)")
    public Object around(ProceedingJoinPoint joinPoint, ZKlock zklock) throws Throwable{
        //构造LockInfo信息
        LockInfo lockInfo = lockInfoProvider.get(joinPoint,zklock);
        currentThreadLock.set(new LockRes(lockInfo, false));
        //根据LockInfo构造出具体的Lock类型
        Lock lock = lockFactory.getLock(lockInfo);
        //获取锁
        boolean lockRes = lock.acquire();
        //如果获取锁失败了，则进入失败的处理逻辑
        if(!lockRes) {
            if(log.isWarnEnabled()) {
                log.warn("Timeout while acquiring Lock({})", lockInfo.getName());
            }
            //如果自定义了获取锁失败的处理策略，则执行自定义的降级处理策略
            if(!StringUtils.isEmpty(zklock.customLockTimeoutStrategy())) {
                return handleCustomLockTimeout(zklock.customLockTimeoutStrategy(), joinPoint);
            } else {
                //否则执行预定义的执行策略
                //注意：如果没有指定预定义的策略，默认的策略为静默啥不做处理
                zklock.lockTimeoutStrategy().handle(lockInfo, lock, joinPoint);
            }
        }
        currentThreadLock.get().setLock(lock);
        currentThreadLock.get().setRes(true);
        return joinPoint.proceed();
    }

    /**
     * 返回后
     * @param joinPoint
     * @return
     */
    @AfterReturning(value = "@annotation(zklock)")
    public void afterReturning(JoinPoint joinPoint, ZKlock zklock) throws Throwable{
        releaseLock(zklock, joinPoint);
        cleanUpThreadLocal();
    }

    /**
     * 发生异常后
     * @param joinPoint
     * @return
     */
    @AfterThrowing(value = "@annotation(zklock)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, ZKlock zklock, Throwable ex) throws Throwable{
        releaseLock(zklock, joinPoint);
        cleanUpThreadLocal();
        throw ex;
    }

    /**
     * 处理自定义加锁超时
     * @param lockTimeoutHandler
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    private Object handleCustomLockTimeout(String lockTimeoutHandler, JoinPoint joinPoint) throws Throwable {

        // prepare invocation context
        Method currentMethod = ((MethodSignature)joinPoint.getSignature()).getMethod();
        Object target = joinPoint.getTarget();
        Method handleMethod = null;
        try {
            handleMethod = joinPoint.getTarget().getClass().getDeclaredMethod(lockTimeoutHandler, currentMethod.getParameterTypes());
            handleMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Illegal annotation param customLockTimeoutStrategy",e);
        }
        Object[] args = joinPoint.getArgs();

        // invoke
        Object res = null;
        try {
            res = handleMethod.invoke(target, args);
        } catch (IllegalAccessException e) {
            throw new ZKlockInvocationException("Fail to invoke custom lock timeout handler: " + lockTimeoutHandler ,e);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }

        return res;
    }

    /**
     * 释放锁
     * @param zklock
     * @param joinPoint
     */
    private void releaseLock(ZKlock zklock, JoinPoint joinPoint) throws Throwable{
        LockRes lockRes = currentThreadLock.get();
        if (lockRes.getRes()) {
            boolean releaseRes = currentThreadLock.get().getLock().release();
            // avoid release lock twice when exception happens below
            lockRes.setRes(false);
            if (!releaseRes) {
                handleReleaseTimeout(zklock, lockRes.getLockInfo(), joinPoint);
            }
        }
    }


    /**
     * 释放锁异常
     * @param klock
     * @param lockInfo
     * @param joinPoint
     * @throws Throwable
     */
    private void handleReleaseTimeout(ZKlock klock, LockInfo lockInfo, JoinPoint joinPoint) throws Throwable {

        if(log.isWarnEnabled()) {
            log.warn("Timeout while release Lock({})", lockInfo.getName());
        }

        if(!StringUtils.isEmpty(klock.customReleaseTimeoutStrategy())) {

            handleCustomReleaseTimeout(klock.customReleaseTimeoutStrategy(), joinPoint);

        } else {
            klock.releaseTimeoutStrategy().handle(lockInfo);
        }

    }


    /**
     * 处理自定义释放锁时已超时
     * @param releaseTimeoutHandler
     * @param joinPoint
     * @throws Throwable
     */
    private void handleCustomReleaseTimeout(String releaseTimeoutHandler, JoinPoint joinPoint) throws Throwable {

        Method currentMethod = ((MethodSignature)joinPoint.getSignature()).getMethod();
        Object target = joinPoint.getTarget();
        Method handleMethod = null;
        try {
            handleMethod = joinPoint.getTarget().getClass().getDeclaredMethod(releaseTimeoutHandler, currentMethod.getParameterTypes());
            handleMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Illegal annotation param customReleaseTimeoutStrategy",e);
        }
        Object[] args = joinPoint.getArgs();

        try {
            handleMethod.invoke(target, args);
        } catch (IllegalAccessException e) {
            throw new ZKlockInvocationException("Fail to invoke custom release timeout handler: " + releaseTimeoutHandler, e);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }


    private class LockRes {

        private LockInfo lockInfo;
        private Lock lock;
        private Boolean res;

        LockRes(LockInfo lockInfo, Boolean res) {
            this.lockInfo = lockInfo;
            this.res = res;
        }

        LockInfo getLockInfo() {
            return lockInfo;
        }

        public Lock getLock() {
            return lock;
        }

        public void setLock(Lock lock) {
            this.lock = lock;
        }

        Boolean getRes() {
            return res;
        }

        void setRes(Boolean res) {
            this.res = res;
        }

        void setLockInfo(LockInfo lockInfo) {
            this.lockInfo = lockInfo;
        }
    }

    /**
     * 避免内存泄露
     */
    private void cleanUpThreadLocal() {
        currentThreadLock.remove();
    }

}
