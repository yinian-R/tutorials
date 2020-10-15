package com.wymm.kafka;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

class _3StreamsTest {
    
    @Test
    void test() {
        // Serializers/deserializers (serde) for String and Long types
        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();
        
        // Construct a `KStream` from the input topic "streams-plaintext-input", where message values
        // represent lines of text (for the sake of this example, we ignore whatever may be stored
        // in the message keys).
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream(
                "streams-plaintext-input",
                Consumed.with(stringSerde, stringSerde)
        );
        
        textLines
                // Split each text line, by whitespace, into words.
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                // Group the text words as message keys
                .groupBy((key, value) -> value)
                // Materialize the result into a KeyValueStore named "counts-store".
                // The Materialized store is always of type <Bytes, byte[]> as this is the format of the inner most store.
                // count运算符具有一个Materialized参数，用于指定运行计数应存储在名为的状态存储中counts-store。Counts可以实时查询该存储区
                .count(Materialized.as("counts-store"))
                // Store the running counts as a changelog stream to the output topic.
                .toStream().to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()));
        
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-plaintext-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        
        Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, props);
        
        // 输出程序的拓扑结构
        System.out.println(topology.describe());
        
        final CountDownLatch latch = new CountDownLatch(1);
        
        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });
        
        try {
            // 触发该客户端的执行
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
    
}
