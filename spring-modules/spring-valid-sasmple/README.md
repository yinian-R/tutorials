## 常用方法

validatedValue - 获取入参值
value - 获取限制值；{value}内的值同注解属性名，如{min}
```
@Min(value = 0L, message = "${validatedValue}，年龄最小为{value}")
private Integer age;
    
@Length(min = 10,max = 100, message = "长度应在{min}~{max}之间")
private String bookTypeText;
```


## 参考
消息表达式使用插值：https://www.baeldung.com/spring-validation-message-interpolation