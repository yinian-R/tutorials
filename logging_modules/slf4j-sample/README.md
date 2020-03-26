# slf4j
> 一个使用slf4j作为日志工具

## 单独使用Slf4j，引入依赖
```
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.2</version>
</dependency>
```

> slf4j-simple 具有依赖 slf4j-api

## slf4j如何识别其他的日志jar
LoggerFactory#findPossibleStaticLoggerBinderPathSet()加载类org.slf4j.impl.StaticLoggerBinder。
而slf4j-simple和logback-classic依赖中有这个类，所以可以使用slf4j作为外观