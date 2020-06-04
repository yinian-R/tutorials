# logback
Logback是Log4j的改进版，也引入了Log4j 2

将依赖引入pom文件：
```
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.1.7</version>
</dependency>
```
此依赖可传递依赖logback-core和slf4j-api

## 基本示例配置
创建一个ogback.xm放在类路径
```xml
<configuration>
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
  </appender>
 
<!--根标签。此标记将根记录器设置为DEBUG模式，并将其输出与名为STDOUT的Appender关联-->
  <root level="debug">
    <appender-ref ref="STDOUT" />
  </root>
</configuration>
```
> 如果找不到配置文件，则会创建  ConsoleAppender 并将其与根记录器关联。

以下是Logback尝试查找配置数据的方式
- 按此顺序在类路径中搜索名为`logback-test.xml`, `logback.groovy`,`logback.xml`的文件
- 如果该库找不到这些文件，它将尝试使用Java的`ServiceLoader`来定位`com.qos.logback.classic.spi.Configurator`的实现者。
- 自行配置以直接将输出记录到控制台
## 设置日志级别
出现设置重复的会以路径更准确的为准，以下com.wymm.logback.level 和 com.wymm.logback.level*的日志级别都是ERROR
```
<logger name="com.wymm.logback" level="TRACE"/>
<logger name="com.wymm.logback.level" level="ERROR"/>
```
## 替换变量
Logback配置文件支持变量。我们在配置脚本内部或外部定义变量。可以在配置脚本中的任何位置代替值来指定变量
```xml
<configuration>
    <property name="LOG_PATH" value="./logs"/>
    <property name="DEFAULT_PATTERN" value="%d{yyyy-MM-dd HH:mm:ss} %-5p %m%n"/>

    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>${LOG_DIR}/tests.log</file>
        <append>true</append>
        <encoder>
            <pattern>${DEFAULT_PATTERN}</pattern>
        </encoder>
    </appender>
</configuration>
```
使用${propertyName}来替换变量

## Appender简述
Logger 把 LoggingEvents 传递给 Appender，Appender 完成实际的工作


additivity设置false，不会关联根记录器的Appender。
com.wymm.logback.level 只有fout生效，stdout不会生效

```xml
<configuration>
    <logger name="com.wymm.logback.level" level="ERROR" additivity="false">
        <appender-ref ref="fout"/>
    </logger>
    <root level="INFO">
        <appender-ref ref="stdout"/>
    </root>
</configuration>
```