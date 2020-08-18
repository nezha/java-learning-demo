package com.nezha.component.core;

import com.nezha.component.annotation.ZKlock;
import com.nezha.component.config.ZKlockConfig;
import com.nezha.component.model.LockInfo;
import com.nezha.component.model.LockType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: nezha <br>
 * @Title: ZKlockAspectHandler <br>
 * @ProjectName: zklock-spring-boot-starter <br>
 * @Description: 构造LockInfo锁信息 <br>
 * @Date: 2019/9/18 7:54 PM <br>
 * 构造锁的key也在这。
 */
@Slf4j
public class LockInfoProvider {

    /**
     * 建议增加项目名称做路径分割
     */
    private static final String LOCK_PATH_PREFIX = "/lock";
    private static final String LOCK_PATH_SEPARATOR = "/";


    @Autowired
    private ZKlockConfig klockConfig;

    @Autowired
    private BusinessKeyProvider businessKeyProvider;

    LockInfo get(ProceedingJoinPoint joinPoint, ZKlock klock) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LockType type= klock.lockType();
        String businessKeyName=businessKeyProvider.getKeyName(joinPoint,klock);
        //锁的名字，锁的粒度就是这里控制的
        String lockName = LOCK_PATH_PREFIX + LOCK_PATH_SEPARATOR + getName(klock.name(), signature) + businessKeyName;
        long waitTime = getWaitTime(klock);
        long leaseTime = getLeaseTime(klock);
        //如果占用锁的时间设计不合理，则打印相应的警告提示
        if(leaseTime == -1 && log.isWarnEnabled()) {
            log.warn("Trying to acquire Lock({}) with no expiration, " +
                        "Klock will keep prolong the lock expiration while the lock is still holding by current thread. " +
                        "This may cause dead lock in some circumstances.", lockName);
        }
        return new LockInfo(type,lockName,waitTime,leaseTime);
    }

    /**
     * 获取锁的name，如果没有指定，则按全类名拼接方法名处理
     * @param annotationName
     * @param signature
     * @return
     */
    private String getName(String annotationName, MethodSignature signature) {
        if (annotationName.isEmpty()) {
            return String.format("%s.%s", signature.getDeclaringTypeName(), signature.getMethod().getName());
        } else {
            return annotationName;
        }
    }


    private long getWaitTime(ZKlock lock) {
        return lock.waitTime() == Long.MIN_VALUE ?
                klockConfig.getWaitTime() : lock.waitTime();
    }

    private long getLeaseTime(ZKlock lock) {
        return lock.leaseTime() == Long.MIN_VALUE ?
                klockConfig.getLeaseTime() : lock.leaseTime();
    }
}
