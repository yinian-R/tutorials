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
