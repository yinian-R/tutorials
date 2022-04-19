# tutorials
记录值得学习和反思的基础知识，归类为深度入门教程

**深度**且**全面**入门教程
- 在学会 HttpClient4 发送 GET/POST 等请求，还告诉你还有超时参数设置、SSL使用、短链接处理、多线程、连接管理等功能
- 在学会使用 Kafka 消息发送和消费的同时，还告诉你还有幂等生产者、事务生产者、无消息丢失生产者配置、事务消费者、多线程消费者、认证机制等等功能

## Java 基础
- core-java - Java 基础类使用技巧相关
- core-java-collections - 集合使用技巧相关
- core-java-concurrent - 多线程使用技巧相关
- core-java-date - Java 日期使用技巧相关
- core-java-enum - Java enum 使用技巧相关
- core-java-lamdba - lamdba 使用技巧相关
- core-java-stream - stream 使用技巧相关
- core-java-optional - optional 使用技巧相关
- idea - Java 基础碎片
- 合并以上项目（需进行...）

### IO
- IO 入门 - [💡 io-sample](/io-sample) 

## Spring Boot 专栏
- 自定义 Spring Boot Starter 入门 - [💡 spring-boot-custom-starter](/spring-modules/spring-boot-custom-starter) 
- Spring Boot + Vue 单体项目极简示例 - [💡 spring-boot-book-sample](/spring-modules/spring-boot-book-sample)
- Spring Boot 支持 HTTP2 入门 - [💡 spring-boot-http2-sample](/spring-modules/spring-boot-http2-sample)

### 
- Spring Boot webflux 流式服务 - [💡 webflux](/spring-modules/webflux)
- Spring Boot webflux 流式服务 - [💡 webflux2](/spring-modules/webflux2)
- Spring Boot webflux 流式服务 WebClient 封装 - [💡 webflux-client](/spring-modules/webflux-client)

### 监控管理
- Spring Boot Admin 管理和监控 SpringBoot 应用程序 - [💡 spring-boot-admin-samples](/spring-modules/spring-boot-admin-samples)

## Spring Cloud 专栏
**注册中心**
- Spring Cloud Alibaba 注册中心 Nacos 入门 - [💡 spring-cloud-eureka-server](/spring-clould-modules/discovery/spring-cloud-discovery-nacos-sample)
- Spring Cloud Netflix 注册中心 Eureka 入门 - [💡 spring-cloud-eureka-server](/spring-clould-modules/spring-cloud-sample/spring-cloud-eureka-server)
- Spring Cloud 注册中心 Consul 入门 - [💡 spring-cloud-eureka-server](/spring-clould-modules/discovery/spring-cloud-discovery-consul-sample)

**服务调用**
- Spring Cloud Netflix 负载均衡 Ribbon 入门 - [💡 spring-cloud-consumer-rest-ribbon](/spring-clould-modules/spring-cloud-sample/spring-cloud-consumer-rest-ribbon)
- Spring Cloud 声明式调用 Feign 入门 - [💡 spring-cloud-consumer-feign](/spring-clould-modules/spring-cloud-sample/spring-cloud-consumer-feign)

**服务容错**
- Spring Cloud Alibaba 服务容错 Sentinel 入门 
- Spring Cloud Netflix 服务容错 Hystrix 入门 

**路由网关**
- Spring Cloud 服务网关 Spring Cloud Gateway 入门
- Spring Cloud Netflix 路由网关 Zuul 入门 - [💡 spring-cloud-zuul](/spring-clould-modules/spring-cloud-sample/spring-cloud-zuul)

**配置中心**
- Spring Cloud Alibaba 配置中心 Nacos 入门
- Spring Cloud 配置中心 Spring Cloud Config 入门 - [💡 spring-cloud-config-server](/spring-clould-modules/spring-cloud-sample/spring-cloud-config-server)

**消息队列**
++Spring Cloud Stream++

- Spring Cloud Alibaba 消息队列 RocketMQ 入门
- Spring Cloud 消息队列 RabbitMQ 入门
- Spring Cloud 消息队列 Kafka 入门
- Spring Cloud 消息队列 ActiveMQ 入门

++Spring Cloud Bus++

