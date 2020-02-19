package com.wymm.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * send test message
 * 请注意，模板使用foo.bar.baz与绑定匹配的路由键路由消息到交换机。
 */
@Slf4j
@Component
public class TestRunner implements CommandLineRunner {
    
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;
    
    public TestRunner(RabbitTemplate rabbitTemplate, Receiver receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }
    
    @Override
    public void run(String... args) throws Exception {
        
        log.info("send test message");
        rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE_NAME, "foo.bar.baz", "Hello Word");
        receiver.getLatch().await(10, TimeUnit.SECONDS);
        log.info("send test message finally");
    }
}
