# Jackson 简述

- writeValue() 将Java对象输出JSON 
- writeValueAsString() 将Java对象输出JSON字符串
- readTree() 将JSON字符串解析为JsonNode对象
- configure() 扩展 ObjectMapper
- TypeReference 

**Jackson库的最大优势之一是高度可定制的序列化和反序列化过程**

主要方法：
- 使用 setDateFormat 设置日期格式
- 使用 @JsonFormat 设置日期格式
- 使用 @JsonSerialize 指定使用的自定义序列化程序
- 使用 @JsonDeserialize 指定使用自定义反序列化器
- registerModule(Module module) 注册全局的自定义序列化器、反序列化器
- 引入依赖 *jackson-datatype-jsr310* 支持 JAVA 8 日期
- 序列化时，使用 @JsonAnyGetter 把 Map 中的所有键值作为标准的普通属性获取
- 使用 @JsonAnySetter 使我们可以灵活地使用 Map 作为标准属性。
- 序列化时，使用 @JsonGetter 自定义序列化的 key
- @JsonSetter 是一种替代 @JsonProperty - 将标记的方法作为setter方法
- @JsonProperty注释，以表明在JSON属性名
- 使用 @JsonPropertyOrder 指定序列化属性的排序
- 使用 @JsonPropertyOrder（alphabetic = true） 指定序列化时，按字母顺序对属性进行排序
- 序列化时，使用 @JsonRawValue 指定属性作为 json 字符串的值
- 使用 @JsonValue 指定用于序列化整个实例的方法
- 使用 @JsonRootName 指定此潜在包装实体的名称
- 使用 @JsonCreator 来调整反序列化中使用的构造函数/工厂
- 使用 @JsonAlias 定义反序列化时属性一个或多个替代名称
- @JsonIgnoreProperties 是一个类级别的注释，用于标记 Jackson 将忽略的一个属性或属性列表
- @JsonIgnore 注释用来在字段级别标记被忽略的属性
- @JsonIgnoreType 将带注释的类型的所有属性标记为忽略
- 使用 @JsonInclude 排除具有null/空/默认值等属性
- @JsonAutoDetect 可以覆盖默认语义，即哪些属性可见，哪些属性不可见
- Jackson多态类型处理注释：@JsonTypeInfo – 指示要在序列化中包含哪些类型信息的详细信息。@JsonSubTypes – 指示带注释类型的子类型。@JsonTypeName – 定义用于注释类的逻辑类型名称
- @JsonUnwrapped定义在序列化/反序列化时应该解包/展平的值
- @JsonView 将在其中包含属性以进行序列化/反序列化的视图
- @JsonManagedReference 和 @JsonBackReference 注释可以处理父/子关系并在循环中工作
- @JsonIdentityInfo 指示在对值进行序列化/反序列化时应使用对象标识
- @JsonFilter注释指定一个过滤器序列化过程中使用
- @JacksonAnnotationsInside 注释自定义 Jackson 注释
- 使用 Jackson MixIn 语法，在序列化时忽略 addMixIn 添加的类型
- 禁用所有 Jackson 注解

> PS：引入依赖 *jackson-datatype-jsr310*，使用 @JsonDeserialize、@JsonSerialize、@JsonFormat 定制Java 8 日期格式

> 忽略属性使用 *@JsonIgnoreProperties*、*@JsonIgnore*、*@JsonIgnoreType*、*@JsonFilter*

**支持 JAVA 8 日期**
```
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
    <version>2.11.1</version>
</dependency>
```

**通过注解 @JsonFormat 设置日期格式原理**

子类*LocalDateDeserializer* 实现基类 *JSR310DateTimeDeserializerBase* 的 *withDateFormat(DateTimeFormatter dtf)* 方法设置 *deserialize* 方法使用的 *DateTimeFormatter* 实例

*JSR310DateTimeDeserializerBase* 的 *createContextual* 方法获取注解的日期格式并调用 *withDateFormat* 设置 *DateTimeFormatter*

**如何使用Jackson 2.x将Java对象序列化为XML数据并将其反序列化回POJO**

```
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.11.1</version>
</dependency>
```

**支持 Java 8 Optional 支持**
```
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jdk8</artifactId>
    <version>2.11.1</version>
</dependency>
```

## 参考

https://www.baeldung.com/jackson-object-mapper-tutorial

https://www.baeldung.com/jackson-serialize-dates

https://www.baeldung.com/jackson
