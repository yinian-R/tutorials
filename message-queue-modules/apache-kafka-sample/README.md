# Kafka

## 消息引擎模型
- 点对点模型
- 发布/订阅模型

## 消息传递
由于各种故障，消息传递系统无法保证生产者和消费者应用程序之间的消息传递。根据客户端应用程序与此类系统交互的方式，可能会出现以下消息语义：

- 最多一次：如果消息传递系统消息永远不会复制消息，但可能会偶尔遗漏消息
- 至少一次：如果它永远不会丢失一条消息，但可能偶尔会复制一条消息。
- 精准一次：它总是不重复的发送所有消息

##Kafka Consumer
实现多线程有两种方式：
- 方案1：消费者启用多个线程，每个线程维护专属的 KafkaConsumer 实例，负责完整的消费消息获取、消息处理流程。
- 方案2：消费者使用单或者多线程获取消息，同时创建多个消费者线程执行消息处理逻辑。

## Kafka Streams

http://kafka.apache.org/26/documentation/streams/

```shell script
# 创建输入主题
bin/kafka-topics.sh --create \
    --bootstrap-server localhost:9092 \
    --replication-factor 1 \
    --partitions 1 \
    --topic streams-plaintext-input;

# 创建输出主题，设置 Topic 日志压缩
bin/kafka-topics.sh --create \
    --bootstrap-server localhost:9092 \
    --replication-factor 1 \
    --partitions 1 \
    --topic streams-wordcount-output \
    --config cleanup.policy=compact;
```

> PS: 在任何系统中，因为程序而非资源限制而导致的阻塞都可能是系统的瓶颈，会影响整个应用程序的 TPS

## CAP

> 如果你听说过 CAP 理论的话，你一定知道，一个分布式系统通常只能同时满足一致性（Consistency）、可用性（Availability）、分区容错性（Partition tolerance）中的两个。
显然，在这个问题上，Kafka 赋予你选择 C 或 A 的权利。

## 认证机制 SASL/SCRAM-SHA-256
创建用户
```shell script
# 用于 broker 之间
bin/kafka-configs.sh --zookeeper localhost:2181 --alter --add-config 'SCRAM-SHA-256=[password=admin],SCRAM-SHA-512=[password=admin]' --entity-type users --entity-name admin
# 用于 producer
bin/kafka-configs.sh --zookeeper localhost:2181 --alter --add-config 'SCRAM-SHA-256=[password=writer],SCRAM-SHA-512=[password=writer]' --entity-type users --entity-name writer
# 用于 consumer
bin/kafka-configs.sh --zookeeper localhost:2181 --alter --add-config 'SCRAM-SHA-256=[password=reader],SCRAM-SHA-512=[password=reader]' --entity-type users --entity-name reader
```

创建 kafka-broker.jaas
```shell script
KafkaServer {
org.apache.kafka.common.security.scram.ScramLoginModule required
username="admin"
password="admin";
};
```

修改 server.properties
```shell script
listeners=SASL_PLAINTEXT://localhost:9092
security.inter.broker.protocol=SASL_PLAINTEXT
sasl.enabled.mechanisms=SCRAM-SHA-256
sasl.mechanism.inter.broker.protocol=SCRAM-SHA-256
```

启动 Broker 命令添加：
```shell script
export KAFKA_OPTS="-Djava.security.auth.login.config=<your_path>/kafka-broker.jaas"
nohup /bin/kafka-server-start.sh -daemon /config/server.properties  &>/dev/null &
```


创建 producer.conf
```
security.protocol=SASL_PLAINTEXT
sasl.mechanism=SCRAM-SHA-256
sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required username="writer" password="writer";
```
创建 consumer.conf
```
security.protocol=SASL_PLAINTEXT
sasl.mechanism=SCRAM-SHA-256
sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required username="reader" password="reader";
```
```shell script
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 6 --topic test --command-config /usr/local/kafka/config/producer.conf
# 测试 producer
bin/kafka-console-producer.sh --broker-list localhost:9092,localhost:9093 --topic test  --producer.config /usr/local/kafka/config/producer.conf
# 测试 consumer
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092,localhost:9093 --topic test --from-beginning --consumer.config /usr/local/kafka/config/consumer.conf
```

## 权限模型
权限模型，常见的有四种

- ACL：Access-Control List，访问控制列表。
- RBAC：Role-Based Access Control，基于角色的权限控制。
- ABAC：Attribute-Based Access Control，基于属性的权限控制。
- PBAC：Policy-Based Access Control，基于策略的权限控制。

在典型的互联网场景中，前两种模型应用得多，后面这两种则比较少用。

ACL 模型很简单，它表征的是用户与权限的直接映射关系，如下图所示：
```
graph LR
用户-->权限
```

而 RBAC 模型则加入了角色的概念，支持对用户进行分组，如下图所示：
```
graph LR
用户-->角色
角色-->权限
```

> Kafka 没有使用 RBAC 模型，它用的是 ACL 模型。

## 授权机制 ACL

**authorizer.class.name=kafka.security.auth.SimpleAclAuthorizer**:Kafka 开启 ACL

在开启了 ACL 授权之后，你还必须显式地为不同用户设置访问某项资源的权限，否则，在默认情况下，没有配置任何 ACL 的资源是不能被访问的。
不过，这里也有一个例外：超级用户能够访问所有的资源，即使你没有为它们设置任何 ACL 项。

**super.users=User:admin;User:admin2**:设置超级用户

当 admin 不是超级用户时，如果不为指定的 admin 授予集群操作的权限，你是无法成功启动 Broker 的。因此，你需要在启动 Broker 之前执行下面的命令：
```shell script
bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --add --allow-principal User:"admin" --operation All --cluster
```

为生产者授予 producer 权限
```shell script
bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --add --allow-principal User:"writer" --producer --topic '*'
```

为消费者授予 consumer 权限
```shell script
bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --add --allow-principal User:"reader" --consumer --topic '*' --group '*'
```

> 注意这两条命令中的 --producer 和 --consumer，它们类似于一个快捷方式，直接将 Producer 和 Consumer 常用的权限进行了一次性的授予。

## SSL
配置每个 Broker 的 server.properties 文件，增加以下内容：
```shell script
listeners=SSL://:9092                     
# SSL
security.inter.broker.protocol=SSL
ssl.truststore.location=/home/yi/source/kafkaenv/certificates/server.truststore.jks
ssl.truststore.password=pwd123456
ssl.keystore.location=/home/yi/source/kafkaenv/certificates/server.keystore.jks
ssl.keystore.password=pwd123456
ssl.client.auth=required
ssl.key.password=pwd123456
ssl.endpoint.identification.algorithm=
```

创建一个名为 client-ssl.config 的文件，内容如下：
```shell script
security.protocol=SSL
ssl.truststore.location=/home/yi/source/kafkaenv/certificates/client.truststore.jks
ssl.truststore.password=pwd123456
ssl.keystore.location=/home/yi/source/kafkaenv/certificates/server.keystore.jks
ssl.keystore.password=pwd123456
ssl.key.password=pwd123456
ssl.endpoint.identification.algorithm=
```
使用 SSL 测试生产者
```shell script
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test --producer.config client-ssl.config
bin/kafka-console-producer.sh --broker-list 192.168.191.128:9092 --topic test --producer.config client-ssl.config
```

> 认证和授权可组合使用：SASL + ACL, SSL + ACL

# 参考
http://kafka.apache.org/26/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html