Spring Boot Admin用于管理和监控SpringBoot应用程序

## admin-server

使用 Spring Initializr 只需引入 Spring Boot Admin(admin)

启动类添加`@EnableAdminServer`注解

application.yml代码如下：
```
server:
  port: 8090
spring:
  application:
    name: admin-server
```

## admin-client

使用 Spring Initializr 引入Spring Boot Web 和 Spring Boot Admin(client)

application.yml代码如下：
```
server:
  port: 8080
spring:
  application:
    name: admin-client
  boot:
    admin:
      client:
        url: http://localhost:8090
management:
  endpoints:
    web:
      exposure:
        include: '*'
  endpoint:
    health:
      show-details: always
```

启动两个程序，访问localhost:8090