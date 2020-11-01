# Consul 作为服务发现与配置中心

## 关键特性
- 服务发现
- 健康检查
- KV存储
- 多数据中心支持
- 安全的服务间通信

引入依赖：
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-discovery</artifactId>
</dependency>
```

添加依赖支持consul的健康检查
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

简单配置：
```yaml
spring:
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        prefer-ip-address: true
```