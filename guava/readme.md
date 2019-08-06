
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

[使用Guava RateLimiter限流以及源码解析](https://www.jianshu.com/p/5d4fe4b2a726)

[guava cache 简单入门](https://www.jianshu.com/p/5299f5a11bd5)

[深入解密来自未来的缓存-Caffeine](https://juejin.im/post/5b8df63c6fb9a019e04ebaf4)

