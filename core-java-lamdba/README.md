lambda 的接口必须只有一个需要实现的方法

@FunctionalInterface 主要是编译期间的一个校验，即便不加 lambda 也会生效，但是加了之后会告诉开发者有且仅有一个方法需要实现。

**使用函数接口的好处？**

一，可以不用定义那么多接口。
二，函数接口支持链式调用。
可以定义更好的接口，可以更好的调用

**JDK 提供的 FunctionalInterface:**

接口 | 输入参数 | 返回类型 | 说明
---|---|---|---
Predicate<T>| T | boolean | 断言
Consumer<T>| T | / | 消费一个数据
Function<T,R>| T | R | 输入T输出R的函数
Supplier<T>| / | T | 提供一个数据
UnaryOperator<T>| T | T | 一元函数（输入输出类型相同）
Bifunction<T,U,R>| (T,U) | R | 两个输入的函数
BinaryOperator<T>| (T,T) | T | 两元函数（输入输出类型相同）