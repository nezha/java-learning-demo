
# guava cache
文章博客：<https://www.jianshu.com/p/b65331dcc7e6>

## 主要的类别

1. 过期策略 
2. 填充策略（Population）
3. 驱逐策略（eviction）
4. 移除监听器（Removal）
5. 统计（Statistics）


## 1.过期策略

```
expireAfterWrite
expireAfterAccess
```

## 2.填充策略（Population）

```
手动填充
默认值填充：CacheLoader#load或者Callable
```


## 3.驱逐策略（eviction）

```
基于大小（size-based）
基于时间（Time-based）
```

## 4. 移除监听器（Removal）

```
驱逐（eviction）：由于满足了某种驱逐策略，后台自动进行的删除操作
无效（invalidation）：表示由调用方手动删除缓存
移除（removal）：监听驱逐或无效操作的监听器
手动删除缓存：
```
 

## 5. 统计（Statistics）

```
hitRate()：返回命中与请求的比率
hitCount(): 返回命中缓存的总数
evictionCount()：缓存逐出的数量
averageLoadPenalty()：加载新值所花费的平均时间
```

## 参考文献

[guava cache 简单入门](https://www.jianshu.com/p/5299f5a11bd5)

[深入解密来自未来的缓存-Caffeine](https://juejin.im/post/5b8df63c6fb9a019e04ebaf4)


# guava RateLimiter

> Guava有两种限流模式，一种为稳定模式(SmoothBursty:令牌生成速度恒定)，一种为渐进模式(SmoothWarmingUp:令牌生成速度缓慢提升直到维持在一个稳定值) 两种模式实现思路类似，主要区别在等待时间的计算上，本篇重点介绍SmoothBursty

## 1、Bursty

### TryAcquire

```java
RateLimiter limiter1 = RateLimiter.create(5);
for (int i = 0; i < 10; i++) {
    boolean ownLock = limiter1.tryAcquire();
    log.info("Bursty-TryAcquire, TimeStamp:{} ,get the lock:{}", System.currentTimeMillis(), ownLock);
}
```
### Acquire

```java
RateLimiter limiter1 = RateLimiter.create(5);
StopWatch stopWatch = new StopWatch();
for (int i = 0; i < 10; i++) {
    stopWatch.start();
    limiter1.acquire();
    stopWatch.stop();
    log.info("Bursty-TryAcquire, cost time:{}", stopWatch.getLastTaskTimeMillis());
}
```

## 2、WarmingUp

### TryAcquire

```java
RateLimiter limiter2 = RateLimiter.create(5, 5, TimeUnit.SECONDS);
for (int i = 0; i < 10; i++) {
    boolean ownLock = limiter2.tryAcquire();
    log.info("WarmingUp-TryAcquire, TimeStamp:{},get the lock:{}", System.currentTimeMillis(), ownLock);
}
```

### Acquire

```java
RateLimiter limiter2 = RateLimiter.create(2, 2, TimeUnit.SECONDS);
for (int i = 0; i < 10; i++) {
    stopWatch.start();
    limiter2.acquire();
    stopWatch.stop();
    log.info("WarmingUp-TryAcquire, cost time:{}", stopWatch.getLastTaskTimeMillis());
}
```

## 源码解析

### 1.

> [限流原理解读之guava中的RateLimiter](https://juejin.im/post/5bb48d7b5188255c865e31bc#heading-14)
> [使用Guava RateLimiter限流以及源码解析](https://segmentfault.com/a/1190000016182737)
> [使用Guava RateLimiter限流以及源码解析](https://www.jianshu.com/p/5d4fe4b2a726)
