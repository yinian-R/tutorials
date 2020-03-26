package com.example.kafka.kafka.producer;

import com.example.kafka.kafka.modal.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

@Slf4j
@Component
public class KafkaSender {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Scheduled(cron = "00/1 * * * * ?")
    public void send() {
        String data = UUID.randomUUID().toString();
        Message message = Message.builder()
                .id(data)
                .msg("hello")
                .build();
        ListenableFuture<SendResult<String, Object>> listenableFuture = kafkaTemplate.send("channel1", message);
        listenableFuture.addCallback(o -> log.info("------发送消息成功：" + data), e -> log.info("------发送消息失败：" + data));
    }
}
