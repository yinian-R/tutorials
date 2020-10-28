# Http Client 4
## 引入依赖
```xml
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.9</version>
</dependency>
```

## 使用流式接口
引入依赖 `fluent-hc`
```xml
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>fluent-hc</artifactId>
    <version>4.5.9</version>
</dependency>
```

## 时间
- Connection Timeout : 建立与远程主机连接的时间
- Socket Timeout : 等待数据的时间，两个数据包之间不活动的最长时间
- Connection Manager Timeout : 等待连接管理器/池连接的时间（默认-1，无限等待，使用默认即可）
- Keep-Alive ：一个http产生的tcp连接在传送完最后一个响应后，还需要持有keepalive_timeout秒后，才开始关闭这个连接

## 常用命令

使用linux命令：`netstat -n | awk '/^tcp/ {++S[$NF]} END {for(a in S) print a, S[a]}'`可以清楚的看到tcp各个状态下的连接数。
主要查看 CLOSE_WAIT 是否大得惊人

> PS: RequestConfig 类中有许多配置，我们可以在不同场景中设置不同的配置对请求进行优化

- evictExpiredConnections - 驱逐过期连接
- evictIdleConnections - 驱逐空闲连接
- setKeepAliveStrategy - 连接复用保持策略