## Cache
* 使用 CacheLoader 加载缓存
* 使用 Callable 加载缓存
* 使用 CacheLoader 加载缓存，如果没有就从 Callable 获取默认值
* 使用 maximumSize() 限制缓存的大小
* 使用 Weigher 自定义权重函数来限制缓存大小
* 使用 expireAfterAccess() 按缓存闲置时间来驱逐缓存
* 使用 expireAfterWrite() 按缓存生存时间来驱逐缓存
* 使用 weakKeys() 设置键弱引用，当键被回收驱逐缓存
* 使用 weakValues() 设置值弱引用，当值被回收驱逐缓存
* 使用 softValues() 设置值软引用，当值被回收驱逐缓存
* 利用 Optional 处理空值，避免异常
* 使用 refreshAfterWrite() 自动刷新缓存
* 使用 putAll() 预加载缓存

> “30min内没有被读或写就会被驱逐” 不等于 “30min内会被驱逐”，因为真正的过期/刷新操作是在key被读或写时发生的

## Guava Cache 不足之处
* 是单个应用运行时的缓存，数据没有持久和放置在外部系统
* 单机缓存，受机器内存限制，重启系统会使缓存丢失
* 应用分布式系统会出现缓存数据不一致的情况

## expireAfterAccess\expireAfterWrite\refreshAfterWrite 差别

- expireAfterAccess 是指在创建、替换或最后一次访问指定时间后失效，下次从 load() 同步加载缓存
- expireAfterWrite 是指在创建、替换指定时间后，下次从 load() 同步加载缓存，在加载期间，请求缓存所有的线程都会被阻塞
- refreshAfterWrite 是指在创建、替换指定时间后，下次从 reload() 异步刷新缓存，在加载期间不会阻塞其他请求缓存的线程，而是获取加载之前的值