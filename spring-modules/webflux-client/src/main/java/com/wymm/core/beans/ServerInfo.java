package com.wymm.core.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求服务器信息类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServerInfo {
    // 服务器 URL
    private String url;
}
