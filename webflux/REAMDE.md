1 添加依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
</dependency>
```

2 @EnableReactiveMongoRepositories

3  数据库配置

```
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/webflux
```

4 定义对象
```
@Data
@Document(collection = "user")
```

5 仓库

接口继承ReactiveMongoRepository
```
public interface UserRepository extends ReactiveMongoRepository<User, String> {
```


# 参数校验
第一种使用 hibernate validate，第二种使用自定义的异常和检验工具类