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
Supplier<T>| / | T | 提供一个数据
Function<T,R>| T | R | 输入T输出R的函数
UnaryOperator<T>| T | T | 一元函数（输入输出类型相同）
BiFunction<T,U,R>| (T,U) | R | 两个输入的函数
BiConsumer<T,U>| (T,U) | / | 两个输入的函数
BinaryOperator<T>| (T,T) | T | 两元函数（输入输出类型相同）

> 使用 Lambda 表达式的时候尽量使用JDK自带的函数接口，这样就不用编写太多代码

**Stream 的创建**

 / | 相关方法
---|---
集合 | Collection.stream/parallelStream
数组 | Arrays.stream
数字Stream | IntStream/LongStream.range/rangeClosed Random.ints/longs/doubles
自己创建 | Stream.generate/iterate

**Stream 中间操作**

 / | 相关方法
---|---
无状态操作 | Collection.stream/parallelStream
 | Arrays.stream
 | IntStream/LongStream.range/rangeClosed Random.ints/longs/doubles
自己创建 | Stream.generate/iterate

**Stream 中间操作**


<html>
<table>
    <tr>
        <th></th>
        <th>相关方法</th>
    </tr>
    <tr>
        <td rowspan="5">无状态操作</td>
        <td>map/mapToXxx</td>
    </tr>
    <tr>
        <td>flatMap/flatMapToXxx</td>
    </tr>
    <tr>
        <td>filter</td>
    </tr>
    <tr>
        <td>peek</td>
    </tr>
    <tr>
        <td>unordered</td>
    </tr>
    <tr>
        <td rowspan="3">有状态操作</td>
        <td>distinct</td>
    </tr>
    <tr>
        <td>sorted</td>
    </tr>
    <tr>
        <td>limit/skip</td>
    </tr>
</table>
</html>


**Stream 终止操作**
<html>
<table>
    <tr>
        <th></th>
        <th>相关方法</th>
    </tr>
    <tr>
        <td rowspan="4">无短路操作</td>
        <td>forEach / forEachOrdered</td>
    </tr>
    <tr>
        <td>collect / toArray</td>
    </tr>
    <tr>
        <td>reduce</td>
    </tr>
    <tr>
        <td>min / max / count</td>
    </tr>
    <tr>
        <td rowspan="3">短路操作</td>
        <td>findFirst / findAny</td>
    </tr>
    <tr>
        <td>allMatch / anyMatch / noneMatch</td>
    </tr>
</table>
</html>