package com.example.messagingstompwebsocket.socket;

import com.example.messagingstompwebsocket.socket.config.MyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private MyBean myBean;
    
    
    // 这里的 @MessageMapping 可以当成 @RequestMapping,
    // 当有信息 (sendMsg 方法中的 messageEntity 参数即为客服端发送的信息实体)
    // 发送到 /sendMsg 时会在这里进行处理
    @MessageMapping("/sendMsg")
    public void sendMsg(MessageEntity messageEntity) {
        messageService.sendToUser(messageEntity);
    }
    
    @MessageMapping("/sendMsg2")
    public void sendMsg2(MessageEntity messageEntity) {
        messageService.sendToUserByUrl(messageEntity);
    }
    
}