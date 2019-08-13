package com.nezha.guava.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.util.StopWatch;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author: nezha <br>
 * @Title: GuavaMain <br>
 * @ProjectName: demo-guava <br>
 * @Description: main函数 <br>
 * @Date: 2019/8/6 1:49 PM <br>
 */
public class GuavaMain {
    public static void main(String[] args) {
        cacheWriteTest();
    }


    /**
     * 压力测试
     * 1000000个
     */
    public static void cacheWriteTest() {
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(1000000)
                .recordStats()
                .build();
        StopWatch stopWatch = new StopWatch(UUID.randomUUID().toString());
        stopWatch.start("put operate");
        for (int i = 0; i < 1000000; i++) {
            cache.put(i, i);
        }
        stopWatch.stop();
        stopWatch.start("get operate");
        for (int i = 0; i < 1000000; i++) {
            cache.getIfPresent(ThreadLocalRandom.current().nextInt(1000000));
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(cache.stats());

    }

    /**
     * 基本操作
     */
    public static void cacheMethod1() {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.put(1, "a");
        System.out.println(cache.getIfPresent(1));
        System.out.println(cache.getIfPresent(2));
    }

    /**
     * 默认值返回
     */
    public static void cacheMethod2() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                return "key-" + key;
            }
        });
        cache.put("1", "a");
        System.out.println(cache.getIfPresent("1"));
        try {
            System.out.println(cache.get("2"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缓存兜底
     */
    public static void cacheMethod3() {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.put(1, "a");
        cache.put(2, "b");
        System.out.println(cache.getIfPresent(1));
        try {
            String value = cache.get(3, () -> "call world");
            System.out.println(value);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基于容量的回收:
     * 基于LRU算法
     */
    public static void cacheMethod4() {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().maximumSize(4).recordStats().build();
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        cache.put(4, "d");
        System.out.println(cache.asMap());
        System.out.println(cache.getIfPresent(1));
        cache.put(5, "e");
        cache.getIfPresent(6);
        cache.getIfPresent(7);
        System.out.println(cache.asMap());
        System.out.println(cache.stats().toString());
    }

    /**
     * 基于容量的回收:
     * 基于LRU算法
     * 通过增加
     */
    public static void cacheMethod5() {
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder().maximumWeight(100)
                .weigher((key, value) ->
                        {
                            if ((Integer) value % 2 == 0) {
                                return 20;
                            } else {
                                return 5;
                            }
                        }
                ).build();
        //放偶数
        cache.invalidateAll();
        for (int i = 0; i <= 20; i += 2) {
            cache.put(i, i);
        }
        System.out.println(cache.asMap());
        cache.invalidateAll();
        for (int i = 1; i <= 10; i++) {
            cache.put(i, i);
        }
        System.out.println(cache.asMap());
    }

    /**
     * 基于时间的策略
     * 写入之后3秒过期
     */
    public static void cacheMethod6() {
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();
        cache.put(1, 1);
        System.out.println(cache.asMap());
        try {
            Thread.sleep(2000);
            System.out.println(cache.getIfPresent(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.asMap());
    }

    /**
     * 基于时间的策略
     * access之后3秒过期
     */
    public static void cacheMethod7() {
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder().expireAfterAccess(3, TimeUnit.SECONDS).build();
        cache.put(1, 1);
        System.out.println(cache.asMap());
        try {
            Thread.sleep(2000);
            System.out.println(cache.getIfPresent(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.asMap());
    }

    /**
     * 测试通知和监听器
     */
    public static void cacheMethod8() {
        LoadingCache<Integer, Integer> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .removalListener(
                        //移除的时候刷新
                        notification -> System.out.println("remove key[" + notification.getKey() + "],value[" + notification.getValue() + "],remove reason[" + notification.getCause() + "]")
                )
                .build(new CacheLoader<Integer, Integer>() {
                    //设置默认值
                    @Override
                    public Integer load(Integer key) throws Exception {
                        return 0;
                    }
                });
        cache.put(1, 1);
        //尝试获取没有的key
        try {
            System.out.println(cache.get(2));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //打印当前缓存
        System.out.println(cache.asMap());
        cache.invalidate(2);
        try {
            //过期key值
            Thread.sleep(4000);
            System.out.println(cache.getUnchecked(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.asMap());
    }

}
