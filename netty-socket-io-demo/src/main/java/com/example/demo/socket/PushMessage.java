package com.example.demo.socket;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PushMessage {
    //@ApiModelProperty(value = "登录用户编号")
    private String loginUserNum;
    
    //@ApiModelProperty(value = "推送内容")
    private String content;
    
}