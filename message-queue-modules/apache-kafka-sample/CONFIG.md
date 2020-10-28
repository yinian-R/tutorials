### 最最最重要的集群参数配置

#### 配置存储信息
**log.dirs**：这是非常重要的参数，指定了Broker需要使用的若干个文件目录路径。

- 提升读写性能，比起单块磁盘，多块物理磁盘同时读写数据具有更高的吞吐量
- 能实现故障转移，即Failover。这是Kafka 1.1版本新引入的强大功能。要知道在以前，只要Kafka Broker使用的任何一块磁盘挂掉了，整个Broker进程都会关闭。
但是自1.1开始，这种情况被修正了，坏掉的磁盘上的数据会自动地转移到其他正常的磁盘上，而且Broker还能正常工作。


#### zookeeper 相关
**zookeeper.connect**。这是一个CSV格式的参数，比如我可以指定它的值为zk1:2181,zk2:2181,zk3:2181。2181是ZooKeeper的默认端口。

如果你有两套Kafka集群，假设分别叫它们kafka1和kafka2，那么两套集群的zookeeper.connect参数可以这样指定：zk1:2181,zk2:2181,zk3:2181/kafka1和zk1:2181,zk2:2181,zk3:2181/kafka2。
切记chroot只需要写一次，而且是加到最后的。


#### Broker 连接相关

**listeners**：学名叫监听器，其实就是告诉外部连接者要通过什么协议访问指定主机名和端口开放的
Kafka服务。

**advertised.listeners**：和listeners相比多了个advertised。Advertised的含义表示宣称的、公布
的，就是说这组监听器是Broker用于对外发布的。

我们具体说说监听器的概念，从构成上来说，它是若干个逗号分隔的三元组，每个三元组的格式为<协议名
称，主机名，端⼝号>。这里的协议名称可能是标准的名字，比如PLAINTEXT表示明文传输、SSL表示使用
SSL或TLS加密传输等；比如SSL://localhost:9092。

> 经常有人会问主机名这个设置中我到底使用IP地址还是主机名。这里我给出统一的建议：最好全部使用主机名，即Broker端和Client端应用配置中全部填写主机名。
Broker源代码中也使用的是主机名，如果你在某些地方使用了IP地址进行连接，可能会发生无法连接的问题。

#### Topic 管理相关
**auto.create.topics.enable**：是否允许自动创建Topic。推荐false

**unclean.leader.election.enable**：是否允许Unclean Leader选举。推荐false

**auto.leader.rebalance.enable**：是否允许定期进行Leader选举。推荐false

#### 数据留存方面

**log.retention.{hour|minutes|ms}**：这是个“三兄弟”，都是控制一条消息数据被保存多长时间。从优先级上来说ms设置最高、minutes次之、hour最低

**log.retention.bytes**：这是指定Broker为消息保存的总磁盘容量大小

其次是这个log.retention.bytes。这个值默认是-1，表明你想在这台Broker上保存多少数据都可以，至少在容量方面Broker绝对为你开绿灯，
不会做任何阻拦。这个参数真正发挥作用的场景其实是在云上构建多租户的Kafka集群：设想你要做一个云上的Kafka服务，每个租户只能使用100GB的磁盘空间，
为了避免有个“恶意”租户使用过多的磁盘空间，设置这个参数就显得至关重要了。

**message.max.bytes**：控制Broker能够接收的最大消息大小

最后说说message.max.bytes。实际上今天我和你说的重要参数都是指那些不能使用默认值的参数，这个参数也是一样，默认的1000012太少了，还不到1KB。
实际场景中突破1MB的消息都是屡见不鲜的，因此在线上环境中设置一个比较大的值还是比较保险的做法。毕竟它只是一个标尺而已，仅仅衡量Broker能够处
理的最大消息大小，即使设置大一点也不会耗费什么磁盘空间的。


#### Topic级别参数

**retention.ms**：规定了该Topic消息被保存的时长。默认是7天，即该Topic只保存最近7天的消息。一旦设置了这个值，它会覆盖掉Broker端的全局参数值。

**retention.bytes**：规定了要为该Topic预留多大的磁盘空间。和全局参数作用相似，这个值通常在多租户的Kafka集群中会有用武之地。
当前默认值是-1，表示可以无限使用磁盘空间。

**message.max.bytes**：控制Topic能够接收的最大消息大小

如果在全局层面上，我们不好给出一个合适的最大消息值，那么不同业务部门能够自行设定这个Topic级别参数就显得非常必要了。在实际场景中，这种用法也确实是非常常见的。


Topic级别参数的设置就是这种情况，我们有两种方式可以设置：
- 创建Topic时进行设置
- 修改Topic时设置

#### JVM参数

Java 8+

**KAFKA_HEAP_OPTS**：指定堆大小。

**KAFKA_JVM_PERFORMANCE_OPTS**：指定GC参数。

启动Kafka Broker之前，先设置上这两个环境变量:
```shell script
export KAFKA_HEAP_OPTS=--Xms6g --Xmx6g
export KAFKA_JVM_PERFORMANCE_OPTS= -server -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:InitiatingHeapOccupancyPercent=35 -XX:+ExplicitGCInvokesConcurrent -Djava.awt.headless=true
bin/kafka-server-start.sh config/server.properties
```