- Spring Cloud Alibaba 事件总线 Bus RocketMQ 入门
- Spring Cloud 事件总线 Bus RabbitMQ 入门
- Spring Cloud 事件总线 Bus Kafka 入门

**分布式事务**
- Spring Cloud Alibaba 分布式事务 Seata 入门

**链路追踪**
- Spring Cloud 链路追踪 SkyWalking 入门
- Spring Cloud 链路追踪 Spring Cloud Sleuth

**监控管理**
- Spring Boot Admin 管理和监控 SpringBoot 应用程序 - [💡 spring-boot-admin-samples](/spring-modules/spring-boot-admin-samples)


## 配置中心
- 配置中心 zookeeper 入门 - [💡 apache-zookeeper](/apache_modules/apache-zookeeper) 

## 数据访问
- Spring Boot中使用多个数据源 - [💡 spring-boot-multiple-db](/persistence-modules/spring-boot-multiple-db)

**关系数据库**
- 通过SpringJDBC访问数据库 - [💡 spring-boot-simple-jdbc](/persistence-modules/spring-boot-simple-jdbc)
- Spring Boot Data JPA 访问数据库 - [💡 spring-data-jpa](/persistence-modules/spring-data-jpa)


**非关系数据库**
- Elasticsearch 入门 - [💡 elasticsearch-sample](/elasticsearch-sample) 
- Spring Data Elasticsearch 访问 Elasticsearch - [💡 spring-data-elasticsearch](/persistence-modules/spring-data-elasticsearch)
- Spring Data MongoDB 访问 MongoDB - [💡 spring-data-mongodb](/persistence-modules/spring-data-mongodb)
- Spring Data Redis 访问 Redis - [💡 spring-data-redis](/persistence-modules/spring-data-redis)
- Spring Data Redis + Cache 使用示例 - [💡 spring-web-redis](/persistence-modules/spring-web-redis)

## 远程调用
**HTTP**
- HttpClient4 入门 - [💡 http-client-4-sample](/http-client-4-sample) 

## 消息队列
**kafka**
- 消息队列 kafka 入门 - [💡 apache-kafka-sample](/message-queue-modules/apache-kafka-sample) 
- Spring Boot 消息队列 Kafka 入门 - [💡 spring-kafka-sample](/message-queue-modules/spring-kafka-sample)

**RabbitMQ**
- 消息队列 RabbitMQ 极简入门 - [💡 rabbitmq-sample](/message-queue-modules/rabbitmq-sample)
- Spring Boot 消息队列 RabbitMQ 入门 - [💡 spring-boot-rabbitmq-sample](/message-queue-modules/spring-boot-rabbitmq-sample)

## 设计
- Java 设计模式 26 种入门 - [💡 design-pattern](/design-pattern) 

## 日志打印
- SLF4J 入门 - [💡 slf4j-sample](/logging_modules/slf4j-sample) 
- logback 入门 - [💡 logback-sample](/logging_modules/logback-sample) 
- Log4j2 入门 - [💡 log4j2-sample](/logging_modules/log4j2-sample) 
- Log4j2 编程式配置入门 - [💡 log4j2-programmatic-sample](/logging_modules/log4j2-programmatic-sample) 

## 高级进阶
- 手写持久化框架 Mybatis - [💡 custom-mybatis](/advanced-learning/custom-mybatis) 

## 其它
- Java 常用类库 Guava - [💡 guava](/guava) 
- Jackson 高性能 JSON 处理类库 - [💡 jackson-sample](/jackson-sample) 
- Linux 常用脚本集 - [💡 common-shell-scripts](/common-shell-scripts) 
- Flume 集成 RabbitMQ - [💡 rabbitmq-flume-plugin](/message-queue-modules/rabbitmq-flume-plugin)
- MySQL 笔记 - [💡 mysql-advanced](/mysql-advanced)
- Spring Boot + Netty 构建 socket io 极简入门 - [💡 netty-socket-io-demo](/netty-socket-io-demo)
- 使用 protobuf 序列化和反序列化数据 - [💡 protobuf](/protobuf)
- REST 语言后端实现 - [💡 spring-rest-query-language](/spring-modules/spring-rest-query-language)
- Junit5 极简入门 - [💡 junit-5](/test_modules/junit-5)
