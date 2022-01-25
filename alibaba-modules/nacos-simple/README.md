# NACOS
一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。

## Java 引入 Nacos 客户端依赖
```xml
<dependency>
    <groupId>com.alibaba.nacos</groupId>
    <artifactId>nacos-client</artifactId>
    <version>2.0.3</version>
</dependency>
```
## 新建命名空间
访问 http://127.0.0.1:8848 -> 命名空间，新建命名空间

## 微服务获取配置引入依赖
maven pom添加：
```xml
    <properties>
        <spring-cloud-alibaba.version>2.2.2.RELEASE</spring-cloud-alibaba.version>
        <spring-cloud.version>2021.0.0</spring-cloud.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
boostrap.yml 添加：
```
spring:
  application:
    name: nacos-simple
  cloud:
    nacos:
      config:
        # 是否获取 nacos 配置
        enabled: true
        # nacos server
        server-addr: 127.0.0.1:8848
        # 命名空间ID，不同环境的分离配置。默认 public
        namespace: a85a37ef-5bec-478c-a60f-0b11f10b3da4
        # 配置内容的元数据
        group: TEST_GROUP
        # 配置内容的文件扩展名，启动后读取 dataId = {spring.application.name}.{file-extension} 例如：nacos-simple.yml
        file-extension: yml
        # 刷新主配置开关，默认 true。主配置： {spring.application.name}.{file-extension}
        refresh-enabled: true
        # 一组共享配置
        # 优先级：shared-configs < extension-configs < {spring.application.name}.{file-extension}，配置列表中在下面的配置优先级高
        # 后加载的优先级高，因此 shared-configs 最先加载，{spring.application.name}.{file-extension} 最后加载
        shared-configs:
          - dataId: common.yml
        # 一组扩展配置
        extension-configs:
          # 使用默认组，即：DEFAULT_GROUP
          - dataId: spring-redis.yml
          - dataId: spring-mq.yml
            # 使用自定义组
            group: TEST_GROUP
            # 支持动态更新，默认 false
            refresh: true
```


## 参考
[NACOS 官网](https://nacos.io/zh-cn/)

[Spring Cloud Alibaba Nacos 详解（上）](https://developer.aliyun.com/learning/course/724?spm=a2c6h.14164896.0.0.61fe435bMAan0l)

[Spring Cloud Alibaba Nacos 详解（下）](https://developer.aliyun.com/learning/course/725?spm=a2c6h.14164896.0.0.61fe435bMAan0l)
