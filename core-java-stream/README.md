**什么是Stream？**

Stream不是一个集合，不会存放数据，它关注的是怎么高效处理数据，它其实是把数据在一个流水线里面处理

**说一下流操作**

创建流，中间操作，终止操作

**Stream 流编程**

 ` | 相关方法
---|---
集合 | Collection.stream/parallelStream
数组 | Arrays.stream
数字Stream | IntStream/LongStream.range/rangeClosed Random.ints/longs/doubles
自己创建 | Stream.generate/iterate


**Stream 中间操作**

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
