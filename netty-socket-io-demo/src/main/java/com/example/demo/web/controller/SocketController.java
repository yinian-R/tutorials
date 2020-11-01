package com.example.demo.web.controller;

import com.example.demo.socket.PushMessage;
import com.example.demo.socket.SocketIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/socket")
@RestController
public class SocketController {
    
    @Autowired
    private SocketIOService socketIOService;
    
    /**
     * example: http://localhost:8080/socket/push?loginUserNum=88&content=hello
     *
     * @param pushMessage
     */
    @GetMapping("/push")
    public void pushMessage(PushMessage pushMessage) {
        socketIOService.pushMessageToUser(pushMessage);
    }
}
