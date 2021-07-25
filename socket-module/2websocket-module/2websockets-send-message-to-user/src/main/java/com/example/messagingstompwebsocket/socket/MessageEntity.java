package com.example.messagingstompwebsocket.socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {
    
    private String from;
    private String to;
    private String message;
    private Date time;
}
