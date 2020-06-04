# log4j 2
> 一个使用`log4j`作为日志工具

引入依赖，包含`log4j-api、log4j-core、slf4j-api`
```
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>2.11.1</version>
</dependency>
```

* `Console` 输出日志到控制台
* `File` 输出日志到文件
* 添加依赖`disruptor`和使用`<AsyncRoot>`开启异步记录
* 引入依赖`log4j-slf4j-impl`使用`Slf4j`作为外观
* 自定义Appender
* 使用lambda表达式进行懒记录

### 基础
日志级别由高到低：`OFF、FATAL、ERROR、WARN、INFO、DEBUG、TRACE、ALL`。级别越高打的日志越少

没有日志配置文件或者JAVA配置，打印error及更高级别日志到控制台
```
ERROR StatusLogger No Log4j 2 configuration file found. Using default configuration (logging only errors to the console), or user programmatically provided configurations. Set system property 'log4j2.debug' to show Log4j 2 internal initialization logging. See https://logging.apache.org/log4j/2.x/manual/configuration.html for instructions on how to configure Log4j 2
```

## 值得参考

[Log4j – Log4j 2 Layouts](https://logging.apache.org/log4j/2.x/manual/layouts.html) - Pattern Layout 最值得看