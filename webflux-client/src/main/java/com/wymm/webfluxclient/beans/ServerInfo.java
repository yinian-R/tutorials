package com.wymm.webfluxclient.beans;

import lombok.Builder;
import lombok.Data;

/**
 * 服务器信息
 */
@Data
@Builder
public class ServerInfo {
    // 服务器 URL
    private String url;
}
