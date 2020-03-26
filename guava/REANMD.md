## Cache
* 使用CacheLoader加载缓存
* 使用Callable加载缓存
* 使用CacheLoader加载缓存，如果没有就从Callable获取默认值
* 使用maximumSize()限制缓存的大小
* 使用Weigher自定义权重函数来限制缓存大小
* 使用expireAfterAccess()按缓存闲置时间来驱逐缓存
* 使用expireAfterWrite()按缓存生存时间来驱逐缓存
* 使用weakKeys()设置键弱引用，当键被回收驱逐缓存
* 使用weakValues()设置值弱引用，当值被回收驱逐缓存
* 使用softValues()设置值软引用，当值被回收驱逐缓存
* 利用Optional处理空值，避免异常
* 使用refreshAfterWrite()自动刷新缓存
* 使用putAll()预加载缓存

> “30min内没有被读或写就会被驱逐” 不等于 “30min内会被驱逐”，因为真正的过期/刷新操作是在key被读或写时发生的

## Guava Cache 不足之处
* 是单个应用运行时的缓存，数据没有持久和放置在外部系统
* 单机缓存，受机器内存限制，重启系统会使缓存丢失
* 应用分布式系统会出现缓存数据不一致的情况