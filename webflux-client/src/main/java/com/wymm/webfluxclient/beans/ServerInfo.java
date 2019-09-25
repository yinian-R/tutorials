package com.wymm.webfluxclient.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务器信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServerInfo {
    // 服务器 URL
    private String url;
}
