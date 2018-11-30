package com.nezha.learn.demo.util;

import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;

public class RedisTool {

    public static boolean getLock(StringRedisTemplate stringRedisTemplate, Environment env, String key, String requestId, Long expiresTime) {
        DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>(env.getProperty("lua.lockScript"), Long.class);
        Long result = stringRedisTemplate.execute(longDefaultRedisScript, Collections.singletonList(key), requestId,String.valueOf(expiresTime));
        return result == 1;
    }

    public static boolean releaseLock(StringRedisTemplate stringRedisTemplate, Environment env, String key, String requestId) {
        DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>(env.getProperty("lua.releaseLockScript"), Long.class);
        Long result = stringRedisTemplate.execute(longDefaultRedisScript, Collections.singletonList(key), requestId);
        return result == 1;
    }
}

