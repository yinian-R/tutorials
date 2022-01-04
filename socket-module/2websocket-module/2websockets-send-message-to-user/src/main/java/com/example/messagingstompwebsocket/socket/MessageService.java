package com.example.messagingstompwebsocket.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    public MessageService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    
    public void sendToUser(MessageEntity messageEntity) {
        // convertAndSendToUser 方法可以发送信给给指定用户,
        // 底层会自动将第二个参数目的地址 /chat/contact 拼接为
        // /user/username/chat/contact，其中第二个参数 username 即为这里的第一个参数
        // username 也是前文中配置的 Principal 用户识别标志
        simpMessagingTemplate.convertAndSendToUser(
                messageEntity.getTo(),
                "/queue/chat/contact",
                messageEntity
        );
    }
    
    public void sendToUserByUrl(MessageEntity messageEntity) {
        simpMessagingTemplate.convertAndSend(
                "/queue/chat/contact2/" + messageEntity.getTo(),
                messageEntity
        );
    
    }
}
