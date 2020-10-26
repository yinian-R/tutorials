package com.wymm.kafka;

import kafka.common.KafkaException;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.internals.ErrorLoggingCallback;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.util.Properties;

/**
 * show javadoc {@link KafkaProducer}
 * Producer 由一个缓存空间和一个 I/O 后台线程组成，该缓存池保存尚未传输到服务器，该I/O线程负责将这些记录转换为请求并将他们传输到集群。使用后如果不关闭Producer，则会泄漏这些资源
 * <p>
 * send() 是异步的，调用时，它将记录添加到暂挂记录发送和立即返回的缓冲区中。这使生产者可以将各个记录一起批处理以提高效率。
 * acks 配置控制将请求视为完成的条件。我们指定的“all”设置将导致记录的完全提交受阻，这是最慢但最持久的设置。
 * 如果请求失败，生产者可以自动重试，尽管由于我们将其指定retries为0，不会重试。启用重试也将打开重复的可能性
 * <p>
 * KafkaProducer支持两种附加模式：幂等生产者和事务生产者。
 * - 幂等的生成器将Kafka的交付语义从至少一次交付增强到恰好一次交付。特别是，生产者重试将不再引入重复项。
 * - 事务生产方允许应用程序原子地将消息发送到多个分区（和主题！）
 * <p>
 * 要启用幂等性，enable.idempotence 必须将配置设置为true。如果设置，则 retries 配置将默认为Integer.MAX_VALUE，并且 acks 配置将默认为all。幂等生产者没有API更改，因此无需修改现有应用程序即可利用此功能。
 * 为了利用幂等的生成器，必须避免重新发送应用程序级别，因为这些应用程序无法重复删除。因此，如果应用程序启用幂等性，建议将 retries 配置保留为未设置状态，
 * 因为它将默认设置为Integer.MAX_VALUE。此外，如果 send(ProducerRecord) 即使无限次重试也返回错误（例如，如果消息在发送之前已在缓冲区中过期），
 * 则建议关闭生产者并检查最后产生的消息的内容，以确保不会重复的。最后，生产者只能保证在单个会话中发送的消息具有幂等性。
 * <p>
 * 要使用事务性生产者和附带的API，必须设置 transactional.id 配置属性。如果 transactional.id 设置，将自动启用幂等性以及幂等性依赖的生产者配置。
 * 此外，交易中包含的主题应配置为具有持久性。特别 replication.factor 是，至少应将设置为3，将 min.insync.replicas 这些主题的设置为2。
 * 最后，为了实现端到端的交易保证，必须将 consumers 配置为仅 read only committed messages
 */
class _1ProducerTest {
    
    /**
     * 生产者的简单示例
     */
    @Test
    void send() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<>(KafkaConfig.TEST_TOPIC, Integer.toString(i), Integer.toString(i)));
        }
        
        producer.close();
    }
    
    /**
     * Producer 永远要使用带有回调通知的发送 API，也就是说不要使用 producer.send(msg)，而要使用 producer.send(msg, callback)。
     * 不要小瞧这里的 callback（回调），它能准确地告诉你消息是否真的提交成功了。一旦出现消息提交失败的情况，你就可以有针对性地进行处理。
     */
    @Test
    void whenSendUsingCallback() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, "10");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        
        String topic = KafkaConfig.TEST_TOPIC;
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 1000; i++) {
            String key = Integer.toString(i);
            String value = Integer.toString(i);
            producer.send(new ProducerRecord<>(topic, key, value), new ErrorLoggingCallback(topic, key.getBytes(), value.getBytes(), true));
        }
        
        producer.close();
    }
    
    /**
     * 幂等生产者
     */
    @Test
    void idempotence() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        
        Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<>(KafkaConfig.TEST_TOPIC, Integer.toString(i), Integer.toString(i)));
        }
        
        producer.close();
    }
    
    /**
     * 事务生产者
     * 配合事务消费者一起使用可以达到精准一次
     * <p>
     * 如示例所示，每个生产者只能有一个未完成的交易。在beginTransaction()和commitTransaction()调用之间发送的所有消息 将成为单个交易的一部分。
     * 当 transactional.id指定时，由生产者发送的所有消息必须是事务的一部分。
     * 事务性生产者使用异常来传达错误状态。特别是，不需要为指定回调producer.send()或对返回的Future进行调用get()，KafkaException如果任何producer.send()或事务调用在事务期间遇到不可恢复的错误，则将抛出异常。
     * 通过调用 producer.abortTransaction()，KafkaException我们可以确保将任何成功的写入都标记为已中止，从而保留事务保证。
     */
    @Test
    void transactional() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "my-transactional-id");
        Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
        
        producer.initTransactions();
        
        try {
            producer.beginTransaction();
            for (int i = 0; i < 100; i++) {
                producer.send(new ProducerRecord<>(KafkaConfig.TEST_TOPIC, Integer.toString(i), Integer.toString(i)));
            }
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit.
            producer.close();
        } catch (KafkaException e) {
            // For all other exceptions, just abort the transaction and try again.
            producer.abortTransaction();
        }
        producer.close();
    }
    
    /**
     * 使用认证机制 SCRAM-SHA-256 生产消息
     */
    @Test
    void usingSaslScram_thenSend() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        props.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-256");
        props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"writer\" password=\"writer\";");
        
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<>(KafkaConfig.TEST_TOPIC, Integer.toString(i), Integer.toString(i)));
        }
        
        producer.close();
    }
    
}
