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
│     └── ServerInfo        请求服务器信息
│  └── interfaces
│     ├── ProxyCreator      创建代理类接口
│     └── RestHandler       rest 请求调用处理类接口
│  └── proxys
│     └── JDKProxyCreator   ProxyCreator 创建代理类接口实现
│  └── resthandlers
│     └── WebClientRestHandler  RestHandler 请求调用处理类接口实现
├── webfluxclient
│  └── IUserApi     测试获取用户信息接口（定义请求链接、参数、返回值）
```