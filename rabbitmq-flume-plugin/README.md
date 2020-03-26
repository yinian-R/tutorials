## 创建Agent文件
在conf目录下创建 sampleRabbitMQAgent.properties，内容如下：
```
sampleRabbitMQAgent.sources=rabbitSource
sampleRabbitMQAgent.sinks=sink1
sampleRabbitMQAgent.channels=channel1
# sources
sampleRabbitMQAgent.sources.rabbitSource.type=com.wymm.flume.core.rabbitmq.RabbitMQSource
sampleRabbitMQAgent.sources.rabbitSource.converter=com.wymm.flume.core.source.sample.SampleEventDataConverter
sampleRabbitMQAgent.sources.rabbitSource.rabbitmq.host=localhost
sampleRabbitMQAgent.sources.rabbitSource.rabbitmq.port=5672
sampleRabbitMQAgent.sources.rabbitSource.rabbitmq.queue=rabbit-java-sample
sampleRabbitMQAgent.sources.rabbitSource.rabbitmq.consumer.thread.count=5
# 启动命令
#.\bin\flume-ng.cmd agent -conf .\conf -conf-file .\conf\sampleRabbitMQAgent.properties -name sampleRabbitMQAgent -property flume.root.logger=INFO,console
# sink
sampleRabbitMQAgent.sinks.sink1.type=logger
# channel
sampleRabbitMQAgent.channels.channel1.type=memory
sampleRabbitMQAgent.channels.channel1.capacity=1000
sampleRabbitMQAgent.channels.channel1.transactionCapacity=100
# Bind the source and sink to the channel
sampleRabbitMQAgent.sources.rabbitSource.channels=channel1
sampleRabbitMQAgent.sinks.sink1.channel=channel1
```

## Source 配置
|  属性 | 功能 | 值类型 |
|---|---|---|
| rabbitmq.host | RabbitMQ 主机 | 字符串 |
| rabbitmq.port | RabbitMQ 端口 | 整数 |
| rabbitmq.username | RabbitMQ 用户 | 字符串 |
| rabbitmq.password | RabbitMQ 密码 | 字符串 |
| rabbitmq.queue | RabbitMQ 队列 | 字符串 |
| rabbitmq.queue.create | 是否根据TOPIC_EXCHANGE、ROUTING_KEY创建QUEUE | 布尔 |
| rabbitmq.exchange | RabbitMQ 交换机 | 字符串 |
| rabbitmq.routingKey | RabbitMQ 路由 | 字符串 |
| rabbitmq.prefetch-count | 预期接收数量 | 整数 |
| rabbitmq.autoAck | 是否开启自动确认ACK | 布尔 |
| converter | 数据转换类 | 字符串 |
| converter.consumer.singleton | 每个Consumer有一个Converter实例 | 布尔 |
| rabbitmq.consumer.thread.count | 消费线程数量 | 整数 |

## 启动命令
> .\bin\flume-ng.cmd agent -conf .\conf -conf-file .\conf\sampleRabbitMQAgent.properties -name sampleRabbitMQAgent -property flume.root.logger=INFO,console


## Converter Sample
```
@Slf4j
public class SampleEventDataConverter implements Converter {
    
    @Override
    public Event convert(Event source, Context context) {
        log.info("执行转换数据");
        return source;
    }
}
```