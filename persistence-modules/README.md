# modules
- spring-boot-multiple-db：Spring Boot多数据源
- spring-boot-simple-jdbc：通过SpringJDBC访问数据库

## 连接池
HikariCp是一个高性能的JDBC连接池。Spring Boot 2.* 默认使用 `HikariCP` 作为连接池。
alibaba druid 为监控而生的数据库连接池。

## Spring Data JPA 的 Repository 是怎么从接口变成 Bean
最主要的是**JpaRepositoriesRegistrar**，它在里面激活了`@EnableJpaRepositories`，并且返回了`JpaRepositoryConfigExtension`。其父类**RepositoryBeanDefinitionRegistrarSupport**方法`registerBeanDefinitions`注册`Repository Bean`（类型JpaRepositoryFactoryBean）

`JpaRepositoryConfigExtension`的父类**RepositoryConfigurationExtensionSupport**方法`getRepositoryConfigurations`取得Repository配置

**JpaRepositoryFactory**方法`getTargetRepository`创建了Repository