package com.example.kafka.kafka.consumer;

import com.example.kafka.kafka.modal.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class KafkaReceiver {

    @KafkaListener(topics = {"channel1"})
    public void listen(ConsumerRecord record) {
        Optional<?> kafkaData = Optional.ofNullable(record.value());
        if (kafkaData.isPresent()) {
            Message data = (Message) kafkaData.get();

            log.info("-------订阅消息-------:");
            log.info("-------record-------:" + record);
            log.info("-------kafka data-------:" + data);
        }
    }

    @KafkaListener(topics = {"channel1"}, groupId = "group2")
    public void listen2(ConsumerRecord record) {

        Optional<?> kafkaData = Optional.ofNullable(record.value());
        if (kafkaData.isPresent()) {
            Message data = (Message) kafkaData.get();

            log.info("~~~~订阅消息~~~~~:");
            log.info("~~~~record~~~~~:" + record);
            log.info("~~~~kafka data~~~~~:" + data);
        }
    }
}
