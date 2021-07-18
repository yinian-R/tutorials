
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
│       ├── excel           easyexcel 导入导出相关类 ***Excel、***ImportListener
│       ├── mapper          持久层 - interface、xml
│       ├── model           数据模型
│       │   ├── dto         数据传输
│       │   ├── entity      实体对象 - 与数据库表结构一一对应
│       │   └── qo          查询对象
│       └── service         逻辑层
│           └── impl        逻辑实现类
└── Application.java        项目启动类
```
## 依赖
- Spring Boot - Spring Boot 使得创建独立的、生产级的、基于 Spring 的应用程序变得很容易
- Knife4j - knife4j是为Java MVC框架集成Swagger生成Api文档的增强解决方案
- MyBatis-Plus - MyBatis-Plus 只对 MyBatis 增强，不做改变
- Flyway - 数据库的版本控制

## 功能
- [x] Spring Boot 单体应用
- [x] MyBatis Plus 操作数据库
- [x] Knife4j 日志在线文档
- [ ] 基于 Knife4j 操作日志落地数据库
- [ ] EasyExcel 导入导出 excel
- [ ] 内存缓存
- [ ] Flyway 初始化数据库脚本
- [ ] p6spy 控制数据库日志打印
