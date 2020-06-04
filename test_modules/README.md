## 初始化项目可引入junit聚合模块
```
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.5.2</version>
    <scope>test</scope>
</dependency>
```
重要的是要注意，此版本需要Java 8才能运行。

> junit-jupiter-engine：聚合所有JUnit Jupiter模块