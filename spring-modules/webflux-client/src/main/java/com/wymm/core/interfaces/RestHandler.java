package com.wymm.core.interfaces;

import com.wymm.core.beans.MethodInfo;
import com.wymm.core.beans.ServerInfo;

/**
 * rest 请求调用 handle
 */
public interface RestHandler {
    /**
     * 初始化服务器信息
     *
     * @param serverInfo 服务器信息
     */
    void init(ServerInfo serverInfo);
    
    /**
     * 调用 REST 请求返回接口
     *
     * @param methodInfo 接口信息
     * @return
     */
    Object invokeRest(MethodInfo methodInfo);
}
