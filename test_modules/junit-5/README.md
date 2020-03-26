设置  JUnit 5.x.0非常简单，我们需要在pom.xml中添加以下依赖项：
```
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.5.2</version>
    <scope>test</scope>
</dependency>
```
重要的是要注意，此版本需要Java 8才能运行。