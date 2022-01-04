
### 目录结构
```
├── config
│   ├── BoundSql.java           解析后SQL和参数名称
│   ├── XMLConfigBuilder.java   解析配置文件
│   └── XMLMapperBuilder.java   解析 Mapper.xml
├── io
│   └── Resources.java          资源工具类
├── pojo
│   ├── Configuration.java      配置对象（数据源、映射语句）
│   └── MappedStatement.java    映射语句对象
├── sqlSession
│   ├── DefaultSqlSession.java          SqlSession接口实现（持有配置对象、获取 MappedStatement 对象、调用SQL执行方法）
│   ├── DefaultSqlSessionFactory.java   SqlSessionFactory接口实现（生成 SqlSession 对象）
│   ├── Executor.java                   访问数据库执行类
│   ├── SimpleExecutor.java             Executor接口实现（1.获取连接、2.获取转换 SQL、3.获取预处理对象、4.设置参数、5.执行 SQL、6.封装返回结果集）
│   └── SqlSession.java                 SQL 会话接口
│   ├── SqlSessionFactory.java          SQL 会话工厂接口
│   ├── SqlSessionFactoryBuilder.java   SQL 会话工厂建造类
└── utils
    ├── GenericTokenParser.java         解析 SQL 文件
    ├── ParameterMapping.java           参数映射对象
    ├── ParameterMappingTokenHandler.java   TokenHandler接口实现（参数映射处理类）
    └── TokenHandler.java               参数映射处理接口
```

### 进阶点
GenericTokenParser 可以总结思路