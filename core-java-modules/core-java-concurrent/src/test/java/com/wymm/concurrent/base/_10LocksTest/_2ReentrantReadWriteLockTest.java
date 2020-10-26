package com.wymm.concurrent.base._10LocksTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock 类实现ReadWriteLock接口
 * 通过线程获取 ReadLock 或 WriteLock 的规则：
 * - ReadLock - 如果没有线程获取或请求写锁，则多个线程可以获取读锁
 * - WriteLock - 如果没有线程正在读或写，则只有一个线程可以获取写锁
 * <p>
 * 可以锁降级：线程获取写入锁后可以获取读锁，然后释放写入锁，这样写入锁就降级成读锁，从而实现锁降级的特性
 * 不可锁升级：线程获取读锁后不能直接升级为写入锁，需先释放读锁才能获取写入锁
 */
class _2ReentrantReadWriteLockTest {
    
    private final Map<String, String> syncHashMap = new HashMap<>();
    
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();
    
    public void put(String key, String value) {
        try {
            writeLock.lock();
            syncHashMap.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
    
    public String remove(String key) {
        try {
            writeLock.lock();
            return syncHashMap.remove(key);
        } finally {
            writeLock.unlock();
        }
    }
    
    public String get(String key) {
        try {
            readLock.lock();
            return syncHashMap.get(key);
        } finally {
            readLock.unlock();
        }
    }
    
    public boolean containsKey(String key) {
        try {
            readLock.lock();
            return syncHashMap.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }
    
}
