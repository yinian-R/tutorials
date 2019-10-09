package com.wymm.core.interfaces;

/**
 * 创建道理类接口
 */
public interface ProxyCreator {
    /**
     * 创建代理类
     *
     * @param type
     * @return
     */
    Object createProxy(Class<?> type);
}
