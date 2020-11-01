开发最需要关注的是输入输出

设计最需要关注的是解耦

# webflux-client

使用动态代理，对 WebClient 请求进行封装
- 使用接口的方式发送 http 请求
- 请求链接从接口类注解和方法注解获取

### 目录结构
```
├── core
│  └── beans
│     ├── MethodInfo        请求接口方法调用信息类
│     └── ServerInfo        请求服务器信息类
│  └── interfaces
│     ├── ProxyCreator      创建代理类接口
│     └── RestHandler       请求接口调用处理类接口
│  └── proxys
│     └── JDKProxyCreator   ProxyCreator 创建代理类接口实现（提取服务器信息，提取方法定义和调用的相关信息，创建接口的代理类）
│  └── resthandlers
│     └── WebClientRestHandler  RestHandler 请求接口调用处理类接口实现
├── webfluxclient
│  └── IUserApi     用户信息请求接口（定义用户信息请求链接、参数、返回值）
```

### 关键代码
主要使用 Java 动态代理技术实现关键代码
```
Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{type}, (proxy, method, args) -> {
    // 根据方法和参数提取调用的信息
    MethodInfo methodInfo = extractMethodInfo(method, args);
    // 调用 rest
    return handler.invokeRest(methodInfo);
});
```

### 下一步优化思路
目前注入是单个注入，如何做到扫描，批量注入？(可参考 mybatis mapper 注入)，目前代码如下：
`FactoryBean<IUserApi> userApiFactoryBean(ProxyCreator proxyCreator)`