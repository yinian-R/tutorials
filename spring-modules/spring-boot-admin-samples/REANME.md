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

> 启动两个程序，访问localhost:8090

## 添加 Security

### Admin Server

添加依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

application.yml 添加配置

```
spring:
  security:
    user:
      name: admin
      password: admin-password
```

设置配置类
```
@EnableAdminServer
@SpringBootApplication
public class SpringBootAdminServerApplication extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private AdminServerProperties adminServerProperties;
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminServerApplication.class, args);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String adminContextPath = adminServerProperties.getContextPath();
    
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");
    
        http.authorizeRequests()
                .antMatchers(adminContextPath + "/assets/**").permitAll()
                .antMatchers(adminContextPath + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
                .logout().logoutUrl(adminContextPath + "/logout").and()
                .httpBasic().and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers(
                        adminContextPath + "/instances",
                        adminContextPath + "/actuator/**"
                );
    }
}
```

### Admin Client

添加依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

application.yml 添加配置
```
spring:
  boot:
    admin:
      client:
        ...
        username: admin
        password: admin-password
        instance:
          metadata:
            user:
              name: ${spring.security.user.name}
              password: ${spring.security.user.password}
  security:
    user:
      name: client
      password: client-password
```


> 重启两个项目，可有通过账号 admin、admin-password 登录