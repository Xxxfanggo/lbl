package com.zfy.bwcj.javaTutorial.thread.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @文件名: MyCache.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.lock
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-21 11:19
 * @版本号: V2.4.0
 */
public class MyCache {
    // ReentrantReadWriteLock 读写锁保证了写操作的原子性，并且可以进行并发读
    // 创建读写锁实例
    public final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 使用 HashMap 存储缓存数据
    private Map<String, String> cache = new HashMap<>();

    // 写操作方法
    public void put(String key, String value) {
        readWriteLock.writeLock().lock();
        try {
            // 输出当前线程开始写入的提示信息
            System.out.println(Thread.currentThread().getName() + " 开始写入！" + value);

            // 模拟写操作的延迟
            Thread.sleep(300);

            // 将数据写入缓存
            cache.put(key, value);

            // 输出当前线程写入成功的提示信息
            System.out.println(Thread.currentThread().getName() + " 写入成功！" + value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // 读操作方法
    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            // 输出当前线程开始读出的提示信息
            System.out.println(Thread.currentThread().getName() + " 开始读出！");

            // 模拟读操作的延迟
            Thread.sleep(300);

            // 从缓存中读取数据
            String value = cache.get(key);

            // 输出当前线程读出成功的提示信息
            System.out.println(Thread.currentThread().getName() + " 读出成功！" + value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
