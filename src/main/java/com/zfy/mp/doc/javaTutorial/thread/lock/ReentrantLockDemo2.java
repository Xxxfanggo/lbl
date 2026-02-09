package com.zfy.bwcj.javaTutorial.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @文件名: ReentrantLockDemo2.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.lock
 * @描述: 可重入锁
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-20 16:58
 * @版本号: V2.4.0
 */
public class ReentrantLockDemo2 {
    private final ReentrantLock lock = new ReentrantLock();

    private void a() {
        lock.lock();
        try {
            b();
            // 打印 "b"
            System.out.println("a");
        } finally {

            lock.unlock();
        }
    }
    private void b() {
        // 获取锁
        lock.lock();
        try {
            // 打印 "b"
            System.out.println("b");
        } finally {
            // 确保锁能正确释放
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo2 demo = new ReentrantLockDemo2();
        demo.a();
    }
}
