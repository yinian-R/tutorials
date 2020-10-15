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

## 概念

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
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 6 --topic test2 --command-config /usr/local/kafka/config/producer.conf
# 测试 producer
bin/kafka-console-producer.sh --broker-list localhost:9092,localhost:9093 --topic test  --producer.config /usr/local/kafka/config/producer.conf
# 测试 consumer
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092,localhost:9093 --topic test --from-beginning --consumer.config /usr/local/kafka/config/consumer.conf
```


# 参考
http://kafka.apache.org/26/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html