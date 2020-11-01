package com.wymm.core.interfaces;

import com.wymm.core.beans.MethodInfo;
import com.wymm.core.beans.ServerInfo;

/**
 * 请求接口调用处理类接口
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
