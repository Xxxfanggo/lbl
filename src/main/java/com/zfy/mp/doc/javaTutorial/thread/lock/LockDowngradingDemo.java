package com.zfy.bwcj.javaTutorial.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @文件名: LockDowngradingDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.lock
 * @描述: 锁降级
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-21 14:05
 * @版本号: V2.4.0
 */
public class LockDowngradingDemo {
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = reentrantReadWriteLock.readLock();
    private Lock writeLock = reentrantReadWriteLock.writeLock();
    // 用于存储数据
    private int data = 0;

    private int readData() {
        // 获取读锁
        readLock.lock();
        System.out.println("read begin 读到数据"
                + Thread.currentThread().getName()
                + ": " + data);
        try {
            Thread.sleep(500);
            return data;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 读操作结束，释放读锁
            System.out.println("read end 读到数据"
                    + Thread.currentThread().getName()
                    + ": " + data);

            readLock.unlock();
        }
    }
    private int updateData(int newData) {
        // 获取写锁
        writeLock.lock();
        System.out.println("update begin 更新数据" +
                Thread.currentThread().getName()
                + ": " + newData);
        try {
            Thread.sleep(500);
            this.data = newData;
            // 锁降级，获得读锁
            readLock.lock();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 写操作结束，释放写锁
            System.out.println("update end 更新数据"
                    + Thread.currentThread().getName()
                    + ": " + newData);
            writeLock.unlock();
        }

        try {
            // 读取数据
            return readData();
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        LockDowngradingDemo lockDowngradingDemo = new LockDowngradingDemo();
        // 启动一个线程进行写操作
        new Thread(() -> {
            lockDowngradingDemo.updateData(999);
        }).start();

        // 启动另一个线程进行读操作
        new Thread(() -> {
            lockDowngradingDemo.readData();
        }).start();

    }
}