GC参数 | 含义
---|---
-XX:+UseG1GC | 使用G1垃圾回收器
-XX:MaxGCPauseMillis=<N> | 最大暂停时间目标（两个GC之间的间隔[毫秒]），这是一个软性指标(soft goal), JVM 会尽量去达成这个目标
-XX:InitiatingHeapOccupancyPercent=<N> | 启动并发GC周期时的堆内存占用百分比. G1之类的垃圾收集器用它来触发并发GC周期,基于整个堆的使用率,而不只是某一代内存的使用比. 值为 0 则表示"一直执行GC循环". 默认值为 45.
-XX:+ExplicitGCInvokesConcurrent | 显式 GC 会并发执行。System.gc() 触发 full gc，会 Stop Work，使用了这个参数则能并发执行

java.awt.headless: 激活headless模式

> Headless模式是系统的一种配置模式。在系统可能缺少显示设备、键盘或鼠标这些外设的情况下可以使用该模式

> G1 是 JDK9 中默认的，JDK8 需显式指定

#### 操作系统参数

**ulimit -n <N>**: 指定同一时间最多可开启的文件数。
任何一个 Java 项目最好都调整下这个值。实际上，文件描述符系统资源并不像我们想象的那样昂贵，你不用太担心调大此值会有什么不利的影响。通常情况下将它设置成一个超大的值是合理的做法，比如ulimit -n 1000000

#### Product 无消息丢失配置

**acks = all**:acks 是 Producer 的一个参数，代表了你对“已提交”消息的定义。如果设置成 all，则表明所有 ISR 副本 Broker 都要接收到消息，该消息才算是“已提交”。这是最高等级的“已提交”定义。

**retries = <N>**:设置 retries 为一个较大的值。retries 同样是 Producer 的参数，对应前面提到的 Producer 自动重试。
当出现网络的瞬时抖动时，消息发送可能会失败，此时配置了 retries > 0 的 Producer 能够自动重试消息发送，避免消息丢失。

**unclean.leader.election.enable = false**: 这是 Broker 端的参数，它控制的是哪些 Broker 有资格竞选分区的 Leader。
如果一个 Broker 落后原先的 Leader 太多，那么它一旦成为新的 Leader，必然会造成消息的丢失。故一般都要将该参数设置成 false，即不允许这种情况的发生。

**replication.factor >= 3**:这也是 Broker 端的参数。其实这里想表述的是，最好将消息多保存几份，毕竟目前防止消息丢失的主要机制就是冗余

**min.insync.replicas > 1**:这依然是 Broker 端参数，控制的是消息至少要被写入到多少个副本才算是“已提交”。设置成大于 1 可以提升消息持久性。在实际环境中千万不要使用默认值 1。

> 确保 **replication.factor > min.insync.replicas**。如果两者相等，那么只要有一个副本挂机，整个分区就无法正常工作了。
我们不仅要改善消息的持久性，防止数据丢失，还要在不降低可用性的基础上完成。推荐设置成 **replication.factor = min.insync.replicas + 1**

> 确保消息消费完成再提交。Consumer 端有个参数 enable.auto.commit，最好把它设置成 false，并采用手动提交位移的方式。就像前面说的，这对于单 Consumer 多线程处理的场景而言是至关重要的。

> min.insync.replicas是保证下限的。acks=all的含义是producer会等ISR中所有副本都写入成功才返回，但如果不设置min.insync.replicas = 5，默认是1，
那么假设ISR中只有1个副本，只要写入这个副本成功producer也算其正常写入，因此min.insync.replicas保证的写入副本的下限。

#### 幂等生产者和事务生产者

**enable.idempotence = true**:Producer 升级成幂等生产者，此时 retries 配置将默认为Integer.MAX_VALUE，acks 配置将默认为 all

**transactional.id = <V>**:Producer 升级成事务生产者，此时 enable.idempotence 配置默认为 true

#### 消费者组重平衡相关

**session.timeout.ms = 6s**:会话超时时间，如果在会话超时过期之前没有接收到心跳，broker 会移除客户端并进行重平衡

**heartbeat.interval.ms = 2s**:客户端发送周期性心跳来表示它的活力，并且在消费新进入和离开组时促进消费者平衡

> 要保证 Consumer 实例在被判定为“dead”之前，能够发送至少 3 轮的心跳请求，即 session.timeout.ms >= 3 * heartbeat.interval.ms。

**max.poll.interval.ms**:消费者调用poll()最大超时。在超时前没有调用 poll() ,则消费者认定失败，消费者将进行重平衡

**max.poll.records**:对 poll() 一次调用中返回的最大记录数

#### 位移

**enable.auto.commit**:开启自动提交位移，它的默认值就是 true，即 Java Consumer 默认就是自动提交位移的

**auto.commit.interval.ms**:如果启用了自动提交，auto.commit.interval.ms 就派上用场了。它的默认值是 5 秒，表明 Kafka 每 5 秒会为你自动提交一次位移

#### 副本相关

**replica.lag.time.max.ms**:这个标准就是 Broker 端参数。这个参数的含义是 Follower 副本能够落后 Leader 副本的最长时间间隔，当前默认值是 10 秒。
这就是说，只要一个 Follower 副本落后 Leader 副本的时间不连续超过 10 秒，那么 Kafka 就认为该 Follower 副本与 Leader 是同步的，即使此时 Follower 副本中保存的消息明显少于 Leader 副本中的消息。

#### Broker相关

**num.io.threads**: 服务器用于处理请求的线程数，其中可能包括磁盘I/O。
**num.network.threads**: 服务器用于从网络接收请求并向网络发送响应的线程数。
这是Broker端两组线程池，就我个人而言，我觉得这是动态 Broker 参数最实用的场景了。毕竟，在实际生产环境中，Broker 端请求处理能力经常要按需扩容。

**num.replica.fetchers**:用于复制来自源代理的消息的访存线程的数量。增大此值可以增加关注代理中的I / O并行度。默认1