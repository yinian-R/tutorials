# log4j 2
> 一个使用log4j作为日志工具
* Console 输出日志到控制台
* File 输出日志到文件
* 添加依赖disruptor和使用<AsyncRoot>开启异步记录

### 概念
日志级别由高到低：`OFF、FATAL、ERROR、WARN、INFO、DEBUG、TRACE、ALL`。级别越高打的日志越少

没有日志配置文件或者JAVA配置，打印error及更高级别日志到控制台
```
ERROR StatusLogger No Log4j 2 configuration file found. Using default configuration (logging only errors to the console), or user programmatically provided configurations. Set system property 'log4j2.debug' to show Log4j 2 internal initialization logging. See https://logging.apache.org/log4j/2.x/manual/configuration.html for instructions on how to configure Log4j 2
```

## 值得参考

[Log4j – Log4j 2 Layouts](https://logging.apache.org/log4j/2.x/manual/layouts.html) - Pattern Layout 最值得看