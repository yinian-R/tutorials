
## Spring boot 模板脚手架
便于开发，集成常用技术的 spring boot 开发框架。

## 目录结构
```
springboottemplate
├── config                  Spring Configuration
├── core                    核心类
│   ├── generator           代码生成
│   └── log                 日志事件
├── modules
│   ├── common              公共组件 constants、util ...
│   ├── developer           开发者模块
│   ├── resource            文件模块
│   └── manage              后端管理模块
│       ├── controller      控制层
│       │   └── qo          查询对象
│       ├── excel           easyexcel 导入导出相关类 ***Excel、***ImportListener
│       ├── mapper          持久层 - interface、xml
│       ├── entity          实体对象 - 与数据库表结构一一对应
│       │   └── dto         数据传输
│       └── service         逻辑层
│           └── impl        逻辑实现类
└── Application.java        项目启动类
```
## 依赖
- Spring Boot - Spring Boot 使得创建独立的、生产级的、基于 Spring 的应用程序变得很容易
- Knife4j - knife4j是为Java MVC框架集成Swagger生成Api文档的增强解决方案
- MyBatis-Plus - MyBatis-Plus 只对 MyBatis 增强，不做改变
- Flyway - 数据库的版本控制
- MapStruct - Java Bean 之间转换

## 功能
- [x] Spring Boot 单体应用
- [x] MyBatis Plus 操作数据库
- [x] Knife4j 日志在线文档
- [ ] EasyExcel 导入导出 excel
- [ ] 内存缓存
- [x] Flyway 初始化数据库脚本
- [ ] p6spy 控制数据库日志打印
- [x] MapStruct 作为实体映射工具
- [ ] spring websocket 模板代码
- [ ] 使用 spring flux
- [ ] 使用 spring 一个安全框架

## 代码生成
关于代码生成，使用的是 IDEA 的插件 EasyCode 对代码进行生成