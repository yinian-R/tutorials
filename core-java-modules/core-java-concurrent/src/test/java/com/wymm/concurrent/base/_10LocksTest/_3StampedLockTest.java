package com.wymm.concurrent.base._10LocksTest;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock 是 ReentrantReadWriteLock 的另一种优化。
 * ReentrantReadWriteLock 虽然读写分离，但是是悲观读，当线程持有读锁时线程获取写锁就需等待读锁完成，则写操作花费的时间是等待读操作完成+写操作。
 * 而 StampedLock 提供一种乐观读操作，通过 tryOptimisticRead 返回一个 stamp （当有写锁时值为0），使用 validate 方法检查中间这段时间是否有获取写锁
 */
class _3StampedLockTest {
    private final StampedLock lock = new StampedLock();
    private double x, y;
    
    /**
     * 独占锁
     */
    void move(double deltaX, double deltaY) {
        long stamp = lock.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    /**
     * 乐观读
     * 其中存在乐观读转换悲观读的过程
     */
    double distanceFromOrigin() {
        long stamp = lock.tryOptimisticRead();
        double currentX = x, currentY = y;
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
    
    /**
     * 获取读锁，并尝试转换为写锁
     */
    void moveIfAtOrigin(double newX, double newY) {
        // Could instead start with optimistic, not read mode
        long stamp = lock.readLock();
        try {
            while (x == 0.0 && y == 0.0) {
                long ws = lock.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    lock.unlockRead(stamp);
                    stamp = lock.writeLock();
                }
            }
        } finally {
            lock.unlock(stamp);
        }
    }
    
}
