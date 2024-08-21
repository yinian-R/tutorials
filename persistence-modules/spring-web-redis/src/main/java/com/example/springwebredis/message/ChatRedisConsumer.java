package com.example.springwebredis.message;

import com.example.springwebredis.core.message.RedisConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChatRedisConsumer implements RedisConsumer {

    @Override
    public String getPatternTopic() {
        return "chat";
    }

    @Override
    public void doHandleMessage(String message, String pattern) {
        log.info("Received <" + message + ">");
    }

    @Override
    public void errorHandle(Exception e, String message, String pattern) {
        log.error(e.getMessage(), e);
    }
}
