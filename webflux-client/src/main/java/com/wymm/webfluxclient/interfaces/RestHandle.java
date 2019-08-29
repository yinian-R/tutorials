package com.wymm.webfluxclient.interfaces;

import com.wymm.webfluxclient.beans.MethodInfo;
import com.wymm.webfluxclient.beans.ServerInfo;

/**
 * rest 请求调用 handle
 */
public interface RestHandle {
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
